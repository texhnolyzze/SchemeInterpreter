package interpreter.exp.compound;

import interpreter.exp.self.NumberExpression;

public class ModExpression extends NumberCombineCopyingExpression {

    public static final ModExpression INSTANCE = new ModExpression();

    private ModExpression() {
        super();
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.mod(right);
    }

}
