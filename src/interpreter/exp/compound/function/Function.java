package interpreter.exp.compound.function;

import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.List;

public interface Function extends Expression {

    Expression eval(
        final Environment env,
        final List<Expression> args
    );

    @Override
    default Expression eval(Environment env) {
        return this;
    }

    Environment bind(
        List<Expression> args,
        Environment callerEnvironment
    );

}
