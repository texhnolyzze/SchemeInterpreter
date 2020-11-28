package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public class IfExpression extends CompoundExpression {

    private final Expression predicate;
    private final Expression consequent;
    private final Expression alternative;

    public IfExpression(List<Object> list, Analyzer analyzer) {
        super(list, analyzer);
        assertNumArgs(list, 3);
        this.predicate = analyzer.analyze(list.get(1));
        this.consequent = analyzer.analyze(list.get(2));
        this.alternative = analyzer.analyze(list.get(3));
    }

    @Override
    public Expression eval(Environment env) {
        Expression eval = predicate.eval(env);
        if (eval == TrueExpression.INSTANCE)
            return consequent.eval(env);
        return alternative.eval(env);
    }

}
