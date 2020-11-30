package interpreter.exp.compound;

import interpreter.Analyzer;

import java.util.List;

public class LessThanExpression extends NumberCompareExpression {

    public LessThanExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
    }

    @Override
    protected boolean matches(long compare) {
        return compare < 0;
    }

}
