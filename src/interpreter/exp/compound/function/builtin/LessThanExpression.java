package interpreter.exp.compound.function.builtin;

public class LessThanExpression extends NumberCompareExpression {

    public static final LessThanExpression INSTANCE = new LessThanExpression();

    private LessThanExpression() {
    }

    @Override
    protected boolean matches(long compare) {
        return compare < 0;
    }

    @Override
    public String toString() {
        return "<";
    }

}
