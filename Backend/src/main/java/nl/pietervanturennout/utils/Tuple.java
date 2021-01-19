package nl.pietervanturennout.utils;

public class Tuple<TA, TB> {
    private final TA valueA;
    private final TB valueB;

    public Tuple(TA valueA, TB valueB) {
        this.valueA = valueA;
        this.valueB = valueB;
    }

    public TA getValueA() {
        return valueA;
    }

    public TB getValueB() {
        return valueB;
    }
}
