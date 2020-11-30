package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.PairExpression;

import java.util.List;

public class CdrExpression extends BaseExpression {

    private final Expression arg;

    public CdrExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        assertNumArgs(list, 1);
        this.arg = analyzer.analyze(list.get(1));
    }

    @Override
    public Expression eval(Environment env) {
        Expression eval = arg.eval(env);
        assertNotNull(eval);
        assertType(eval, PairExpression.class);
        return ((PairExpression) eval).cdr();
    }

}
