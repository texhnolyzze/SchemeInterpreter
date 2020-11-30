package interpreter.exp.compound;

import interpreter.Analyzer;

import java.util.List;

public class NumberEqExpression extends NumberCompareExpression {

    public NumberEqExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
    }

    @Override
    protected boolean matches(long compare) {
        return compare == 0;
    }

}
