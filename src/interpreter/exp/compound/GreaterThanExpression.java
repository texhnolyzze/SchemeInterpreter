package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.BooleanExpression;

import java.util.List;

public class GreaterThanExpression extends CompoundExpression {

    private final LessThanExpression exp;

    public GreaterThanExpression(List<Object> list, Analyzer analyzer) {
        super(list, analyzer);
        this.exp = new LessThanExpression(list, analyzer);
    }

    @Override
    public Expression eval(Environment env) {
        return ((BooleanExpression) exp.eval(env)).not();
    }

}
