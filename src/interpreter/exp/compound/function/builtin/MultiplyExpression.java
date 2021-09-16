package interpreter.exp.compound.function.builtin;

import interpreter.exp.self.NumberExpression;

public class MultiplyExpression extends NumberCombineCopyingExpression {

    public static final MultiplyExpression INSTANCE = new MultiplyExpression();

    private MultiplyExpression() {
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.mul(right);
    }

    @Override
    public String toString() {
        return "*";
    }

}
