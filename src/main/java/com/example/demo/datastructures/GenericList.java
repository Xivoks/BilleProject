package com.example.demo.datastructures;

import java.util.ArrayList;
import java.util.List;

public class GenericList<T> {
    private final List<T> list;

    public GenericList() {
        list = new ArrayList<>();
    }

    public void add(T item) {
        list.add(item);
    }

    public List<T> getList() {
        return list;
    }
}
