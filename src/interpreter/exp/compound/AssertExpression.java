package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.self.NilExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;
import java.util.Map;

public class AssertExpression extends BaseExpression {

    private final Expression expr;

    public AssertExpression(
        final List<?> list,
        final Analyzer analyzer
    ) {
        super(list);
        Util.assertNumArgs(list, 1);
        this.expr = analyzer.analyze(list.get(1));
    }

    private AssertExpression(final Expression expr) {
        super(null);
        this.expr = expr;
    }

    @Override
    public Expression eval(final Environment env) {
        final Expression eval = expr.eval(env);
        if (eval != TrueExpression.INSTANCE) {
            throw new IllegalArgumentException("Assertion failed: " + eval);
        }
        return NilExpression.INSTANCE;
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return new AssertExpression(expr.expand(params, env));
    }

}
