package FIUBA.CineXplore.service;

import java.util.List;

public interface IGenericService<T> {
    List<T> findAll();

    T findById(Long id);

    T save(T entity);

    void deleteById(Long id);
}