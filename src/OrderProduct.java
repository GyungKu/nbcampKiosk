public class OrderProduct extends Product{

    private int quantity;
    private String size;

    public OrderProduct(Product product) {
        super(product.getName(), product.getDescription(), product.getPrice(), product.getId(), product.getMenu());
    }

    public int getQuantity() {
        return quantity;
    }

    public void quantityPlus(int quantity) {
        this.quantity += quantity;
    }

    public void selectSize() {
        this.size = "더블";
        nameChangeSize();
        int price = getPrice();
        changePrice(price+2000);
    }

}