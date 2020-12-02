package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.TrueExpression;

import java.util.ArrayList;
import java.util.List;

public class OrExpression extends BaseExpression {

    private final List<Expression> args;

    public OrExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        assertAtLeastNumArgs(list, 2);
        this.args = new ArrayList<>(2);
        for (int i = 1; i < list.size(); i++) {
            this.args.add(analyzer.analyze(list.get(i)));
        }
    }

    @Override
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public Expression eval(Environment env) {
        for (int i = 0; i < args.size(); i++) {
            Expression e = args.get(i);
            Expression eval = e.eval(env);
            if (eval == TrueExpression.INSTANCE)
                return TrueExpression.INSTANCE;
        }
        return FalseExpression.INSTANCE;
    }

}
