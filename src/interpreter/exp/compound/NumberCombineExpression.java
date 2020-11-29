package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.NumberExpression;

import java.util.ArrayList;
import java.util.List;

public abstract class NumberCombineExpression extends CompoundExpression {

    private final List<Expression> args;

    public NumberCombineExpression(List<Object> list, Analyzer analyzer) {
        super(list, analyzer);
        assertAtLeastNumArgs(list, 2);
        this.args = new ArrayList<>(2);
        for (int i = 1; i < list.size(); i++) {
            this.args.add(analyzer.analyze(list.get(i)));
        }
    }

    @Override
    public final Expression eval(Environment env) {
        NumberExpression res = null;
        for (Expression e : args) {
            Expression eval = e.eval(env);
            assertNotNull(eval);
            assertType(eval, NumberExpression.class);
            res = res == null ? ((NumberExpression) eval).copy() : combine(res, (NumberExpression) eval);
        }
        return res;
    }

    protected abstract NumberExpression combine(NumberExpression left, NumberExpression right);

}
