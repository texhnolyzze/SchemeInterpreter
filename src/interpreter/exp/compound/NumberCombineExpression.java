package interpreter.exp.compound;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.procedure.BuiltInProcedure;
import interpreter.exp.self.NumberExpression;

import java.util.List;

public abstract class NumberCombineExpression implements BuiltInProcedure {

    protected NumberCombineExpression() {
    }

    @Override
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public Expression eval(Environment env, List<Expression> args) {
        args = modify(args);
        NumberExpression prev = null;
        NumberExpression res = null;
        for (int i = 0; i < args.size(); i++) {
            Expression e = args.get(i);
            Expression eval = e.eval(env);
            Util.assertNotNull(eval);
            Util.assertType(eval, NumberExpression.class);
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
    protected List<Expression> modify(final List<Expression> args) {
        return args;
    }

}
