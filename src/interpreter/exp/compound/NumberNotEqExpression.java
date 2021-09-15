package interpreter.exp.compound;

public class NumberNotEqExpression extends NumberCompareExpression {

    public static final NumberNotEqExpression INSTANCE = new NumberNotEqExpression();

    private NumberNotEqExpression() {
    }

    @Override
    protected boolean matches(long compare) {
        return compare != 0;
    }

}
