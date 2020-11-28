package interpreter.exp.self;

public class TrueExpression extends BooleanExpression {

    public static final TrueExpression INSTANCE = new TrueExpression();

    private TrueExpression() {
    }

    @Override
    public BooleanExpression not() {
        return FalseExpression.INSTANCE;
    }

    @Override
    public String toString() {
        return "true";
    }

}
