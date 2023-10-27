import java.util.*;

public class Kiosk {

    private final Input input = new Input();
    private final Map<Integer, Order> orders = new HashMap(); // 주문번호, 주문
    private Integer orderSeq = 1;
    private final Map<Integer, Menu> menus = new HashMap(); // 메뉴번호, 메뉴
    private Integer menuSeq = 1;

    private Integer productSeq = 1;
    private final List<OrderProduct> cart = new ArrayList<>();

    private final Map<Integer, Product> productList = new HashMap<>();


    public Kiosk() {
        kioskSet();
    }

    public void printMode() {
        System.out.println("모드를 선택해 주세요");
        System.out.println("1. 주문 모드    2. 관리자 모드");
        selectMode();
    }

    public void selectMode() {
        int select = input.inputInt();

        if (select == 1) {
            printMenus();
        } else if (select == 2) {
            printAdminMenus();
        } else {
            System.out.println("올바른 숫자를 입력해 주세요");
            System.out.println();
            printMode();
        }
    }

    public Menu createMenu(String name, String description) {
        Menu menu = new Menu(name, description, menuSeq++);
        menus.put(menu.getId(), menu);
        return menu;
    }

    public void printMenus() {
        System.out.println("\" 롯데리아에 오신 걸 환영합니다.\"");
        System.out.println("아래 상품 메뉴판을 보시고 상품을 골라 입력해 주세요.");
        System.out.println("모드 선택으로 돌아가고 싶다면 0을 입력해 주세요");
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

        checkSelect();
    }

    public void checkSelect() {
        int select = input.inputInt();

        if (select <= menus.size()+4 && select >= 0) { // 올바른 범위의 숫자를 입력 했는지

            etcMenu(select);

            // 메뉴중 하나를 선택했다면 메뉴안에 등록된 상품이 있는지 체크하고 있으면 번호를 반환, 아니라면 -1 반환해서 출력부터 다시
            Menu menu = menus.get(select);
            List<Product> productList = menu.getProducts();
            if (menu != null && productList.size() > 0 && productList != null) {
                printProducts(menu);
            }
            System.out.println("죄송합니다. 해당메뉴에 속하는 상품이 없습니다.");
            System.out.println();
            printMenus();
        }
        System.out.println("선택할 수 없는 메뉴입니다.");
        System.out.println();
        printMenus();
    }

    public void printProducts(Menu menu) {

        List<Product> productList = menu.getProducts();

        System.out.println("\"롯데리아에 오신 걸 환영합니다.\"");
        System.out.println("아래 상품 메뉴판을 보시고 상품을 골라 입력해 주세요.");
        System.out.println("메뉴판으로 돌아가시기를 원하시면 0을 입력해 주세요");
        System.out.println();

        System.out.println("[ " + menu.getName() + " MENU ]");
        for (int i = 0; i < productList.size(); i++) {
            System.out.println((i + 1) + ". " + productList.get(i).getName() + "   |  " + productList.get(i).getPrice() + "원   |   " + productList.get(i).getDescription());
        }
        System.out.println();

        selectProduct(menu);
    }

    public void selectProduct(Menu menu) {
        int productSelect = input.inputInt();

        if (productSelect == 0) {
            printMenus();
        }
        List<Product> productList = menu.getProducts();
            if (productList.size() >= productSelect && productSelect > 0) {
                Product selectProduct = productList.get(productSelect-1);
                OrderProduct orderProduct = new OrderProduct(selectProduct);
                System.out.println(orderProduct.getName() + "을(를) 선택하셨습니다.");
                printSize(menu, orderProduct);
            } else { // 상품 목록에 없는 번호라면
                System.out.println("해당 번호는 없는 상품입니다.");
                System.out.println();
                printProducts(menu);
            }
    }

    public void printSize(Menu menu, OrderProduct product) {
        if (menu.getName().equals("햄버거")) {
            System.out.println("1. 싱글사이즈   2. 더블사이즈(+2000원) 선택해 주세요");
            selectSize(product);
        }
        printSelectQuantity(product);
    }

