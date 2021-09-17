package interpreter.exp.compound.function.builtin;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.NumberExpression;

import java.util.List;

public abstract class NumberCombineExpression implements BuiltInFunction {

    protected NumberCombineExpression() {
    }

    @Override
    @SuppressWarnings("ForLoopReplaceableByForEach")
    public Expression eval(Environment env, List<Expression> args) {
        args = modify(args);
        NumberExpression prev = null;
        NumberExpression res = null;
        for (int i = 0; i < args.size(); i++) {
            final Expression arg = args.get(i).eval(env);
            Util.assertNotNull(arg);
            Util.assertType(arg, NumberExpression.class);
            if (res == null) {
                NumberExpression number = (NumberExpression) arg;
                res = mustCopy() ? number.copy() : number;
            } else {
                res = combine(res, ((NumberExpression) arg));
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
