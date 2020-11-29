package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.List;

public class BeginExpression extends CompoundExpression {

    private final SequenceExpression seq;

    public BeginExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        this.seq = new SequenceExpression(1, list, analyzer);
    }

    @Override
    public Expression eval(Environment env) {
        return seq.eval(env);
    }

}
