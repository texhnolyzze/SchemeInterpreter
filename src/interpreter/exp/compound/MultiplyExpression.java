package interpreter.exp.compound;

import interpreter.exp.self.NumberExpression;

public class MultiplyExpression extends NumberCombineCopyingExpression {

    public static final MultiplyExpression INSTANCE = new MultiplyExpression();

    private MultiplyExpression() {
        super();
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.mul(right);
    }

}
