package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.NumberExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public abstract class NumberCompareExpression extends BaseExpression {

    private final Expression left;
    private final Expression right;

    protected NumberCompareExpression(List<?> list, Analyzer analyzer) {
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
        long compare = ((NumberExpression) l).compare(((NumberExpression) r));
        return matches(compare) ? TrueExpression.INSTANCE : FalseExpression.INSTANCE;
    }

    protected abstract boolean matches(long compare);

}
