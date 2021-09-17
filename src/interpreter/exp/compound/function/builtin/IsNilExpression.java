package interpreter.exp.compound.function.builtin;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.NilExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public class IsNilExpression implements BuiltInFunction {

    public static final IsNilExpression INSTANCE = new IsNilExpression();

    private IsNilExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        Util.assertNumArgs(0, args, 1, this);
        final Expression arg = args.get(0).eval(env);
        return arg.eval(env) == NilExpression.INSTANCE ?
               TrueExpression.INSTANCE :
               FalseExpression.INSTANCE;
    }

    @Override
    public String toString() {
        return "null?";
    }

}
