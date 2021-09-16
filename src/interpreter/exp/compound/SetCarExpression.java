package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.self.PairExpression;

import java.util.List;
import java.util.Map;

public class SetCarExpression extends BaseExpression {

    private final Expression target;
    private final Expression value;

    public SetCarExpression(List<?> list, Analyzer analyzer) {
        super(list);
        Util.assertNumArgs(list, 2);
        this.target = analyzer.analyze(list.get(1));
        this.value = analyzer.analyze(list.get(2));
    }

    private SetCarExpression(
        final Expression target,
        final Expression value
    ) {
        super(null);
        this.target = target;
        this.value = value;
    }

    @Override
    public Expression eval(Environment env) {
        Expression eval = target.eval(env);
        Util.assertType(eval, PairExpression.class);
        PairExpression pair = (PairExpression) eval;
        Expression prev = pair.car();
        pair.setCar(value.eval(env));
        return prev;
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return new SetCarExpression(
            target.expand(params, env),
            value.expand(params, env)
        );
    }

    @Override
    public String toString() {
        return "(set-car! " + target.toString() + " " + value.toString() + ")";
    }

}
