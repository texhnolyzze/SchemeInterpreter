package interpreter.exp.compound.function.builtin;

import interpreter.exp.self.NumberExpression;

public class ModExpression extends NumberCombineCopyingExpression {

    public static final ModExpression INSTANCE = new ModExpression();

    private ModExpression() {
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.mod(right);
    }

    @Override
    public String toString() {
        return "%";
    }

}
