package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.ProcedureExpression;
import interpreter.exp.self.NilExpression;

import java.util.List;

public abstract class CompoundExpression implements Expression {

    protected static final ThreadLocal<Boolean> IN_TRAMPOLINE = new ThreadLocal<>();

    private final List<?> src;

    public CompoundExpression(List<?> list, Analyzer analyzer) {
        this.src = list;
    }

    protected void assertNumArgs(List<?> args, int expected) {
        assertNumArgs(1, args, expected);
    }

    protected void assertNumArgs(int offset, List<?> args, int expected) {
        if (args.size() - offset != expected)
            throw new IllegalArgumentException(getClass().getSimpleName() + ": expected " + expected + " args, got " + (args.size() - offset));
    }

    protected void assertAtLeastNumArgs(int offset, List<?> args, int expected) {
        if (args.size() - offset < expected)
            throw new IllegalArgumentException(getClass().getSimpleName() + ": expected at least " + expected + " args, got " + (args.size() - offset));
    }

    protected void assertAtLeastNumArgs(List<?> args, int expected) {
        assertAtLeastNumArgs(1, args, expected);
    }

    protected void assertType(Expression expression, Class<? extends Expression> expected) {
        if (!expected.isAssignableFrom(expression.getClass()))
            throw new IllegalArgumentException(getClass().getSimpleName() + ": expected arg type is " + expected.getSimpleName() + ", actual is " + expression.getClass().getSimpleName());
    }

    protected void assertNotNull(Expression expression) {
        if (expression == NilExpression.INSTANCE)
            throw new IllegalArgumentException("nil pointer dereference");
    }

    protected void assertSymbol(Object o) {
        boolean symbol = o instanceof String && ((String) o).charAt(0) != '"';
        if (!symbol)
            throw new IllegalArgumentException(getClass().getSimpleName() + ": symbol expected, got " + o);
    }

    protected void assertList(Object o) {
        if (!(o instanceof List))
            throw new IllegalArgumentException(getClass().getSimpleName() + ": list expected, got " + o);
    }

    protected void assertNotPredefined(Object o, Analyzer analyzer) {
        if (analyzer.predefined().containsKey(o))
            throw new IllegalArgumentException(o + " is predefined, can't overwrite");
    }

    protected Expression trampoline(Expression e, Environment env) {
        if (e.getClass() != ApplyExpression.class)
            return e.eval(env);
        if (Boolean.TRUE.equals(IN_TRAMPOLINE.get()))
            return e;
        IN_TRAMPOLINE.set(true);
        TrampolineCtx ctx = new TrampolineCtx();
        try {
            do {
                ((ApplyExpression) e).storeProcedureToCall(env, ctx);
                env = ctx.extendedEnvironment;
                e = ctx.proc.eval(env);
            } while (e.getClass() == ApplyExpression.class);
            return e;
        } finally {
            IN_TRAMPOLINE.set(false);
        }
    }

    @Override
    public String toString() {
        return src.toString();
    }

    protected static class TrampolineCtx {
        ProcedureExpression proc;
        Environment extendedEnvironment;
    }

}
