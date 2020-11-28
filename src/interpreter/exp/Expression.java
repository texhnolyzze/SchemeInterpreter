package interpreter.exp;

import interpreter.Environment;

public interface Expression {
    Expression eval(Environment env);
}
