package interpreter.exp.compound;

import interpreter.exp.self.NumberExpression;

public class SubtractAndSetExpression extends NumberCombineModifyingExpression {

    public static final SubtractAndSetExpression INSTANCE = new SubtractAndSetExpression();

    private SubtractAndSetExpression() {
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.sub(right);
    }

}
