package interpreter.exp.compound.function.builtin;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public class OrExpression implements BuiltInFunction {

    public static final OrExpression INSTANCE = new OrExpression();

    private OrExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        Util.assertAtLeastNumArgs(0, args, 2);
        for (int i = 0; i < args.size(); i++) {
            final Expression e = args.get(i);
            final Expression eval = e.eval(env);
            if (eval == TrueExpression.INSTANCE) {
                return TrueExpression.INSTANCE;
            }
        }
        return FalseExpression.INSTANCE;
    }

}
