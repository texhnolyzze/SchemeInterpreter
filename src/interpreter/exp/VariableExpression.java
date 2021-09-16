package interpreter.exp;

import interpreter.Environment;

import java.util.Map;

public record VariableExpression(String name) implements Expression {

    @Override
    public Expression eval(Environment env) {
        return env.lookup(name);
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return params.getOrDefault(name, this);
    }

    @Override
    public String toString() {
        return name;
    }

}
