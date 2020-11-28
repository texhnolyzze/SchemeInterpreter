package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.exp.Expression;
import interpreter.exp.self.NilExpression;

import java.util.List;

public abstract class CompoundExpression implements Expression {

    public CompoundExpression(List<Object> list, Analyzer analyzer) {
    }

    protected void assertNumArgs(List<Object> args, int expected) {
        if (args.size() - 1 != expected)
            throw new IllegalArgumentException(getClass().getSimpleName() + ": expected " + expected + " args, got " + (args.size() - 1));
    }

    protected void assertAtLeastNumArgs(List<Object> args, int expected) {
        if (args.size() - 1 < expected)
            throw new IllegalArgumentException(getClass().getSimpleName() + ": expected at least " + expected + " args, got " + (args.size() - 1));
    }

    protected void assertType(Expression expression, Class<? extends Expression> expected) {
        if (!expected.isAssignableFrom(expression.getClass()))
            throw new IllegalArgumentException(getClass().getSimpleName() + ": expected arg type is " + expected.getSimpleName() + ", actual is " + expression.getClass().getSimpleName());
    }

    protected void assertNotNull(Expression expression) {
        if (expression == NilExpression.INSTANCE)
            throw new IllegalArgumentException("nil pointer dereference");
    }

}
