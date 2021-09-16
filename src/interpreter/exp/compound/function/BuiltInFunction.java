package interpreter.exp.compound.function;

import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.List;
import java.util.Map;

public interface BuiltInFunction extends Function {

    /**
     * No binding required
     */
    @Override
    default Environment bind(
        List<Expression> args,
        Environment callerEnvironment
    ) {
        return callerEnvironment;
    }

    @Override
    default Expression expand(
        Map<String, Expression> params,
        final Environment env
    ) {
        return this;
    }

}
