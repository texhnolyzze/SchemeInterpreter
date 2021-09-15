package interpreter.exp.compound.procedure;

import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.List;

public interface Procedure extends Expression {

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
        Environment environment
    );

}
