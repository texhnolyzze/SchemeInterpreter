package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.TrueExpression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CondExpression extends BaseExpression {

    private final List<List<Expression>> conditions;

    public CondExpression(List<?> list, Analyzer analyzer) {
        super(list);
        Util.assertAtLeastNumArgs(list, 1);
        this.conditions = new ArrayList<>(2);
        for (int i = 1; i < list.size(); i++) {
            Object o = list.get(i);
            Util.assertList(o);
            List<?> condition = (List<?>) o;
            Util.assertNumArgs(0, condition, 2);
            final boolean isElse = condition.get(0).equals("else");
            if (isElse && i != list.size() - 1) {
                throw new IllegalArgumentException("'else' must be last statement in 'cond' expression");
            }
            Expression predicate = isElse ? TrueExpression.INSTANCE : analyzer.analyze(condition.get(0));
            this.conditions.add(List.of(predicate, analyzer.analyze(condition.get(1))));
        }
    }

    private CondExpression(
        final List<List<Expression>> conditions
    ) {
        super(null);
        this.conditions = conditions;
    }

    @Override
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public Expression eval(Environment env) {
        for (int i = 0; i < conditions.size(); i++) {
            List<Expression> condition = conditions.get(i);
            Expression predicate = condition.get(0);
            Expression eval = predicate.eval(env);
            if (eval == TrueExpression.INSTANCE) {
                return trampoline(condition.get(1), env);
            }
        }
        return FalseExpression.INSTANCE;
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        final List<List<Expression>> conds = new ArrayList<>(conditions.size());
        for (int i = 0; i < conditions.size(); i++) {
            final List<Expression> expressions = conditions.get(i);
            final List<Expression> expanded = new ArrayList<>(expressions.size());
            for (int j = 0; j < expressions.size(); j++) {
                expanded.add(expressions.get(j).expand(params, env));
            }
            conds.add(expanded);
        }
        return new CondExpression(conds);
    }

    @Override
    public String toString() {
        return "(cond " + conditions.stream().map(cond -> "(" + cond.get(0).toString() + ") (" + cond.get(1).toString() + ")").collect(Collectors.joining(" ")) + ")";
    }

}
