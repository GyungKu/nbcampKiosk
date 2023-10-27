public class Product extends Menu{
    private int price;

    private Menu menu;

    public Product(String name, String description, int price, int id, Menu menu) {
        super(name, description, id);
        this.price = price;
        this.menu = menu;
    }

    public int getPrice() {
        return price;
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void delete() {
        menu.removeProduct(this);
    }

    public Menu getMenu() {
        return menu;
    }
}