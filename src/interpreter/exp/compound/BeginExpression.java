package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.List;
import java.util.Map;

public class BeginExpression extends BaseExpression {

    private final SequenceExpression seq;

    public BeginExpression(List<?> list, Analyzer analyzer) {
        this.seq = new SequenceExpression(1, list, analyzer);
    }

    private BeginExpression(final SequenceExpression seq) {
        this.seq = seq;
    }

    @Override
    public Expression eval(Environment env) {
        return seq.eval(env);
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return new BeginExpression(seq.expand(params, env));
    }

    @Override
    public String toString() {
        return "(begin " + seq.toString() + ")";
    }

}
