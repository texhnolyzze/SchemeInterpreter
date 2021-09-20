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
        Util.assertNumArgs(list, 1);
        this.expression = analyzer.analyze(list.get(1));
        this.analyzer = analyzer;
    }

    private EvalExpression(
        final Expression expression,
        final Analyzer analyzer
    ) {
        this.expression = expression;
        this.analyzer = analyzer;
    }

    @Override
    public Expression eval(Environment env) {
        if (expression instanceof QuoteExpression qe) {
            return trampoline(
                analyzer.analyze(qe.unquote()),
                env
            );
        } else {
            return expression.eval(env);
        }
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

    @Override
    public String toString() {
        return "(eval " + expression.toString() + ")";
    }

}
