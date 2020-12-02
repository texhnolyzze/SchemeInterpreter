package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.NilExpression;
import interpreter.exp.self.PairExpression;
import interpreter.exp.self.SelfEvaluatingExpression;
import interpreter.exp.self.SymbolExpression;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class QuoteExpression extends BaseExpression {

    private final Expression arg;

    public QuoteExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        assertNumArgs(list, 1);
        Object o = list.get(1);
        if (o instanceof List) {
            if (((List<?>) o).isEmpty())
                arg = NilExpression.INSTANCE;
            else {
                PairExpression curr = PairExpression.cons(NilExpression.INSTANCE, NilExpression.INSTANCE);
                arg = curr;
                for (Iterator<?> iterator = ((List<?>) o).iterator(); iterator.hasNext(); ) {
                    Object quoted = iterator.next();
                    SelfEvaluatingExpression exp = analyzer.analyzeSelfEvaluatingExpression(quoted);
                    if (exp != null)
                        curr.setCar(exp);
                    else {
                        if (quoted instanceof List)
                            curr.setCar(new QuoteExpression((List<?>) quoted, analyzer).arg);
                        else {
                            curr.setCar(new SymbolExpression((String) quoted));
                        }
                    }
                    if (iterator.hasNext()) {
                        PairExpression next = PairExpression.cons(NilExpression.INSTANCE, NilExpression.INSTANCE);
                        curr.setCdr(next);
                        curr = next;
                    }
                }
            }
        } else {
            arg = Objects.requireNonNullElseGet(
                    analyzer.analyzeSelfEvaluatingExpression(o),
                    () -> new SymbolExpression((String) o)
            );
        }
    }

    @Override
    public Expression eval(Environment env) {
        return arg;
    }

}
