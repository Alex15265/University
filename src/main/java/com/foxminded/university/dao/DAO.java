package com.foxminded.university.dao;

import java.util.List;

public interface DAO<T, ID> {
    T create(T t);

    List<T> readAll();

    T readByID(ID id);

    T update(T t);

    void delete(ID id);
}
