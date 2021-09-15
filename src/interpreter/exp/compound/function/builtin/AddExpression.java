package interpreter.exp.compound.function.builtin;

import interpreter.exp.self.NumberExpression;

public class AddExpression extends NumberCombineCopyingExpression {

    public static final AddExpression INSTANCE = new AddExpression();

    private AddExpression() {
        super();
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.add(right);
    }

}
