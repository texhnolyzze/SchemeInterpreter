package interpreter.exp.self;

public class NilExpression extends SelfEvaluatingExpression {

    public static final NilExpression INSTANCE = new NilExpression();

    private NilExpression() {
    }

    @Override
    public String toString() {
        return "nil";
    }

}
