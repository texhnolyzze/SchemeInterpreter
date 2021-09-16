package interpreter.exp.self;

import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.Map;

public abstract class SelfEvaluatingExpression implements Expression {

    @Override
    public Expression eval(Environment env) {
        return this;
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return this;
    }

}
