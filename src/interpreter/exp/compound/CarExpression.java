package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.PairExpression;

import java.util.List;

public class CarExpression extends BaseExpression {

    private final Expression arg;

    public CarExpression(List<?> list, Analyzer analyzer) {
        super(list);
        assertNumArgs(list, 1);
        this.arg = analyzer.analyze(list.get(1));
    }

    @Override
    public Expression eval(Environment env) {
        Expression eval = arg.eval(env);
        assertNotNull(eval);
        assertType(eval, PairExpression.class);
        return ((PairExpression) eval).car();
    }

}
