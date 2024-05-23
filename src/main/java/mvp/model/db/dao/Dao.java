package mvp.model.db.dao;

import mvp.model.db.tablepk.Key;

import java.util.List;

/**
 * Interface defining a Dao
 * @param <T> is the key
 * @param <S> is the object that uses the key
 */
public interface Dao<T, S extends Key<T>> {
    void insert(S item) throws Exception;
    void delete(T key) throws Exception;
    void update(S item) throws Exception;
    S select(T key) throws Exception;
    List<S> selectAll() throws Exception;
}
