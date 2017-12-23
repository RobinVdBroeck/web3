package domain;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;

    public Product() {
    }

    public Product(String name, String description, double d) {
        setName(name);
        setDescription(description);
        setPrice(d);
    }

    public Product(int id, String name, String description, double d) {
        setId(id);
        setName(name);
        setDescription(description);
        setPrice(d);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new DomainException("No name given");
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.isEmpty()) {
            throw new DomainException("No description given");
        }

        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new DomainException("Give a valid price");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return getName() + ": " + getDescription() + " - " + getPrice();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Product) {
            Product other = (Product) obj;
            return this.getName().equals(other.getName())
                && this.getDescription().equals(other.getDescription())
                && this.getPrice() == (other.getPrice());
        }
        return false;
    }
}
