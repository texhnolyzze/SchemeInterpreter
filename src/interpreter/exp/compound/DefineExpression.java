package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.List;

public class DefineExpression extends SetDefineExpression {

    public DefineExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
    }

    @Override
    protected void modify(Environment env, String var, Expression expression) {
        env.define(var, expression);
    }

}
