package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class DelayExpression extends BaseExpression {

    private final LambdaExpression lambda;

    public DelayExpression(List<?> list, Analyzer analyzer) {
        super(list);
        Util.assertAtLeastNumArgs(list, 1);
        List<Object> lambdaList = new ArrayList<>(1);
        lambdaList.add("lambda");
        lambdaList.add(emptyList());
        Util.append(lambdaList, list.subList(1, list.size()));
        this.lambda = new LambdaExpression(lambdaList, analyzer);
    }

    @Override
    public Expression eval(Environment env) {
        return lambda.eval(env);
    }

}
