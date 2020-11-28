package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.NumberExpressionUtil;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.NumberExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public class LessThanExpression extends CompoundExpression {

    private final Expression left;
    private final Expression right;

    public LessThanExpression(List<Object> list, Analyzer analyzer) {
        super(list, analyzer);
        assertNumArgs(list, 2);
        this.left = analyzer.analyze(list.get(1));
        this.right = analyzer.analyze(list.get(2));
    }

    @Override
    public Expression eval(Environment env) {
        Expression l = left.eval(env);
        assertNotNull(l);
        assertType(l, NumberExpression.class);
        Expression r = right.eval(env);
        assertNotNull(r);
        assertType(r, NumberExpression.class);
        return ((NumberExpression) l).compare(((NumberExpression) r)) < 0 ? TrueExpression.INSTANCE : FalseExpression.INSTANCE;
    }

}
