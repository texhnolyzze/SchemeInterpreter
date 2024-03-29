package interpreter.exp.compound.function.builtin;

import interpreter.exp.self.NumberExpression;

public class ModAndSetExpression extends NumberCombineModifyingExpression {

    public static final ModAndSetExpression INSTANCE = new ModAndSetExpression();

    private ModAndSetExpression() {
    }

    @Override
    protected NumberExpression combine(NumberExpression left, NumberExpression right) {
        return left.mod(right);
    }

    @Override
    public String toString() {
        return "%=";
    }

}
