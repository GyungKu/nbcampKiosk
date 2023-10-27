import java.util.ArrayList;
import java.util.List;

public class Menu {
    private String name;
    private String description;

    private Integer id;

    private List<Product> products = new ArrayList<>();

    public Menu(String name, String description, int id) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void nameChangeSize() {
        this.name += "(더블)";
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

    public Integer getId() {
        return id;
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}