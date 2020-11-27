package interpreter;

import java.util.HashMap;
import java.util.Map;

public class Environment {

    private final Environment root;
    private final Map<String, Object> bindings;

    private Environment(Environment root, Map<String, Object> bindings) {
        this.root = root;
        this.bindings = new HashMap<>(bindings);
    }

    public void define(String key, Object val) {
        bindings.put(key, val);
    }

    public void set(String key, Object val) {
        if (!bindings.containsKey(key))
            throw new IllegalArgumentException("Variable " + key + " is undefined");
        bindings.put(key, val);
    }

    public Object lookup(String key) {
        Environment env = this;
        do {
            Object res = env.bindings.get(key);
            if (res != null)
                return res;
            env = env.root;
        } while (env != null);
        throw new IllegalArgumentException("Variable " + key + " is undefined");
    }

    public static Environment create(Map<String, Object> bindings) {
        return new Environment(null, bindings);
    }

    public static Environment extend(Environment root, Map<String, Object> bindings) {
        return new Environment(root, bindings);
    }

}
