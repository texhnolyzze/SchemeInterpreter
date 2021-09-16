package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.compound.function.Function;
import interpreter.exp.compound.function.UserDefinedFunction;

import java.util.List;

public abstract class BaseExpression implements Expression {

    protected static final ThreadLocal<Boolean> IN_TRAMPOLINE = new ThreadLocal<>();

    private final List<?> src;

    protected BaseExpression(List<?> list) {
        this.src = list;
    }

    protected void assertNotPredefined(Object o, Analyzer analyzer) {
        if (analyzer.predefined().containsKey(o)) {
            throw new IllegalArgumentException(o + " is predefined, can't overwrite");
        }
    }

    protected Expression trampoline(Expression e, Environment env) {
        if (e.getClass() != ApplyExpression.class) {
            return e.eval(env);
        }
        if (Boolean.TRUE.equals(IN_TRAMPOLINE.get())) {
            return e;
        }
        IN_TRAMPOLINE.set(true);
        TrampolineCtx ctx = new TrampolineCtx();
        ctx.environment = env;
        try {
            do {
                ((ApplyExpression) e).storeFuncToCall(ctx);
                if (ctx.func instanceof UserDefinedFunction fun) {
                    e = fun.evalBound(ctx.environment);
                } else {
                    e = ctx.func.eval(ctx.environment, ctx.args);
                }
            } while (e instanceof ApplyExpression);
            return e;
        } finally {
            IN_TRAMPOLINE.set(false);
        }
    }

    @Override
    public String toString() {
        return src == null ? super.toString() : src.toString();
    }

    protected static class TrampolineCtx {
        Function func;
        List<Expression> args;
        Environment environment;
    }

}
