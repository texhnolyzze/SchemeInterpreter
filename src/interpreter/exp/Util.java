package interpreter.exp;

import interpreter.exp.self.NilExpression;

import java.util.List;

public final class Util {

    private Util() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void append(
        List left,
        List right
    ) {
        left.addAll(right);
    }

    public static void assertNumArgs(
        int offset,
        List<?> args,
        int expected
    ) {
        if (args.size() - offset != expected) {
            throw new IllegalArgumentException("Expected " + expected + " args, got " + (args.size() - offset));
        }
    }

    public static void assertNumArgs(
        List<?> args,
        int expected
    ) {
        assertNumArgs(1, args, expected);
    }

    public static void assertAtLeastNumArgs(
        int offset,
        List<?> args,
        int expected
    ) {
        if (args.size() - offset < expected) {
            throw new IllegalArgumentException("expected at least " + expected + " args, got " + (args.size() - offset));
        }
    }

    public static void assertAtLeastNumArgs(
        List<?> args,
        int expected
    ) {
        assertAtLeastNumArgs(1, args, expected);
    }

    public static void assertType(
        Object obj,
        Class<?> expected
    ) {
        if (!expected.isAssignableFrom(obj.getClass())) {
            throw new IllegalArgumentException("expected arg type is " + expected.getSimpleName() + ", actual is " + obj.getClass().getSimpleName());
        }
    }

    public static void assertNotNull(Expression expression) {
        if (expression == NilExpression.INSTANCE) {
            throw new IllegalArgumentException("nil pointer dereference");
        }
    }

    public static void assertSymbol(Object o) {
        final boolean symbol = o instanceof String s && s.charAt(0) != '"';
        if (!symbol) {
            throw new IllegalArgumentException("symbol expected, got " + o);
        }
    }

    public static void assertList(Object o) {
        if (!(o instanceof List)) {
            throw new IllegalArgumentException("list expected, got " + o);
        }
    }

}
