package interpreter.exp.self;

import interpreter.EvaluationException;

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
        try {
            this.value %= right.doubleValue();
        } catch (ArithmeticException e) {
            throw new EvaluationException(e.getMessage());
        }
        return this;
    }

    @Override
    public NumberExpression div(NumberExpression right) {
        try {
            this.value /= right.doubleValue();
        } catch (ArithmeticException e) {
            throw new EvaluationException(e.getMessage());
        }
        return this;
    }

}
