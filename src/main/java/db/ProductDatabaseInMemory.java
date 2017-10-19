package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Product;

public class ProductDatabaseInMemory implements ProductDatabase {
    private Map<Integer, Product> records = new HashMap<>();
    private int idCounter = 0;


    public ProductDatabaseInMemory() {
        add(new Product("Rose", "Thorny plant", 2.25));
    }

    @Override
    public Product get(Integer id) {
        if (id < 0) {
            throw new DbException("No valid id given");
        }
        if (!records.containsKey(id)) {
            throw new DbException("No product found");
        }
        return records.get(id);
    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(records.values());
    }

    @Override
    public void add(Product product) {
        if (product == null) {
            throw new DbException("No product given");
        }
        final int id = generateNextID();
        product.setId(id);
        records.put(id, product);
    }

    @Override
    public void update(Product product) {
        if (product == null) {
            throw new DbException("No product given");
        }
        if (!records.containsKey(product.getId())) {
            throw new DbException("No product found");
        }
        records.put(product.getId(), product);
    }

    @Override
    public void delete(Integer id) {
        if (id < 0) {
            throw new DbException("No valid id given");
        }
        if (!records.containsKey(id)) {
            throw new DbException("No product found");
        }
        records.remove(id);
    }

    private int generateNextID() {
        return idCounter++;
    }
}
