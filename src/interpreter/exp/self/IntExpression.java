package interpreter.exp.self;

import interpreter.EvaluationException;

public class IntExpression extends NumberExpression {

    public static final IntExpression ZERO = new IntExpression(0L);

    private long value;

    public IntExpression(long value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public long compare(NumberExpression right) {
        return right.getClass() == IntExpression.class ?
               Long.compare(value, right.longValue()) :
               Double.compare(doubleValue(), right.doubleValue());
    }

    @Override
    public NumberExpression add(NumberExpression right) {
        if (right.getClass() == IntExpression.class) {
            value += right.longValue();
            return this;
        }
        return new DecimalExpression(this.value + right.doubleValue());
    }

    @Override
    public NumberExpression copy() {
        return new IntExpression(value);
    }

    @Override
    public NumberExpression sub(NumberExpression right) {
        if (right.getClass() == IntExpression.class) {
            value -= right.longValue();
            return this;
        }
        return new DecimalExpression(this.value - right.doubleValue());
    }

    @Override
    public NumberExpression mul(NumberExpression right) {
        if (right.getClass() == IntExpression.class) {
            value *= right.longValue();
            return this;
        }
        return new DecimalExpression(this.value * right.doubleValue());
    }

    @Override
    public NumberExpression mod(NumberExpression right) {
        try {
            if (right.getClass() == IntExpression.class) {
                value %= right.longValue();
                return this;
            }
            return new DecimalExpression(this.doubleValue() % right.doubleValue());
        } catch (ArithmeticException e) {
            throw new EvaluationException(e.getMessage());
        }
    }

    @Override
    public NumberExpression div(NumberExpression right) {
        try {
            if (right.getClass() == IntExpression.class) {
                value /= right.longValue();
                return this;
            }
            return new DecimalExpression(this.doubleValue() / right.doubleValue());
        } catch (ArithmeticException e) {
            throw new EvaluationException(e.getMessage());
        }
    }

}
