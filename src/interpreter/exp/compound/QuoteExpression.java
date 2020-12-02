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
        assertNumArgs(list, 1);
        this.arg = INTERNED.computeIfAbsent(list.get(1), o -> quote(list.get(1), analyzer));
    }

    private Expression quote(Object o, Analyzer analyzer) {
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
                            curr.setCar(quote(quoted, analyzer));
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
    }

    public static Object unquote(Expression exp) {
        if (exp instanceof PairExpression) {
            PairExpression pair = (PairExpression) exp;
            Expression curr;
            List<Object> res = new ArrayList<>();
            do {
                res.add(unquote(pair.car()));
                curr = pair.cdr();
                if (curr == NilExpression.INSTANCE)
                    break;
                pair = (PairExpression) curr;
            } while (true);
            return res;
        } else {
            return exp.toString();
        }
    }

    @Override
    public Expression eval(Environment env) {
        return arg;
    }

}