    public void selectSize(OrderProduct product) {
        int sizeSelect = input.inputInt();

        while (true) {
            if (sizeSelect == 1) {
                printSelectQuantity(product);
                break;
            } else if (sizeSelect == 2) {
                product.selectSize();
                printSelectQuantity(product);
                break;
            } else {
                System.out.println("올바른 숫자를 입력해 주세요");
                System.out.println();
                selectSize(product);
            }
    }
    }

    public void printSelectQuantity(OrderProduct product) {
        System.out.println("몇 개를 장바구니에 넣으시겠습니까? 메뉴판으로 돌아가기를 원하시면 0을 입력해 주세요");
        System.out.println();
        selectQuantity(product);
    }

    public void selectQuantity(OrderProduct product) {
        int quantity = input.inputInt();

        if (quantity == 0) {
            System.out.println("메뉴판으로 돌아갑니다.");
            printMenus();
        } else if (quantity < 0) {
            System.out.println("0보다 작은 수를 입력하실 수 없습니다. 다시 입력해 주세요");
            selectQuantity(product);
        }
        product.quantityPlus(quantity);
        printAddCart(product);
    }

    public void printAddCart(OrderProduct product) {
        System.out.println("정말 장바구니에 추가하시겠습니까?");
        System.out.println("1. 예   2. 아니오");
        System.out.println();
        addCart(product);
    }


    public void addCart(OrderProduct selectProduct) {
        int confirm = input.inputInt();

        if (confirm == 1) {
            // 장바구니에 같은 상품이 있는지 체크하고 있다면 선택한 수량만큼 추가
            boolean isFirst = true;
            for (OrderProduct product : cart) {
                isFirst = !product.getName().equals(selectProduct.getName());
                if(!isFirst) {
                    product.quantityPlus(selectProduct.getQuantity());
                    System.out.println("장바구니에 담았습니다. 1초 뒤 메뉴판으로 돌아갑니다.");
                    System.out.println();
                    sleep(1000);
                    break;
                }
            }
            // 없다면 새로 장바구니에 추가
            if (isFirst) {
                cart.add(selectProduct);
                System.out.println("장바구니에 담았습니다. 1초 뒤 메뉴판으로 돌아갑니다.");
                System.out.println();
                sleep(1000);
            }
        } else if(confirm == 2) {
            System.out.println("장바구니에 넣기를 취소하셨습니다. 메뉴판으로 돌아갑니다.");
            System.out.println();
            printMenus();
        } else {
            System.out.println("올바르지 않은 숫자입니다.");
            printAddCart(selectProduct);
        }
        printMenus();

    }

    private void etcMenu(int select) {
        if (select == menus.size()+1) {
            printCart();
        } else if (select == menus.size()+2) {
            printOrders();
        } else if (select == menus.size()+3) {
            sales();
        } else if (select == menus.size()+4) {
            salesDetails();
        } else if (select == 0) {
            printMode();
        }
    }

    public void printCart() {
        int num = 1;
        for (OrderProduct cartProduct : cart) {
            System.out.println((num++) + ". " + cartProduct.getName() + " | " + cartProduct.getPrice() + "원 | 수량 " + cartProduct.getQuantity() + " | " + cartProduct.getDescription());
        }

        System.out.println("주문하시겠습니까?");
        System.out.println("1. 주문   2. 메뉴판   3. 장바구니 초기화");
        selectCart();
    }

    public void selectCart() {
        int select = input.inputInt();

        if(select == 1){
            printRequestMessage();
        } else if(select == 3) {
            cart.clear();
            System.out.println("장바구니를 초기화 했습니다. 메뉴판으로 돌아갑니다.");
            printMenus();
        } else if(select == 2) {
            System.out.println("메뉴판으로 돌아갑니다.");
            System.out.println();
            printMenus();
        } else {
            System.out.println("잘못된 번호를 입력하셨습니다.");
            printCart();
        }
    }

    public void printRequestMessage() {
        System.out.println("주문 요청사항을 입력해 주세요 (20자 제한)");
        inputRequestMessage();
    }

