package interpreter.exp.compound;

import interpreter.Analyzer;
import interpreter.Environment;
import interpreter.exp.Expression;
import interpreter.exp.Util;
import interpreter.exp.self.NilExpression;
import interpreter.exp.self.PairExpression;
import interpreter.exp.self.SymbolExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuoteExpression extends BaseExpression {

    /**
     * Interpreter is single-threaded, so no need to use ConcurrentMap
     */
    private static final Map<Object, Expression> INTERNED = new HashMap<>();

    private final Expression arg;

    public QuoteExpression(List<?> list, Analyzer analyzer) {
        super(list);
        Util.assertNumArgs(list, 1);
        this.arg = INTERNED.computeIfAbsent(list.get(1), o -> quote(list.get(1)));
    }

    private Expression quote(Object o) {
        if (o instanceof List<?> list) {
            if (list.isEmpty()) {
                return  NilExpression.INSTANCE;
            } else {
                PairExpression curr = PairExpression.cons(NilExpression.INSTANCE, NilExpression.INSTANCE);
                PairExpression head = curr;
                for (int i = 0;;) {
                    Object next = list.get(i++);
                    curr.setCar(quote(next));
                    if (i < list.size()) {
                        PairExpression pair = PairExpression.cons(NilExpression.INSTANCE, NilExpression.INSTANCE);
                        curr.setCdr(pair);
                        curr = pair;
                    } else {
                        break;
                    }
                }
                return head;
            }
        } else {
            return new SymbolExpression((String) o);
        }
    }

    public static Object unquote(Expression exp) {
        if (exp instanceof PairExpression pair) {
            Expression curr;
            List<Object> res = new ArrayList<>(8);
            do {
                res.add(unquote(pair.car()));
                curr = pair.cdr();
                if (curr == NilExpression.INSTANCE) {
                    break;
                }
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
