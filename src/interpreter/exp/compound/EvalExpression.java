package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.List;

public class EvalExpression extends BaseExpression {
    
    private final Expression expression;
    private final Analyzer analyzer;

    public EvalExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        assertNumArgs(list, 1);
        this.expression = analyzer.analyze(list.get(1));
        this.analyzer = analyzer;
    }

    @Override
    public Expression eval(Environment env) {
        Expression eval = expression.eval(env);
        return trampoline(analyzer.analyze(QuoteExpression.unquote(eval)), env);
    }
    
}
