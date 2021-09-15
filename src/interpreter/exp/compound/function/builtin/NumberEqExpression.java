package interpreter.exp.compound.function.builtin;

public class NumberEqExpression extends NumberCompareExpression {

    public static final NumberEqExpression INSTANCE = new NumberEqExpression();

    private NumberEqExpression() {
    }

    @Override
    protected boolean matches(long compare) {
        return compare == 0;
    }

}
