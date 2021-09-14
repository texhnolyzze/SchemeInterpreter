package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.NilExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public class IsNilExpression extends BaseExpression {

    private final Expression arg;

    public IsNilExpression(List<?> list, Analyzer analyzer) {
        super(list);
        assertNumArgs(list, 1);
        this.arg = analyzer.analyze(list.get(1));
    }

    @Override
    public Expression eval(Environment env) {
        return arg.eval(env) == NilExpression.INSTANCE ? TrueExpression.INSTANCE : FalseExpression.INSTANCE;
    }

}
