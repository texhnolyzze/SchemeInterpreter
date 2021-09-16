package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.self.TrueExpression;

import java.util.List;
import java.util.Map;

public class IfExpression extends BaseExpression {

    private final Expression predicate;
    private final Expression consequent;
    private final Expression alternative;

    public IfExpression(List<?> list, Analyzer analyzer) {
        super(list);
        Util.assertNumArgs(list, 3);
        this.predicate = analyzer.analyze(list.get(1));
        this.consequent = analyzer.analyze(list.get(2));
        this.alternative = analyzer.analyze(list.get(3));
    }

    private IfExpression(
        final Expression predicate,
        final Expression consequent,
        final Expression alternative
    ) {
        super(null);
        this.predicate = predicate;
        this.consequent = consequent;
        this.alternative = alternative;
    }

    @Override
    public Expression eval(Environment env) {
        Expression eval = predicate.eval(env);
        if (eval == TrueExpression.INSTANCE) {
            return trampoline(consequent, env);
        }
        return trampoline(alternative, env);
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return new IfExpression(
            predicate.expand(params, env),
            consequent.expand(params, env),
            alternative.expand(params, env)
        );
    }

}
