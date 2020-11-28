package interpreter.exp;

import interpreter.Environment;
import interpreter.exp.compound.ApplyExpression;
import interpreter.exp.compound.SequenceExpression;

import java.util.List;

public class ProcedureExpression implements Expression {

    private final SequenceExpression sequence;
    private final List<String> params;

    public ProcedureExpression(SequenceExpression sequence, List<String> params) {
        this.sequence = sequence;
        this.params = params;
    }

    @Override
    public Expression eval(Environment env) {
        Expression eval = sequence.eval(env);
        while (eval.getClass() == ApplyExpression.class) {
            eval = eval.eval(env);
        }
        return eval;
    }

    public List<String> params() {
        return params;
    }

}
