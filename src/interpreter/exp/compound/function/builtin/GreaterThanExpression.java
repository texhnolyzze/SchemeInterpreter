package interpreter.exp.compound.function.builtin;

public class GreaterThanExpression extends NumberCompareExpression {

    public static final GreaterThanExpression INSTANCE = new GreaterThanExpression();

    private GreaterThanExpression() {
    }

    @Override
    protected boolean matches(long compare) {
        return compare > 0;
    }

}
