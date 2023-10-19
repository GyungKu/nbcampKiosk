import java.util.*;

public class Kiosk {
    private Map<Integer, Order> orders = new HashMap(); // 주문번호, 주문
    private int orderSeq = 1;
    private Map<Integer, Menu> menus = new HashMap(); // 메뉴번호, 메뉴
    private int menuSeq = 1;
    private Map<Menu, List<Product>> products = new HashMap(); // 카테고리, 상품목록

    private List<OrderProduct> cart = new ArrayList<>();

    public void menuRegistration(Menu menu) {
        menus.put(menuSeq++, menu);
    }
    public void productRegistration(Menu menu, Product product) {
        if (!products.containsKey(menu)) { // 카테고리가 없다면 새로운 list, 카테고리를 생성해서 추가
            ArrayList<Product> list = new ArrayList<>();
            list.add(product);
            products.put(menu, list);
        } else { // 있다면 원래 있던 것에 새로운 상품 추가
            List<Product> addProduct = products.get(menu);
            addProduct.add(product);
            products.put(menu, addProduct);
        }
    }

    public boolean printProducts(Menu menu) {
        List<Product> productList = products.get(menu);
        if (productList != null && productList.size() > 0) {
            System.out.println("\"롯데리아에 오신 걸 환영합니다.\"");
            System.out.println("아래 상품 메뉴판을 보시고 상품을 골라 입력해 주세요.");
            System.out.println("메뉴판으로 돌아가시기를 원하시면 0을 입력해 주세요");
            System.out.println();

            System.out.println("[ " + menu.getName() + " MENU ]");
            for (int i = 0; i < productList.size(); i++) {
                System.out.println((i + 1) + ". " + productList.get(i).getName() + "   |  " + productList.get(i).getPrice() + "원   |   " + productList.get(i).getDescription());
            }
            System.out.println();
            return true;
        } else {
            System.out.println("해당 번호의 메뉴는 없습니다.");
            System.out.println();
            return false;
        }
    }

    public Product printChoiceProduct(int productNum, Menu menu) {
        List<Product> productList = products.get(menu);
            if (productList.size() >= productNum && productNum > 0) {
                Product choiceProduct = productList.get(productNum-1);
                System.out.println(choiceProduct.getName() + "을(를) 선택하셨습니다.");
                return choiceProduct;
            } else { // 상품 목록에 없는 번호라면
                System.out.println("죄송합니다. 해당 메뉴에 속하는 상품이 없습니다.");
                System.out.println();
                return null;
            }
    }

    public boolean printSizeChoice(Menu menu) {
        if (menu.getName().equals("햄버거")) {
            System.out.println("1. 싱글사이즈   2. 더블사이즈(+2000원) 선택해 주세요");
            return true;
        }
        return false;
    }

    public void printQuantityInput() {
        System.out.println("몇 개를 장바구니에 넣으시겠습니까? 원치 않으시면 0을 입력해 주세요");
    }


    public void addCart(OrderProduct choiceProduct, int quantity) {
        if (quantity > 0) {
            // 장바구니에 같은 상품이 있는지 체크하고 있다면 선택한 수량만큼 추가
            boolean isFirst = true;
            for (OrderProduct product : cart) {
                isFirst = !product.getName().equals(choiceProduct.getName());
                if(!isFirst) {
                    product.quantityPlus(quantity);
                    System.out.println("장바구니에 담았습니다. 1초 뒤 메뉴판으로 돌아갑니다.");
                    System.out.println();
                    sleep(1000);
                    break;
                }
            }
            // 없다면 새로 장바구니에 추가
            if (isFirst) {
                cart.add(choiceProduct);
                System.out.println("장바구니에 담았습니다. 1초 뒤 메뉴판으로 돌아갑니다.");
                System.out.println();
                sleep(1000);
            }
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancel(int number) {
        orders.remove(number);
        System.out.println("주문 취소가 완료되었습니다. 1초 뒤 메뉴판으로 돌아갑니다.");
        sleep(1000);
    }

    public void order(int orderChoice) {
        if(orderChoice == 1){
            Order order = new Order(cart);
            orders.put(orderSeq++, order);
            cart.clear();
            System.out.println("주문 완료되었습니다. 주문 금액 : " + order.totalPrice());
            System.out.println("대기 번호 : [ " + orders.size() + " ] 3초 후 메뉴판으로 돌아갑니다.");
            System.out.println();
            sleep(3000);
        } else if(orderChoice == 3) {
            cart.clear();
        } else if(orderChoice == 2) {
            return;
        } else {
            System.out.println("잘못된 번호를 입력하셨습니다.");
        }
    }

    public int sales() {
        int sales = 0;
        for (Order order : orders.values()) {
            sales += order.totalPrice();
        }
        return sales;
    }

    public void printMenus() {
        System.out.println("\" 롯데리아에 오신 걸 환영합니다.\"");
        System.out.println("아래 상품 메뉴판을 보시고 상품을 골라 입력해 주세요.");
        System.out.println();
        System.out.println("[ MENU ]");

        // 메뉴판 출력
        for (Map.Entry<Integer, Menu> entry : menus.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue().getName() + "           |  " + entry.getValue().getDescription());
        }
        System.out.println();

        System.out.println("[ ORDER MENU ]");
        System.out.println(menus.size()+1 + ". Order       | 장바구니를 확인 후 주문합니다.");
        System.out.println(menus.size()+2 + ". Cancel      | 진행 중인 주문을 취소합니다.");
    }


    public boolean salesDetails() {
        System.out.println("[판매 현황]");
        if (orders.size() != 0) {
            orders.values().stream().forEach(order -> {
                order.getOrderProducts().stream().forEach(product -> {
                    System.out.println(product.getName() + " | " + product.getPrice() + "원 | 수량 : " + product.getQuantity());
                });
            });
            return true;
        } else {
            System.out.println("판매내역이 존재하지 않습니다.");
            System.out.println("2초 뒤 메뉴판으로 돌아갑니다.");
            System.out.println();
            sleep(2000);
            return false;
        }
    }

    public void printOrders() {
        System.out.println("전체 주문 내역입니다. 취소를 원하시는 상품번호를 입력해 주세요. 메뉴판으로 돌아가시려면 0을 입력해 주세요");
        // 주문 번호 출력, 주문 불러오기
        for (Map.Entry<Integer, Order> entry : orders.entrySet()) {
            System.out.println("주문번호 " + entry.getKey());
            List<OrderProduct> orderProducts = entry.getValue().getOrderProducts();

            // 주문 상품들 출력
            for (OrderProduct orderProduct : orderProducts) {
                System.out.println(orderProduct.getName() + " | " + orderProduct.getPrice() + "원 | 수량 : " + orderProduct.getQuantity());
            }
            System.out.println("주문 총액 : " + entry.getValue().totalPrice());
            System.out.println();
        }
    }

    public void printCart() {
        int num = 1;
        for (OrderProduct cartProduct : cart) {
            System.out.println((num++) + ". " + cartProduct.getName() + " | " + cartProduct.getPrice() + "원 | 수량 " + cartProduct.getQuantity() + " | " + cartProduct.getDescription());
        }

        System.out.println("주문하시겠습니까?");
        System.out.println("1. 주문   2. 메뉴판   3. 장바구니 초기화");
    }




    public Map<Integer, Order> getOrders() {
        return orders;
    }

    public Map<Integer, Menu> getMenus() {
        return menus;
    }

    public Map<Menu, List<Product>> getProducts() {
        return products;
    }

    public List<OrderProduct> getCart() {
        return cart;
    }
}