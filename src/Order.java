import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {

    // 주문 상품
    private final List<OrderProduct> orderProducts = new ArrayList<>();

    // 주문 번호
    private Integer orderNumber;

    // 주문 상태
    private OrderStatus orderStatus;

    private String requestMessage;

    private OffsetDateTime receptionDate;

    private OffsetDateTime completeDate;

    public Order(List<OrderProduct> orderProducts, String requestMessage, Integer sequence) {
        this.orderProducts.addAll(orderProducts);
        this.requestMessage = requestMessage;
        orderNumber = sequence;
        orderStatus = OrderStatus.WAITING;
        receptionDate = OffsetDateTime.now().withNano(0);
    }

    public void orderComplete() {
        orderStatus = OrderStatus.COMPLETED;
        completeDate = OffsetDateTime.now().withNano(0);
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

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public OffsetDateTime getReceptionDate() {
        return receptionDate;
    }

    public OffsetDateTime getCompleteDate() {
        return completeDate;
    }
}