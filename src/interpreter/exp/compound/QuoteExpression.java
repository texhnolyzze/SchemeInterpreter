package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.self.NilExpression;
import interpreter.exp.self.PairExpression;
import interpreter.exp.self.SelfEvaluatingExpression;
import interpreter.exp.self.SymbolExpression;

import java.util.*;

public class QuoteExpression extends BaseExpression {

    /**
     * Interpreter is single-threaded, so no need to use ConcurrentMap
     */
    private static final Map<Object, Expression> INTERNED = new HashMap<>();

    private final Expression arg;

    public QuoteExpression(List<?> list, Analyzer analyzer) {
        super(list, analyzer);
        this.arg = analyze(list, analyzer);
    }

    private Expression analyze(List<?> list, Analyzer analyzer) {
        assertNumArgs(list, 1);
        return INTERNED.computeIfAbsent(list.get(1), o -> {
            if (o instanceof List) {
                if (((List<?>) o).isEmpty())
                    return  NilExpression.INSTANCE;
                else {
                    PairExpression curr = PairExpression.cons(NilExpression.INSTANCE, NilExpression.INSTANCE);
                    PairExpression head = curr;
                    for (Iterator<?> iterator = ((List<?>) o).iterator(); iterator.hasNext(); ) {
                        Object quoted = iterator.next();
                        SelfEvaluatingExpression exp = analyzer.analyzeSelfEvaluatingExpression(quoted);
                        if (exp != null)
                            curr.setCar(exp);
                        else {
                            if (quoted instanceof List)
                                curr.setCar(analyze((List<?>) quoted, analyzer));
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
                    return head;
                }
            } else {
                return Objects.requireNonNullElseGet(
                        analyzer.analyzeSelfEvaluatingExpression(o),
                        () -> new SymbolExpression((String) o)
                );
            }
        });
    }

    @Override
    public Expression eval(Environment env) {
        return arg;
    }

}
