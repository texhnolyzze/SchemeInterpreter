package interpreter.exp.self;

public class StringExpression extends SelfEvaluatingExpression {

    private final String value;

    public StringExpression(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
