package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;

import java.util.List;

public class ForceExpression extends BaseExpression {

    private final ApplyExpression force;

    public ForceExpression(List<?> list, Analyzer analyzer) {
        super(list);
        Util.assertNumArgs(list, 1);
        this.force = new ApplyExpression(List.of(list.get(1)), analyzer);
    }

    @Override
    public Expression eval(Environment env) {
        return trampoline(force, env);
    }

}
