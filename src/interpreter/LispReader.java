package interpreter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class LispReader {

    public List<Object> read(String exp) {
        if (exp.isBlank())
            return Collections.emptyList();
        List<Object> result = new ArrayList<>(1);
        int from = 0;
        do {
            Object[] next = read(exp, from);
            result.add(next[0]);
            from = (int) next[1];
        } while (from < exp.length());
        return result;
    }

    private Object[] read(String exp, int from) {
        StringBuilder next = new StringBuilder();
        Object[] result = null;
        LinkedList<Character> parenthesis = new LinkedList<>();
        LinkedList<List<Object>> stack = new LinkedList<>();
        int stringStart = -1;
        boolean readingSymbol = false;
        boolean readingString = false;
        int i = from;
        while (i < exp.length()) {
            char c = exp.charAt(i);
            if (readingString) {
                if (c == '\\') {
                    i = backslash(exp, next, i);
                } else if (c == '"') {
                    if (stack.isEmpty()) {
                        result = cons(next.toString(), i + 1);
                        break;
                    }
                    readingString = finalizeString(stack, next);
                } else
                    next.append(c);
            } else {
                throwOn(c == '\\', "Unexpected backslash", i);
                if (c == '(') {
                    if (stack.isEmpty() && readingSymbol) {
                        result = cons(next.toString(), i);
                        break;
                    }
                    readingSymbol = finalizeSymbol(stack, next, readingSymbol);
                    parenthesis.push(c);
                    List<Object> list = new ArrayList<>();
                    if (!stack.isEmpty()) {
                        stack.peek().add(list);
                    }
                    stack.push(list);
                } else if (c == ')') {
                    throwOn(parenthesis.isEmpty(), "Missing open parenthesis", i);
                    throwOn(parenthesis.peek() != '(', "Unopened ')'", i);
                    readingSymbol = finalizeSymbol(stack, next, readingSymbol);
                    parenthesis.pop();
                    List<Object> list = stack.pop();
                    if (stack.isEmpty()) {
                        result = cons(list, i + 1);
                        break;
                    }
                } else if (Character.isWhitespace(c)) {
                    if (stack.isEmpty() && readingSymbol) {
                        result = cons(next.toString(), i + 1);
                        break;
                    }
                    readingSymbol = finalizeSymbol(stack, next, readingSymbol);

                } else if (c == '"') {
                    if (stack.isEmpty() && readingSymbol) {
                        result = cons(next.toString(), i);
                        break;
                    }
                    readingSymbol = finalizeSymbol(stack, next, readingSymbol);
                    readingString = true;
                    stringStart = i;
                    next.append(c);
                } else {
                    readingSymbol = true;
                    next.append(c);
                }
            }
            i++;
        }
        throwOn(readingString, "Unclosed string literal starting", stringStart);
        throwOn(!stack.isEmpty(), "Unexpected EOF");
        if (result == null && readingSymbol)
            result = cons(next.toString(), exp.length());
        return result;
    }

    private Object[] cons(Object res, int i) {
        return new Object[] {res, i};
    }

    private boolean finalizeSymbol(LinkedList<List<Object>> stack, StringBuilder symbol, boolean readingSymbol) {
        if (readingSymbol) {
            stack.peek().add(symbol.toString());
            symbol.setLength(0);
        }
        return false;
    }

    private boolean finalizeString(LinkedList<List<Object>> stack, StringBuilder next) {
        next.append('"');
        stack.peek().add(next.toString());
        next.setLength(0);
        return false;
    }

    @SuppressWarnings("ConstantConditions")
    private int backslash(String exp, StringBuilder next, int i) {
        throwOn(i + 1 >= exp.length(), "Unexpected backslash", i);
        switch (exp.charAt(i + 1)) {
            case '\\':
                next.append('\\');
                break;
            case '"':
                next.append('"');
                break;
            case 'n':
                next.append('\n');
                break;
            case 't':
                next.append('\t');
                break;
            case 'r':
                next.append('\r');
                break;
            default:
                throwOn(true, "Unexpected escaped character", i);
        }
        return ++i;
    }

    private void throwOn(boolean cond, String msg, int position) {
        if (cond)
            throw new IllegalArgumentException(msg + " at " + position);
    }

    private void throwOn(boolean cond, String msg) {
        if (cond)
            throw new IllegalArgumentException(msg);
    }

}
