package interpreter.exp.self;

import interpreter.Environment;
import interpreter.exp.Expression;

import java.util.Map;

public class PairExpression extends SelfEvaluatingExpression {

    private Expression car;
    private Expression cdr;

    private PairExpression(Expression car, Expression cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    public Expression car() {
        return car;
    }

    public Expression cdr() {
        return cdr;
    }

    public void setCar(Expression car) {
        this.car = car;
    }

    public void setCdr(Expression cdr) {
        this.cdr = cdr;
    }

    public static PairExpression cons(Expression car, Expression cdr) {
        return new PairExpression(car, cdr);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append('(');
        PairExpression curr = this;
        while (true) {
            builder.append(curr.car);
            builder.append(' ');
            if (curr.cdr instanceof PairExpression pe) {
                curr = pe;
            } else {
                if (curr.cdr != NilExpression.INSTANCE) {
                    builder.append(curr.cdr);
                }
                break;
            }
        }
        if (builder.charAt(builder.length() - 1) == ' ') {
            builder.setLength(builder.length() - 1);
        }
        builder.append(')');
        return builder.toString();
    }

    @Override
    public Expression expand(final Map<String, Expression> params, final Environment env) {
        return new PairExpression(
            car.expand(params, env),
            cdr.expand(params, env)
        );
    }

}
