package interpreter.exp.compound.function.builtin;

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
