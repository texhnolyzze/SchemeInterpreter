package interpreter.exp.compound.function.builtin;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.FalseExpression;
import interpreter.exp.self.PairExpression;
import interpreter.exp.self.TrueExpression;

import java.util.List;

public class IsPairExpression implements BuiltInFunction {

    public static final IsPairExpression INSTANCE = new IsPairExpression();

    private IsPairExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        Util.assertNumArgs(0, args, 1, this);
        final Expression eval = args.get(0).eval(env);
        return eval instanceof PairExpression ?
               TrueExpression.INSTANCE :
               FalseExpression.INSTANCE;
    }

    @Override
    public String toString() {
        return "pair?";
    }

}
