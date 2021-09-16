package interpreter.exp;

import interpreter.Environment;

import java.util.Map;

public interface Expression {
    Expression eval(final Environment env);
    Expression expand(final Map<String, Expression> params, final Environment env);
}
