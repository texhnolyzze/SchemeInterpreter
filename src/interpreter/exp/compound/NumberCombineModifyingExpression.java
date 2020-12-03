package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.NumberExpression;

import java.util.List;
import java.util.Map;

public abstract class NumberCombineModifyingExpression extends NumberCombineExpression {

    protected NumberCombineModifyingExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
    }

    @Override
    protected boolean mustCopy() {
        return false;
    }

    @Override
    protected void onTypeChanged(Environment env, NumberExpression prev, NumberExpression newType) {
        for (Map.Entry<String, Expression> e : env.entries()) {
            if (e.getValue() == prev) {
                e.setValue(newType);
                return;
            }
        }
    }

}
