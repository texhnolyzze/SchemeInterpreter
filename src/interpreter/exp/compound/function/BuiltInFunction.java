package interpreter.exp.compound.function;

import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.List;

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

}
