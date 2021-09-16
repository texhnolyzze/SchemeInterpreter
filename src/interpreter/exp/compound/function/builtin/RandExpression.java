package interpreter.exp.compound.function.builtin;

import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.IntExpression;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandExpression implements BuiltInFunction {

    public static final RandExpression INSTANCE = new RandExpression();

    private RandExpression() {
    }

    @Override
    public Expression eval(
        final Environment env,
        final List<Expression> args
    ) {
        return new IntExpression(ThreadLocalRandom.current().nextInt());
    }

    @Override
    public String toString() {
        return "rand";
    }

}
