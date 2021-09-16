package interpreter.exp.self;

import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.Map;

public class SymbolExpression extends SelfEvaluatingExpression {

    private final String sym;

    public SymbolExpression(String sym) {
        this.sym = sym;
    }

    @Override
    public String toString() {
        return sym;
    }

    @Override
    public Expression expand(
        final Map<String, Expression> params,
        final Environment env
    ) {
        return params.getOrDefault(sym, this);
    }

}
