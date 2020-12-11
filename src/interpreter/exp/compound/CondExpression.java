package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.TrueExpression;

import java.util.ArrayList;
import java.util.List;

public class CondExpression extends BaseExpression {

    private final List<List<Expression>> conditions;

    public CondExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        assertAtLeastNumArgs(list, 1);
        this.conditions = new ArrayList<>(2);
        for (int i = 1; i < list.size(); i++) {
            Object o = list.get(i);
            assertList(o);
            List<?> condition = (List<?>) o;
            assertNumArgs(0, condition, 2);
            boolean isElse = condition.get(0).equals("else");
            if (isElse && i != list.size() - 1)
                throw new IllegalArgumentException("'else' must be last statement in 'cond' expression");
            Expression predicate = isElse ? TrueExpression.INSTANCE : analyzer.analyze(condition.get(0));
            this.conditions.add(List.of(predicate, analyzer.analyze(condition.get(1))));
        }
    }

    @Override
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public Expression eval(Environment env) {
        for (int i = 0; i < conditions.size(); i++) {
            List<Expression> condition = conditions.get(i);
            Expression predicate = condition.get(0);
            Expression eval = predicate.eval(env);
            if (eval == TrueExpression.INSTANCE)
                return trampoline(condition.get(1), env);
        }
        return FalseExpression.INSTANCE;
    }

}
