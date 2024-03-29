package interpreter.exp.compound.function.builtin;

import interpreter.exp.self.NumberExpression;

public class DivideExpression extends NumberCombineCopyingExpression {

    public static final DivideExpression INSTANCE = new DivideExpression();

    private DivideExpression() {
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.div(right);
    }

    @Override
    public String toString() {
        return "/";
    }

}
