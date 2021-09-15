package interpreter.exp.compound.function.builtin;

import interpreter.exp.self.NumberExpression;

public class DivideAndSetExpression extends NumberCombineModifyingExpression {

    public static final DivideAndSetExpression INSTANCE = new DivideAndSetExpression();

    private DivideAndSetExpression() {
        super();
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.div(right);
    }

}
