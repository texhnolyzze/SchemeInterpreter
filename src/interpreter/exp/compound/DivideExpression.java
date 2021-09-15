package interpreter.exp.compound;

import interpreter.exp.self.NumberExpression;

public class DivideExpression extends NumberCombineCopyingExpression {

    public static final DivideExpression INSTANCE = new DivideExpression();

    private DivideExpression() {
        super();
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.div(right);
    }

}
