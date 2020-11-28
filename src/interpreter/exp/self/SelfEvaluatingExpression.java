package interpreter.exp.self;

import interpreter.Environment;
import interpreter.exp.Expression;

public abstract class SelfEvaluatingExpression implements Expression {

    @Override
    public Expression eval(Environment env) {
        return this;
    }

}
