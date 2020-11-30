package interpreter.exp.compound;

import interpreter.Analyzer;

import java.util.List;

public class NumberNotEqExpression extends NumberCompareExpression {

    public NumberNotEqExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
    }

    @Override
    protected boolean matches(long compare) {
        return compare != 0;
    }

}
