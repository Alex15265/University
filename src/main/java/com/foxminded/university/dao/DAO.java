package com.foxminded.university.dao;

import java.rmi.NoSuchObjectException;
import java.util.List;

public interface DAO<T, ID> {
    T create(T t);

    List<T> readAll() throws ClassNotFoundException;

    T readByID(ID id);

    T update(T t) throws NoSuchObjectException;

    void delete(ID id);
}
