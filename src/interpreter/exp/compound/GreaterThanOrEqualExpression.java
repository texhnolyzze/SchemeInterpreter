package interpreter.exp.compound;

public class GreaterThanOrEqualExpression extends NumberCompareExpression {

    public static final GreaterThanOrEqualExpression INSTANCE = new GreaterThanOrEqualExpression();

    private GreaterThanOrEqualExpression() {
    }

    @Override
    protected boolean matches(final long compare) {
        return compare >= 0;
    }

}
