package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public class EqExpression extends CompoundExpression {

    private final Expression left;
    private final Expression right;

    public EqExpression(List<Object> list, Analyzer analyzer) {
        super(list, analyzer);
        assertNumArgs(list, 2);
        this.left = analyzer.analyze(list.get(1));
        this.right = analyzer.analyze(list.get(2));
    }

    @Override
    public Expression eval(Environment env) {
        return left.eval(env) == right.eval(env) ? TrueExpression.INSTANCE : FalseExpression.INSTANCE;
    }

}
