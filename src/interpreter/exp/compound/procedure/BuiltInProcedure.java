package interpreter.exp.compound.procedure;

import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.List;

public interface BuiltInProcedure extends Procedure {

    /**
     * No binding required
     */
    @Override
    default Environment bind(
        List<Expression> args,
        Environment environment
    ) {
        return environment;
    }

}
