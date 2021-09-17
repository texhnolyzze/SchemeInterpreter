package interpreter;

import interpreter.exp.Expression;
import interpreter.exp.VariableExpression;
import interpreter.exp.compound.ApplyExpression;
import interpreter.exp.compound.function.BuiltInFunction;
import interpreter.exp.self.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class Analyzer {

    private static final Pattern INT = Pattern.compile("[-+]?\\d+");
    private static final Pattern DEC = Pattern.compile("[-+]?\\d+.\\d+");

    private final Map<String, Class<? extends Expression>> predefined;

    private final ConcurrentMap<Class<? extends Expression>, Constructor<? extends Expression>> constructors;

    public Analyzer(Map<String, Class<? extends Expression>> predefined) {
        this.predefined = predefined;
        this.constructors = new ConcurrentHashMap<>();
    }

    @SuppressWarnings("unchecked")
    public Expression analyze(Object exp) {
        Expression expression = analyzeString(exp);
        if (expression != null) {
            return expression;
        } else { // list
            return analyzeList((List<Object>) exp);
        }
    }

    private Expression analyzeString(Object exp) {
        if (exp.getClass() == String.class) {
            String s = (String) exp;
            if (s.startsWith("\"")) {
                return new StringExpression(s);
            } else if (INT.matcher(s).matches()) {
                return new IntExpression(Long.parseLong(s));
            } else if (DEC.matcher(s).matches()) {
                return new DecimalExpression(Double.parseDouble(s));
            } else if (NilExpression.INSTANCE.toString().equals(s)) {
                return NilExpression.INSTANCE;
            } else if (TrueExpression.INSTANCE.toString().equals(s)) {
                return TrueExpression.INSTANCE;
            } else if (FalseExpression.INSTANCE.toString().equals(s)) {
                return FalseExpression.INSTANCE;
            } else if (predefined.containsKey(s) && BuiltInFunction.class.isAssignableFrom(predefined.get(s))) {
                return instance(predefined.get(s));
            } else {
                return new VariableExpression(s);
            }
        }
        return null;
    }

    private Expression analyzeList(List<Object> exp) {
        if (exp.isEmpty()) {
            throw new EvaluationException("Empty expression");
        }
        Object type = exp.get(0);
        Class<? extends Expression> c = predefined.get(type);
        if (c != null) {
            if (SelfEvaluatingExpression.class.isAssignableFrom(c)) {
                return instance(c);
            } else if (BuiltInFunction.class.isAssignableFrom(c)) {
                return new ApplyExpression(exp, this);
            } else {
                Constructor<? extends Expression> constructor = constructors.computeIfAbsent(
                    c,
                    clazz -> (Constructor<? extends Expression>) clazz.getConstructors()[0]
                );
                final Class<?>[] params = constructor.getParameterTypes();
                try {
                    if (params.length == 2 && params[0] == List.class && params[1] == Analyzer.class) {
                        return constructor.newInstance(exp, this);
                    } else if (params.length == 1 && params[0] == List.class) {
                        return constructor.newInstance(exp);
                    } else {
                        throw new AssertionError();
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    if (e.getCause() instanceof EvaluationException ee) {
                        throw ee;
                    } else {
                        throw new AssertionError(e);
                    }
                }
            }
        } else {
            return new ApplyExpression(exp, this);
        }
    }

    private Expression instance(final Class<? extends Expression> c) {
        try {
            return (Expression) c.getField("INSTANCE").get(c);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new AssertionError(e);
        }
    }

    public Map<String, Class<? extends Expression>> predefined() {
        return predefined;
    }

}
