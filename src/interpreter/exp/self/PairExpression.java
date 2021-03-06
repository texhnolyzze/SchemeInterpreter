package interpreter.exp.self;

import interpreter.exp.Expression;

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
        return "(" + car + " " + cdr + ")";
    }

}
