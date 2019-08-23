package com.mmall.concurrency.example.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mmall.concurrency.annoations.ThreadSafe;

import java.util.List;

/**
 * 不可变对象
 */
@ThreadSafe
public class immutableExample3 {

//    private final static ImmutableList list = ImmutableList.of(1, 2, 3);
    private final static ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);

    private final static ImmutableSet set = ImmutableSet.copyOf(list);

    private final static ImmutableMap<Integer, Integer> map = ImmutableMap.of(1,2, 3,4);
    private final static ImmutableMap<Integer, Integer> map2 = ImmutableMap.<Integer, Integer>builder()
            .put(1, 2).put(3,4).put(5,6).build();


    public static void main(String[] args) {
//        list.add(4);
//        set.add(4);
        System.out.println(map2.get(1));
    }
}
