package interpreter.exp.compound;

import interpreter.exp.self.NumberExpression;

public class AddAndSetExpression extends NumberCombineModifyingExpression {

    public static final AddAndSetExpression INSTANCE = new AddAndSetExpression();

    private AddAndSetExpression() {
        super();
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.add(right);
    }
    
}
