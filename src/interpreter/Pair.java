package interpreter;

public class Pair<CAR, CDR> {

    private final CAR car;
    private final CDR cdr;

    private Pair(CAR car, CDR cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    public CAR car() {
        return car;
    }

    public CDR cdr() {
        return cdr;
    }

    public static <CAR, CDR> Pair<CAR, CDR> cons(CAR car, CDR cdr) {
        return new Pair<>(car, cdr);
    }

}
