package interpreter.exp.self;

import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.HashMap;
import java.util.Map;

public class SymbolExpression extends SelfEvaluatingExpression {

    private static final Map<String, SymbolExpression> SYMBOLS = new HashMap<>();

    private final String sym;

    private SymbolExpression(String sym) {
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

    public static SymbolExpression valueOf(final String sym) {
        return SYMBOLS.computeIfAbsent(
            sym,
            SymbolExpression::new
        );
    }

}
