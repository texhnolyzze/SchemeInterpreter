package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.List;

public class ApplyExpression extends CompoundExpression {

    public ApplyExpression(List<Object> list, Analyzer analyzer) {
        super(list, analyzer);
        assertNumArgs(list, 1);
    }

    @Override
    public Expression eval(Environment env) {
        return null;
    }

}
