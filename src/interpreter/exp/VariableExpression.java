package interpreter.exp;

import interpreter.Environment;

public class VariableExpression implements Expression {

    private final String var;

    public VariableExpression(String var) {
        this.var = var;
    }

    public String getName() {
        return var;
    }

    @Override
    public Expression eval(Environment env) {
        return env.lookup(var);
    }

    @Override
    public String toString() {
        return var;
    }

}
