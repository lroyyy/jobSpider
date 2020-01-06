package com.getfei.jobSpider.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * 将一个指定类型对象的集合按照自定义的一个操作分组； 每组对应一个List、最终返回结果类型是:List<List<T>>
 *
 * @param <T>
 */
public class GroupToList<T> implements Collector<T, List<List<T>>, List<List<T>>> {
    /**
     * 集合中对象两两比较，满足自定义的条件（operation结果返回true），便将这两个元素分为一组
     */
    private BiFunction<T, T, Boolean> operation;
    
    
    public GroupToList(BiFunction<T, T, Boolean> operation) {
        super();
        this.operation = operation;
    }

    @Override
    public BiConsumer<List<List<T>>, T> accumulator() {
        return (c, t) -> {
            boolean added = false;
            for (List<T> cc : c) {
                for (T ccc : cc) {
                    if (operation.apply(ccc, t)){
                        cc.add(t);
                        added = true;
                    }
                    break;
                }
                if (added)
                    break;
                continue;
            }
            if (!added) {
                ArrayList<T> list = new ArrayList<T>();
                list.add(t);
                c.add(list);
            }
        };
    }

    @Override
    public Set<java.util.stream.Collector.Characteristics> characteristics() {
        /*
        Set<java.util.stream.Collector.Characteristics> emptySet = Collections.emptySet();
        */
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }

    @Override
    public BinaryOperator<List<List<T>>> combiner() {
        return (l1,l2) -> {l2.addAll(l1); return l2;};
    }

    @Override
    public Function<List<List<T>>, List<List<T>>> finisher() {
        return p -> p;
    }

    @Override
    public Supplier<List<List<T>>> supplier() {
        return () -> new ArrayList<List<T>>();
    }
}
