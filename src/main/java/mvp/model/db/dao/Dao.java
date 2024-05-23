package mvp.model.db.dao;

import mvp.model.db.tablepk.Key;

import java.util.List;

public interface Dao<T, S extends Key<T>> {
    void insert(S item) throws Exception;
    void delete(T key) throws Exception;
    void update(S item) throws Exception;
    S select(T key) throws Exception;
    List<S> selectAll() throws Exception;
}
