package interpreter.exp.self;

import interpreter.exp.Expression;

public abstract class NumberExpression extends SelfEvaluatingExpression {

    public abstract long longValue();
    public abstract double doubleValue();

    public abstract long compare(NumberExpression right);
    public abstract NumberExpression add(NumberExpression right);
    public abstract NumberExpression copy();
    public abstract NumberExpression sub(NumberExpression eval);

}