    public void inputRequestMessage() {
        String message = input.inputStr();

        if (message.length() > 20) {
            System.out.println("글자 수 제한은 20자 입니다. 다시 입력해 주세요");
            inputRequestMessage();
        }

        Order order = new Order(cart, message, orderSeq);
        orders.put(orderSeq++, order);
        cart.clear();
        System.out.println("주문 완료되었습니다. 주문 금액 : " + order.totalPrice());
        System.out.println("대기 번호 : [ " + watingList().size() + " ] 3초 후 메뉴판으로 돌아갑니다.");
        System.out.println();
        sleep(3000);
        printMenus();
    }

    public void printOrders() {
        if (orders.size() == 0) {
            System.out.println("주문 내역이 존재하지 않습니다. 1초 뒤 메뉴판으로 돌아갑니다.");
            System.out.println();
            sleep(1000);
            printMenus();
        }
        System.out.println("전체 주문 내역입니다. 취소를 원하시는 상품번호를 입력해 주세요. 메뉴판으로 돌아가시려면 0을 입력해 주세요");
        System.out.println();
  
        // 주문 완료 목록 3개 끌어오기
        printCompletedList(3);

        // 주문 대기 목록
        printWaitingList();

        cancel();
    }

    public void cancel() {
        int select = input.inputInt();

        if (select == 0) {
            System.out.println("메뉴판으로 돌아갑니다.");
            printMenus();
        }
        if (!orders.containsKey(select)) {
            System.out.println("없는 주문 번호입니다.");
            printOrders();
        }
        Order order = orders.get(select);
        if (order.getOrderStatus() == OrderStatus.WAITING) {
            orders.remove(select);
            System.out.println("주문 취소가 완료되었습니다. 1초 뒤 메뉴판으로 돌아갑니다.");
            sleep(1000);
            printMenus();
        } else {
            System.out.println("완료 내역은 취소할 수 없습니다.");
            sleep(1000);
            printOrders();
        }
    }

