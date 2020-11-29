package interpreter.exp.self;

public abstract class NumberExpression extends SelfEvaluatingExpression {

    public abstract long longValue();
    public abstract double doubleValue();

    public abstract long compare(NumberExpression right);
    public abstract NumberExpression copy();

    public abstract NumberExpression add(NumberExpression right);
    public abstract NumberExpression sub(NumberExpression right);
    public abstract NumberExpression mul(NumberExpression right);
    public abstract NumberExpression mod(NumberExpression right);
    public abstract NumberExpression div(NumberExpression right);

}
