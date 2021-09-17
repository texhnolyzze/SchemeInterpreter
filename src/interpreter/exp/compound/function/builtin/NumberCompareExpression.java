package interpreter.exp.compound.function.builtin;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.NumberExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public abstract class NumberCompareExpression implements BuiltInFunction {

    protected NumberCompareExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        Util.assertNumArgs(0, args, 2, this);
        final Expression left = args.get(0).eval(env);
        Util.assertNotNull(left, this);
        Util.assertType(left, NumberExpression.class, this);
        final Expression right = args.get(1).eval(env);
        Util.assertNotNull(right, this);
        Util.assertType(right, NumberExpression.class, this);
        final long cmp = ((NumberExpression) left).compare(((NumberExpression) right));
        return matches(cmp) ? TrueExpression.INSTANCE : FalseExpression.INSTANCE;
    }

    protected abstract boolean matches(long compare);

}
