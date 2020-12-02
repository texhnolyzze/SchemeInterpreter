package interpreter.exp.self;

public class DecimalExpression extends NumberExpression {

    private double value;

    public DecimalExpression(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    @Override
    public long longValue() {
        return (long) value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public long compare(NumberExpression right) {
        return Double.compare(value, right.doubleValue());
    }

    @Override
    public NumberExpression add(NumberExpression right) {
        this.value += right.doubleValue();
        return this;
    }

    @Override
    public NumberExpression copy() {
        return new DecimalExpression(value);
    }

    @Override
    public NumberExpression sub(NumberExpression right) {
        this.value -= right.doubleValue();
        return this;
    }

    @Override
    public NumberExpression mul(NumberExpression right) {
        this.value *= right.doubleValue();
        return this;
    }

    @Override
    public NumberExpression mod(NumberExpression right) {
        this.value %= right.doubleValue();
        return this;
    }

    @Override
    public NumberExpression div(NumberExpression right) {
        this.value /= right.doubleValue();
        return this;
    }

}
