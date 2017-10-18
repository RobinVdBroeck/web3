package db;

import java.util.List;

interface Database<K,V> {
    V get(K id);
    List<V> getAll();
    void add(V value);
    void update(V value);
    void delete(K id);
}
