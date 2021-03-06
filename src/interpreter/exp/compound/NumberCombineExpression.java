package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.NumberExpression;

import java.util.ArrayList;
import java.util.List;

public abstract class NumberCombineExpression extends BaseExpression {

    private final List<Expression> args;

    protected NumberCombineExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        assertAtLeastNumArgs(list, 2);
        this.args = new ArrayList<>(2);
        for (int i = 1; i < list.size(); i++) {
            this.args.add(analyzer.analyze(list.get(i)));
        }
    }

    @Override
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public final Expression eval(Environment env) {
        NumberExpression prev = null;
        NumberExpression res = null;
        for (int i = 0; i < args.size(); i++) {
            Expression e = args.get(i);
            Expression eval = e.eval(env);
            assertNotNull(eval);
            assertType(eval, NumberExpression.class);
            if (res == null) {
                NumberExpression number = (NumberExpression) eval;
                res = mustCopy() ? number.copy() : number;
            } else {
                res = combine(res, ((NumberExpression) eval));
                if (prev != res) {
                    onTypeChanged(env, prev, res);
                }
            }
            prev = res;
        }
        return res;
    }

    protected abstract NumberExpression combine(NumberExpression left, NumberExpression right);
    protected abstract boolean mustCopy();
    protected abstract void onTypeChanged(Environment env, NumberExpression prev, NumberExpression newType);

}