    public void sales() {
        int sales = 0;
        for (Order order : orders.values()) {
            sales += order.totalPrice();
        }
        System.out.println("매출 : " + sales);
        System.out.println("메뉴판 돌아가시려면 아무 문자나 입력해 주세요");
        input.inputStr();
        printMenus();
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
            input.inputStr();
            printMenus();
        } else {
            System.out.println("판매내역이 존재하지 않습니다.");
            System.out.println("2초 뒤 메뉴판으로 돌아갑니다.");
            System.out.println();
            sleep(2000);
            printMenus();
        }
    }

    public void printWaitingList() {
        List<Order> list = watingList();

        if (list != null && list.size() > 0) {
            System.out.println("주문 대기 목록");
            int size = list.size();

            for (Order order : list) {
                System.out.println("주문 번호 : " + order.getOrderNumber());
                System.out.println("주문 접수 시간 : " + order.getReceptionDate());
                System.out.println("대기 번호 : " + (list.size() - size-- + 1));
                System.out.println("요청 사항 : " + order.getRequestMessage());

                List<OrderProduct> orderProducts = order.getOrderProducts();

                for (OrderProduct orderProduct : orderProducts) {
                    System.out.println(orderProduct.getName() + " | " + orderProduct.getPrice() + "원 | 수량 : " + orderProduct.getQuantity());
                }
                System.out.println("주문 총액 : " + order.totalPrice());
                System.out.println();
            }
        }

    }

    private List<Order> watingList() {
        return orders.values().stream().filter(v -> v.getOrderStatus() == OrderStatus.WAITING).toList();
    }

    public void printCompletedList(int limit) {
        List<Order> list = orders.values().stream().filter(v -> v.getOrderStatus() == OrderStatus.COMPLETED)
                .sorted(Comparator.comparing(Order::getCompleteDate).reversed()).toList();
        if (limit > 0){
            list = list.stream().limit(limit).toList();
        }

        if (list != null && list.size() > 0) {
            System.out.println("주문 완료 목록");

            for (Order completed : list) {
                System.out.println("주문번호 : " + completed.getOrderNumber());
                System.out.println("주문 접수 시간 : " + completed.getReceptionDate());
                System.out.println("주문 완료 시간 : " + completed.getCompleteDate());
                System.out.println("요청 사항 : " + completed.getRequestMessage());

                System.out.println("주문 상품 목록");
                List<OrderProduct> completedProducts = completed.getOrderProducts();
                for (OrderProduct completedProduct : completedProducts) {
                    System.out.println(completedProduct.getName() + " | " + completedProduct.getPrice() + "원 | 수량 : " + completedProduct.getQuantity());
                }
                System.out.println("주문 총액 : " + completed.totalPrice());
                System.out.println();
            }
        }
    }

    // 관리자 모드

    public void printAdminMenus() {
        System.out.println("관리자 모드 입니다. 선택해 주세요, 모드 선택으로 돌아가고 싶으시면 0을 입력해 주세요");
        System.out.println("1. 대기주문 목록");
        System.out.println("2. 완료주문 목록");
        System.out.println("3. 상품 생성");
        System.out.println("4. 상품 삭제");

        selectAdminMenu();
    }

    public void selectAdminMenu() {
        int select = input.inputInt();
        switch (select) {
            case 0:
                printMode();
                break;
            case 1:
                printOrderCompletion();
                break;
            case 2:
                printOrderCompleted();
                break;
            case 3:
                createProduct();
                break;
            case 4:
                printProductList();
                break;
        }
    }

    public void printOrderCompletion() {
        System.out.println("주문 완료 처리를 할 주문의 주문 번호를 입력해 주세요, 첫 화면으로 이동 하시려면 0을 입력해 주세요");
        printWaitingList();
        orderCompletion();
    }

    public void orderCompletion() {
        int select = input.inputInt();

        if (select == 0) {
            printAdminMenus();
        }

        Order order = orders.get(select);
        if (order == null) {
            System.out.println("존재하지 않는 주문 번호 입니다. 다시 입력해 주세요");
            System.out.println();
            printOrderCompletion();
        }
        if (order.getOrderStatus() != OrderStatus.WAITING) {
            System.out.println("대기 상태의 주문번호가 아닙니다. 다시 입력해 주세요");
            System.out.println();
            printOrderCompletion();
        }
        order.orderComplete();
        System.out.println("주문 상태를 변경 하였습니다.");
        System.out.println();
        printAdminMenus();
    }

    public void printOrderCompleted() {
        System.out.println("메인 화면으로 돌아가길 원하시면 0을 입력해 주세요");
        printCompletedList(0);
        int select = input.inputInt();
        if (select == 0) {
            printAdminMenus();
        }
        System.out.println("0을 제외한 숫자를 입력하실 수 없습니다");
        printOrderCompleted();
    }

    public void createProduct() {
        System.out.println("상품이 추가될 메뉴를 입력해 주세요");
        String menuName = input.inputStr();
        Menu menu = menus.values().stream().filter(v -> v.getName().equals(menuName)).findFirst().orElse(null);
        if (menu == null) {
            System.out.println("새로운 메뉴입니다. 메뉴에 대한 설명을 입력해 주세요");
            menu = createMenu(menuName, input.inputStr());
        }
        System.out.println("상품의 이름을 입력해 주세요");
        String productName = input.inputStr();
        System.out.println("상품에 대한 설명을 입력해 주세요");
        String description = input.inputStr();
        System.out.println("상품의 가격을 입력해 주세요");
        int price = input.inputInt();
        addProduct(productName, description, price, menu);
        System.out.println("상품 등록이 완료 되었습니다.");
        printAdminMenus();
    }

    private void addProduct(String productName, String description, int price, Menu menu) {
        Product product = new Product(productName, description, price, productSeq++, menu);
        menu.addProduct(product);
        productList.put(product.getId(), product);
    }

    public void printProductList() {
        System.out.println("삭제할 상품의 번호를 입력해 주세요 메인 화면으로 돌아가시려면 0을 입력해 주세요");
        for (Product product : productList.values()) {
            System.out.println(product.getId() + ". " + product.getName());
        }
        deleteProductIdSelect();
    }

    public void deleteProductIdSelect() {
        int select = input.inputInt();
        if (select == 0) {
            printAdminMenus();
        }
        if (select > productList.size() || select <= 0) {
            System.out.println("없는 번호 입니다.");
            printProductList();
        }
        Product product = productList.get(select);
        deleteProduct(product);
    }

    public void deleteProduct(Product deleteProduct) {

        // 장바구니에 있는 상품 제거
        List<OrderProduct> removeOrderProducts = new ArrayList<>();
        for (OrderProduct orderProduct : cart) {
            if (orderProduct.getId() == deleteProduct.getId()) {
                removeOrderProducts.add(orderProduct);
            }
        }
        cart.removeAll(removeOrderProducts);

        removeProduct(deleteProduct);

        printAdminMenus();
    }

    private void removeProduct(Product deleteProduct) {
        productList.remove(deleteProduct.getId());
        deleteProduct.delete();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // 키오스크 초기 셋팅
    private void kioskSet() {
        Menu burgers = createMenu("햄버거", "롯데리아의 다양한 햄버거");
        Menu dessert = createMenu("디저트", "포테이토 등의 다양한 사이드 메뉴");
        Menu drinks = createMenu("음료", "음료수");
        Menu iceShot = createMenu("아이스샷", "달달하고 시원한 아이스샷");


        // 상품 추가
        addProduct("핫크리스피버거", "닭가슴살 패티로 만든 매콤 화끈한 프리미엄 치킨버거", 5900, burgers);
        addProduct("데리버거", "쇠고기패티에 달콤 짭짤한 데리소스를 더한 가성비 버거", 3300, burgers);
        addProduct("한우불고기버거", "브리오쉬번에 국내산 한우를 사용한 패티, 신선한 야채, 특제소스로 완성한 프리미엄 햄버거", 8400, burgers);
        addProduct("새우버거", "새우살을 가득넣어 더 맛있어진 오리지널 새우버거", 4700, burgers);
        addProduct("불고기버거", "두툼한 쇠고기패티와 한국적인 맛의 소스가 잘 조화된 롯데리아 대표 버거", 4700, burgers);

        addProduct("포테이토", "바로 튀겨낸 바삭바삭한 후렌치 포테이토", 1800, dessert);
        addProduct("양념감자", "시즈닝(오니언, 치즈, 칠리)을 한가지를 선택해 뿌려먹는 포테이토", 2300, dessert);
        addProduct("치즈스틱", "통모짜렐라치즈에 튀김옷을 입혀 만든 바삭하고 고소한 롯데리아 대표 디저트 메뉴", 2400, dessert);
        addProduct("치킨너겟", "닭안심살과 닭가슴살로 만든 담백하고 촉촉한 치킨너겟 - 5조각", 2700, dessert);

        addProduct("콜라", "톡 쏘는 시원 상쾌한 펩시콜라", 2000, drinks);
        addProduct("사이다", "톡 쏘는 시원 청량한 칠성사이다", 2000, drinks);
        addProduct("제로슈거콜라", "칼로리 걱정 없는 시원 상쾌한 제로 슈거 콜라", 2000, drinks);
        addProduct("레몬에이드", "상큼한 레몬 맛과 톡 쏘는 탄산의 청량함이 느껴지는 시원한 아이스 드링크", 2700, drinks);

        addProduct("소프트콘", "부드러운 맛과 시원한 소프트 타입의 아이스콘 제품", 900, iceShot);
        addProduct("롯데리아빙수", "빙수팥과 후르츠칵테일이 풍성하게 토핑된 전형적인 롯데리아 빙수", 5300, iceShot);
        addProduct("토네이도 초코쿠키", "부드러운 아이스크림에 달콤한 초코쿠키를 넣어 만든 아이스크림", 2800, iceShot);
    }

}