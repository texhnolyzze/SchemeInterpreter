package interpreter.exp;

import interpreter.Environment;

public record VariableExpression(String name) implements Expression {

    @Override
    public Expression eval(Environment env) {
        return env.lookup(name);
    }

    @Override
    public String toString() {
        return name;
    }

}
