import java.util.*;

public class Kiosk {
    private final Map<Integer, Order> orders = new HashMap(); // 주문번호, 주문
    private int orderSeq = 1;
    private final Map<Integer, Menu> menus = new HashMap(); // 메뉴번호, 메뉴
    private int menuSeq = 1;
    private final Map<Menu, List<Product>> products = new HashMap(); // 카테고리, 상품목록

    private final List<OrderProduct> cart = new ArrayList<>();

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

    public int checkSelect(int select) {
        if (select <= menus.size()+4 && select >= 0) {
            if (select > menus.size() || select == 0) {
                return select;
            }
            Menu menu = menus.get(select);
            List<Product> productList = products.get(menu);
            if (menu != null && productList.size() > 0 && productList != null) {
                return select;
            }
            System.out.println("죄송합니다. 해당메뉴에 속하는 상품이 없습니다.");
            System.out.println();
            return -1;
        }
        System.out.println("선택할 수 없는 메뉴입니다.");
        System.out.println();
        return -1;
    }

    public void printProducts(int select) {

        Menu menu = menus.get(select);
        List<Product> productList = products.get(menu);

        System.out.println("\"롯데리아에 오신 걸 환영합니다.\"");
        System.out.println("아래 상품 메뉴판을 보시고 상품을 골라 입력해 주세요.");
        System.out.println("메뉴판으로 돌아가시기를 원하시면 0을 입력해 주세요");
        System.out.println();

        System.out.println("[ " + menu.getName() + " MENU ]");
        for (int i = 0; i < productList.size(); i++) {
            System.out.println((i + 1) + ". " + productList.get(i).getName() + "   |  " + productList.get(i).getPrice() + "원   |   " + productList.get(i).getDescription());
        }
        System.out.println();
    }

    public Product selectProduct(int menuSelect, int productSelect) {
        if (productSelect == 0) {
            return new Product("", "", 0);
        }
        Menu menu = menus.get(menuSelect);
        List<Product> productList = products.get(menu);
            if (productList.size() >= productSelect && productSelect > 0) {
                Product choiceProduct = productList.get(productSelect-1);
                System.out.println(choiceProduct.getName() + "을(를) 선택하셨습니다.");
                return choiceProduct;
            } else { // 상품 목록에 없는 번호라면
                System.out.println("해당 번호는 없는 상품입니다.");
                System.out.println();
                return null;
            }
    }

    public boolean printSize(int select) {
        Menu menu = menus.get(select);
        if (menu.getName().equals("햄버거")) {
            System.out.println("1. 싱글사이즈   2. 더블사이즈(+2000원) 선택해 주세요");
            return true;
        }
        return false;
    }

    public String sizeChoice(int select) {
                while (true) {
                    if (select == 1) {
                        return "싱글";
                    } else if (select == 2) {
                        return "더블";
                    } else {
                        System.out.println("올바른 숫자를 입력해 주세요");
                        System.out.println();
                        return "?";
                    }
            }
    }

    public void printQuantityInput() {
        System.out.println("몇 개를 장바구니에 넣으시겠습니까? 메뉴판으로 돌아가기를 원하시면 0을 입력해 주세요");
    }

    public int inputQuantity(int quantity) {
        if (quantity == 0) {
            System.out.println("메뉴판으로 돌아갑니다.");
            return quantity;
        } else if (quantity < 0) {
            System.out.println("0보다 작은 수를 입력하실 수 없습니다.");
            return -1;
        }
        return quantity;
    }

    public void printAddCart() {
        System.out.println("정말 장바구니에 추가하시겠습니까?");
        System.out.println("1. 예   2. 아니오");
        System.out.println();
    }


    public boolean addCart(Product choiceProduct, boolean isDouble, int quantity, int check) {
        if (check == 1) {
            OrderProduct orderProduct = new OrderProduct(choiceProduct, isDouble, quantity);
            // 장바구니에 같은 상품이 있는지 체크하고 있다면 선택한 수량만큼 추가
            boolean isFirst = true;
            for (OrderProduct product : cart) {
                isFirst = !product.getName().equals(orderProduct.getName());
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
                cart.add(orderProduct);
                System.out.println("장바구니에 담았습니다. 1초 뒤 메뉴판으로 돌아갑니다.");
                System.out.println();
                sleep(1000);
            }
        } else if(check == 2) {
            System.out.println("장바구니에 넣기를 취소하셨습니다. 메뉴판으로 돌아갑니다.");
            System.out.println();
        } else {
            System.out.println("올바르지 않은 숫자입니다.");
            return false;
        }
        return true;

    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean cancel(int select) {
        if (!orders.containsKey(select)) {
            System.out.println("없는 주문 번호입니다.");
            return false;
        }
        if (select == 0) {
            System.out.println("메뉴판으로 돌아갑니다.");
            return true;
        }
        orders.remove(select);
        System.out.println("주문 취소가 완료되었습니다. 1초 뒤 메뉴판으로 돌아갑니다.");
        sleep(1000);
        return true;
    }

    public boolean order(int select) {
        if(select == 1){
            Order order = new Order(cart);
            orders.put(orderSeq++, order);
            cart.clear();
            System.out.println("주문 완료되었습니다. 주문 금액 : " + order.totalPrice());
            System.out.println("대기 번호 : [ " + orders.size() + " ] 3초 후 메뉴판으로 돌아갑니다.");
            System.out.println();
            sleep(3000);
            return true;
        } else if(select == 3) {
            cart.clear();
            return true;
        } else if(select == 2) {
            System.out.println("메뉴판으로 돌아갑니다.");
            System.out.println();
            return true;
        } else {
            System.out.println("잘못된 번호를 입력하셨습니다.");
            return false;
        }
    }

    public void sales() {
        int sales = 0;
        for (Order order : orders.values()) {
            sales += order.totalPrice();
        }
        System.out.println("매출 : " + sales);
        System.out.println("메뉴판 돌아가시려면 아무 숫자나 입력해 주세요");
    }


    public void salesDetails() {
        System.out.println("[판매 현황]");
        if (orders.size() != 0) {
            orders.values().stream().forEach(order -> {
                order.getOrderProducts().stream().forEach(product -> {
                    System.out.println(product.getName() + " | " + product.getPrice() + "원 | 수량 : " + product.getQuantity());
                });
            });
            System.out.println("메뉴판 돌아가시려면 아무 숫자나 입력해 주세요");
            System.out.println();
        } else {
            System.out.println("판매내역이 존재하지 않습니다.");
            System.out.println("2초 뒤 메뉴판으로 돌아갑니다.");
            System.out.println();
            sleep(2000);
        }
    }

    public boolean printOrders() {
        if (orders.size() <= 0) {
            System.out.println("주문 내역이 존재하지 않습니다. 1초 뒤 메뉴판으로 돌아갑니다.");
            System.out.println();
            sleep(1000);
            return false;
        }
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
        return true;
    }

    public void printCart() {
        int num = 1;
        for (OrderProduct cartProduct : cart) {
            System.out.println((num++) + ". " + cartProduct.getName() + " | " + cartProduct.getPrice() + "원 | 수량 " + cartProduct.getQuantity() + " | " + cartProduct.getDescription());
        }

        System.out.println("주문하시겠습니까?");
        System.out.println("1. 주문   2. 메뉴판   3. 장바구니 초기화");
    }

}