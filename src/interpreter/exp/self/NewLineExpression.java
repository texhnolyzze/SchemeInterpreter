package interpreter.exp.self;

import interpreter.Environment;
import interpreter.InOut;
import interpreter.exp.Expression;

public class NewLineExpression extends SelfEvaluatingExpression {

    public static final NewLineExpression INSTANCE = new NewLineExpression();

    private NewLineExpression() {
    }

    @Override
    public Expression eval(final Environment env) {
        InOut.instance().out().println();
        return NilExpression.INSTANCE;
    }

}
