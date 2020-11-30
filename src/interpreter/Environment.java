package interpreter;

import interpreter.exp.Expression;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment {

    private final Environment root;
    private final Map<String, Expression> bindings;

    private Environment(Environment root, Map<String, Expression> bindings) {
        this.root = root;
        this.bindings = new HashMap<>(bindings);
    }

    public void define(String key, Expression val) {
        bindings.put(key, val);
    }

    public void set(String key, Expression val) {
        if (!bindings.containsKey(key))
            throw new IllegalArgumentException("Variable " + key + " is undefined");
        bindings.put(key, val);
    }

    public Expression lookup(String key) {
        Environment env = this;
        do {
            Expression res = env.bindings.get(key);
            if (res != null)
                return res;
            env = env.root;
        } while (env != null);
        throw new IllegalArgumentException("Variable " + key + " is undefined");
    }

    public Environment copy() {
        return new Environment(this.root, new HashMap<>(this.bindings));
    }

    public static Environment create(Map<String, Expression> bindings) {
        return new Environment(null, bindings);
    }

    public Environment extend(Map<String, Expression> bindings) {
        return new Environment(this, bindings);
    }

    public void retailAll(List<String> keys) {
        this.bindings.keySet().retainAll(keys);
    }

}
