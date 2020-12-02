package interpreter.exp.self;

public class SymbolExpression extends SelfEvaluatingExpression {

    private final String sym;

    public SymbolExpression(String sym) {
        this.sym = sym;
    }

    @Override
    public String toString() {
        return sym;
    }

}
