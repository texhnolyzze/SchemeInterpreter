package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;

import java.util.List;
import java.util.Map;

public class EvalExpression extends BaseExpression {
    
    private final Expression expression;
    private final Analyzer analyzer;

    public EvalExpression(List<?> list, Analyzer analyzer) {
        super(list);
        Util.assertNumArgs(list, 1);
        this.expression = analyzer.analyze(list.get(1));
        this.analyzer = analyzer;
    }

    private EvalExpression(
        final Expression expression,
        final Analyzer analyzer
    ) {
        super(null);
        this.expression = expression;
        this.analyzer = analyzer;
    }

    @Override
    public Expression eval(Environment env) {
        Expression eval = expression.eval(env);
        return trampoline(analyzer.analyze(QuoteExpression.unquote(eval)), env);
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return new EvalExpression(
            expression.expand(params, env),
            analyzer
        );
    }

}
