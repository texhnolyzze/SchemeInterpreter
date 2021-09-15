package interpreter.exp.compound.function.builtin;

public class LessThanOrEqualExpression extends NumberCompareExpression {

    public static final LessThanOrEqualExpression INSTANCE = new LessThanOrEqualExpression();

    private LessThanOrEqualExpression() {
    }

    @Override
    protected boolean matches(final long compare) {
        return compare <= 0;
    }

}
