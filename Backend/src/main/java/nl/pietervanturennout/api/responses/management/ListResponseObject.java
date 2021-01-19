package nl.pietervanturennout.api.responses.management;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ListResponseObject extends ArrayList<ResponseObject> {
    public static <T> ListResponseObject listGenerator(Collection<T> list, Function<? super T, ResponseObject> mapper) {
        return list
                .stream()
                .map(mapper)
                .collect(collector());
    }

    public static <T> ListResponseObject listGenerator(
            Collection<T> list,
            boolean extended,
            ActionTwoArgReturn<? super T, Boolean, ResponseObject> mapper
    ) {
        return listGenerator(list, item -> mapper.run(item, extended));
    }

    private static Collector<ResponseObject, ListResponseObject, ListResponseObject> collector() {
        return new Collector<ResponseObject, ListResponseObject, ListResponseObject>() {
            @Override
            public Supplier<ListResponseObject> supplier() {
                return ListResponseObject::new;
            }

            @Override
            public BiConsumer<ListResponseObject, ResponseObject> accumulator() {
                return List::add;
            }

            @Override
            public BinaryOperator<ListResponseObject> combiner() {
                return (left, right) -> { left.addAll(right); return left; };
            }

            @Override
            public Function<ListResponseObject, ListResponseObject> finisher() {
                return i -> (ListResponseObject) i;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
            }
        };
    }
}
