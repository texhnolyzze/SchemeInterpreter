package interpreter.exp.compound.function.builtin;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public class AndExpression implements BuiltInFunction {

    public static final AndExpression INSTANCE = new AndExpression();

    private AndExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        Util.assertAtLeastNumArgs(0, args, 2);
        for (int i = 0; i < args.size(); i++) {
            Expression e = args.get(i);
            Expression eval = e.eval(env);
            if (eval == FalseExpression.INSTANCE) {
                return FalseExpression.INSTANCE;
            }
        }
        return TrueExpression.INSTANCE;
    }

    @Override
    public String toString() {
        return "and";
    }

}
