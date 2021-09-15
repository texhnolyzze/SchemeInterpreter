package interpreter.exp.compound;

public class LessThanExpression extends NumberCompareExpression {

    public static final LessThanExpression INSTANCE = new LessThanExpression();

    private LessThanExpression() {
    }

    @Override
    protected boolean matches(long compare) {
        return compare < 0;
    }

}
