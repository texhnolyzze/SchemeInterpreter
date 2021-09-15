package interpreter.exp.compound;

import interpreter.exp.self.NumberExpression;

public class MultiplyAndSetExpression extends NumberCombineModifyingExpression {

    public static final MultiplyAndSetExpression INSTANCE = new MultiplyAndSetExpression();

    private MultiplyAndSetExpression() {
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.mul(right);
    }

}
