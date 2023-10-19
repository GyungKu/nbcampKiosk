public class OrderProduct extends Product{

    private int quantity;
    private boolean isDouble;
    public OrderProduct(Product product, boolean isDouble, int quantity) {
        super(isDouble ? product.getName() + "(더블)" : product.getName(), product.getDescription(), isDouble ? product.getPrice()+2000 : product.getPrice());
        this.quantity = quantity;
        this.isDouble = isDouble;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public void quantityPlus(int quantity) {
        this.quantity += quantity;
    }

}