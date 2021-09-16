package interpreter.exp.compound;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.StringExpression;

import java.util.List;

public class RaiseExpression implements BuiltInFunction {

    public static final RaiseExpression INSTANCE = new RaiseExpression();

    private RaiseExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        final Expression msg = args.get(0).eval(env);
        Util.assertType(msg, StringExpression.class);
        final Object[] formatArgs = args.stream().skip(1).map(e -> e.eval(env)).toArray();
        throw new IllegalArgumentException(
            String.format(
                msg.toString(),
                formatArgs
            )
        );
    }

}
