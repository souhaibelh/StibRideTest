package mvp.model.db.repository;
import mvp.model.db.tablepk.Key;
import java.util.List;

/**
 * Defines a generalised version of a repository
 * @param <T> the key
 * @param <S> the class extending the key (using it)
 */
public interface Repository<T, S extends Key<T>> {
    void add(S item) throws Exception;
    void delete(T key) throws Exception;
    T get(T key) throws Exception;
    List<S> getAll() throws Exception;
    boolean contains(T key) throws Exception;
}
