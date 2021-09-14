package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.PairExpression;

import java.util.List;

public class SetCdrExpression extends BaseExpression {

    private final Expression target;
    private final Expression value;

    public SetCdrExpression(List<?> list, Analyzer analyzer) {
        super(list);
        assertNumArgs(list, 2);
        this.target = analyzer.analyze(list.get(1));
        this.value = analyzer.analyze(list.get(2));
    }

    @Override
    public Expression eval(Environment env) {
        Expression eval = target.eval(env);
        assertType(eval, PairExpression.class);
        PairExpression pair = (PairExpression) eval;
        Expression prev = pair.cdr();
        pair.setCdr(value.eval(env));
        return prev;
    }

}
