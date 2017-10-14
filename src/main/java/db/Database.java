package db;

import java.util.List;

interface Database<K,V> {
    V get(K id);
    List<V> getAll();
    void add(V product);
    void update(V product);
    void delete(K id);
}
