package interpreter.exp;

import interpreter.Environment;
import interpreter.exp.compound.SequenceExpression;

import java.util.List;

public class Procedure implements Expression {

    private final SequenceExpression body;
    private final List<String> params;
    private final Environment env; // environment in which procedure was defined

    public Procedure(SequenceExpression body, List<String> params, Environment env) {
        this.body = body;
        this.params = params;
        this.env = env;
    }

    @Override
    public Expression eval(Environment env) {
        return body.eval(env);
    }

    public List<String> params() {
        return params;
    }

    public Environment env() {
        return env;
    }

}
