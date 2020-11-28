package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.exp.Expression;
import interpreter.exp.self.NilExpression;

import java.util.List;

public abstract class CompoundExpression implements Expression {

    public CompoundExpression(List<Object> list, Analyzer analyzer) {
    }

    protected void assertNumArgs(List<?> args, int expected) {
        assertNumArgs(1, args, expected);
    }

    protected void assertNumArgs(int offset, List<?> args, int expected) {
        if (args.size() - offset != expected)
            throw new IllegalArgumentException(getClass().getSimpleName() + ": expected " + expected + " args, got " + (args.size() - offset));
    }

    protected void assertAtLeastNumArgs(int offset, List<Object> args, int expected) {
        if (args.size() - offset < expected)
            throw new IllegalArgumentException(getClass().getSimpleName() + ": expected at least " + expected + " args, got " + (args.size() - offset));
    }

    protected void assertAtLeastNumArgs(List<Object> args, int expected) {
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

}
