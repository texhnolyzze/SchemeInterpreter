package interpreter;

import interpreter.exp.Expression;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public record Environment(
    Environment parent,
    Map<String, Expression> bindings
) {

    public Environment(
        Environment parent,
        Map<String, Expression> bindings
    ) {
        this.parent = parent;
        this.bindings = new HashMap<>(bindings);
    }

    public void define(
        String key,
        Expression val
    ) {
        bindings.put(key, val);
    }

    public void set(
        String key,
        Expression val
    ) {
        Environment env = this;
        do {
            if (bindings.computeIfPresent(key, (k, v) -> val) == val) {
                return;
            }
            env = env.parent;
        } while (env != null);
        throw new IllegalArgumentException("Variable " + key + " is undefined");
    }

    public Expression lookup(String key) {
        Environment env = this;
        do {
            Expression res = env.bindings.get(key);
            if (res != null) {
                return res;
            }
            env = env.parent;
        } while (env != null);
        throw new IllegalArgumentException("Variable " + key + " is undefined");
    }

    public static Environment create(Map<String, Expression> bindings) {
        return new Environment(null, bindings);
    }

    public Environment extend(Map<String, Expression> bindings) {
        return new Environment(this, bindings);
    }

    public Set<Map.Entry<String, Expression>> entries() {
        return bindings.entrySet();
    }

}
