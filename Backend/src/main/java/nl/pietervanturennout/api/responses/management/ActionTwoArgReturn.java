package nl.pietervanturennout.api.responses.management;

public interface ActionTwoArgReturn <Ta, Tb, R> {
    R run(Ta argA, Tb argB);
}