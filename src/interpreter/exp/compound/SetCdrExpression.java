package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.self.PairExpression;

import java.util.List;
import java.util.Map;

public class SetCdrExpression extends BaseExpression {

    private final Expression target;
    private final Expression value;

    public SetCdrExpression(List<?> list, Analyzer analyzer) {
        Util.assertNumArgs(list, 2);
        this.target = analyzer.analyze(list.get(1));
        this.value = analyzer.analyze(list.get(2));
    }

    private SetCdrExpression(
        final Expression target,
        final Expression value
    ) {
        this.target = target;
        this.value = value;
    }

    @Override
    public Expression eval(Environment env) {
        Expression eval = target.eval(env);
        Util.assertType(eval, PairExpression.class);
        PairExpression pair = (PairExpression) eval;
        Expression prev = pair.cdr();
        pair.setCdr(value.eval(env));
        return prev;
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return new SetCdrExpression(
            target.expand(params, env),
            value.expand(params, env)
        );
    }

    @Override
    public String toString() {
        return "(set-cdr! " + target.toString() + " " + value.toString() + ")";
    }

}
