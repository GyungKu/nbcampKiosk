import java.util.ArrayList;
import java.util.List;

public class Order {
//    private Map<Product, Integer> orderProduct = new HashMap();
    private final List<OrderProduct> orderProducts = new ArrayList<>();

    public Order(List<OrderProduct> orderProducts) {
        this.orderProducts.addAll(orderProducts);
    }

    public int totalPrice() {
        int totalPrice = 0;
        for (OrderProduct orderProduct : orderProducts) {
            totalPrice += (orderProduct.getPrice() * orderProduct.getQuantity());
        }
        return totalPrice;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

}