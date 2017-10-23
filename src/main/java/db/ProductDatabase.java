package db;

import domain.Product;

import java.util.List;

public interface ProductDatabase extends Database<Integer, Product> {
    List<Product> getAllSorted();
}
