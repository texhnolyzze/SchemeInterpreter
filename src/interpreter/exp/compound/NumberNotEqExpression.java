package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.BooleanExpression;

import java.util.List;

public class NumberNotEqExpression extends CompoundExpression {

    private final NumberEqExpression exp;

    public NumberNotEqExpression(List<Object> list, Analyzer analyzer) {
        super(list, analyzer);
        this.exp = new NumberEqExpression(list, analyzer);
    }

    @Override
    public Expression eval(Environment env) {
        return ((BooleanExpression) exp.eval(env)).not();
    }

}
