package interpreter.exp.compound;

import interpreter.Analyzer;

import java.util.List;

public class GreaterThanExpression extends NumberCompareExpression {

    public GreaterThanExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
    }

    @Override
    protected boolean matches(long compare) {
        return compare > 0;
    }

}
