public class Product extends Menu{
    private int price;

    public Product(String name, String description, int price) {
        super(name, description);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

}