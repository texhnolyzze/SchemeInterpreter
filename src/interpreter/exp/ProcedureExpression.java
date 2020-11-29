package interpreter.exp;

import interpreter.Environment;
import interpreter.exp.compound.SequenceExpression;

import java.util.List;

public class ProcedureExpression implements Expression {

    private final SequenceExpression sequence;
    private final List<String> params;
    private final Environment env;

    public ProcedureExpression(SequenceExpression sequence, List<String> params, Environment env) {
        this.sequence = sequence;
        this.params = params;
        this.env = env;
    }

    @Override
    public Expression eval(Environment env) {
        return sequence.eval(env);
    }

    public List<String> params() {
        return params;
    }

    public Environment env() {
        return env;
    }

}
