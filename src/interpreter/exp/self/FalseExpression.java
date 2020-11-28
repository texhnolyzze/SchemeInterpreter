package interpreter.exp.self;

public class FalseExpression extends BooleanExpression {

    public static final FalseExpression INSTANCE = new FalseExpression();

    private FalseExpression() {
    }

    @Override
    public BooleanExpression not() {
        return TrueExpression.INSTANCE;
    }

    @Override
    public String toString() {
        return "false";
    }

}
