import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final Kiosk kiosk = new Kiosk();
        Scanner sc = new Scanner(System.in);

        // 키오스크 메뉴, 상품 추가
        kioskSet(kiosk);

        // 키오스크 동작
        EXIT:
        while (true) {
            Map<Integer, Menu> menus = kiosk.getMenus();
            kiosk.printMenus(); // 메뉴판 출력

            int choice = sc.nextInt(); // 번호 선택

            // 메뉴판에 없는 번호라면
            if (menus.size()+4 < choice || choice < 0) {
                System.out.println("선택할 수 없는 메뉴입니다.");
                System.out.println();
                continue;
            }

            Menu menu = kiosk.getMenus().get(choice); // 선택한 메뉴
            switch (choice) {

                case 0:
                    break EXIT;

                // 장바구니 이동 및 주문
                case 5:
                    // 장바구니 출력
                    kiosk.printCart();

                    // 주문, 장바구니 초기화, 메뉴판 선택
                    int orderChoice = sc.nextInt();
                    kiosk.order(orderChoice);
                    break;


                // 주문내역 및 주문취소
                case 6:

                    kiosk.printOrders(); // 주문 내역 출력

                    int cancelChoice = sc.nextInt(); // 주문번호 선택
                    // 메뉴판으로 돌아가기
                    if (cancelChoice == 0) {
                        break;
                    }

                    // 주문 취소
                    kiosk.cancel(cancelChoice);
                    System.out.println("주문 취소 완료");
                    System.out.println();
                    break;

                case 7:
                    // 총 매출
                    System.out.println("매출 : " + kiosk.sales());
                    System.out.println("메뉴판 돌아가시려면 아무 문자나 입력해 주세요");
                    sc.next();
                    break;

                case 8:
                    // 판매 내역
                    if (kiosk.salesDetails()) {
                        System.out.println("메뉴판 돌아가시려면 아무 문자나 입력해 주세요");
                        sc.next();
                        System.out.println();
                    }
                    break;

                default:

                    // 메뉴안 상품들 출력
                    if (!kiosk.printProducts(menu)) { // 메뉴 안에 상품이 존재하지 메뉴판으로 않는다면 돌아감
                        break;
                    }

                    int productNum = sc.nextInt(); // 상품 선택
                    if (productNum == 0) { // 0 선택 시 메뉴판으로 돌아감
                        break;
                    }

                    Product choiceProduct = kiosk.printChoiceProduct(productNum, menu); // 상품을 선택하고 선택한 상품을 출력함
                    if (choiceProduct == null) { // 상품 란에 존재하지 않는 번호를 선택하면 메뉴판으로
                        break;
                    }

                    boolean isDouble = false;
                    if (kiosk.printSizeChoice(menu)) { // 메뉴가 햄버거 일 때만 더블을 고를 건지 물어보고 선택함
                        int sizeChoice = sc.nextInt();
                        if (sizeChoice == 1) {
                            isDouble = false;
                        } else if (sizeChoice == 2) {
                            isDouble = true;
                        } else {
                            System.out.println("잘못 입력하셨습니다. 메뉴판으로 돌아갑니다.");
                            System.out.println();
                            continue;
                        }
                    }

                    kiosk.printQuantityInput(); // 개수 입력해 주세요
                    int quantity = sc.nextInt(); // 개수 입력
                    // 조건에 맞게 생성 후 카트에 넣음
                    OrderProduct orderProduct = new OrderProduct(choiceProduct, isDouble, quantity);

                    System.out.println("정말 장바구니에 추가하시겠습니까?");
                    System.out.println("1. 예   2. 아니오");
                    int check = sc.nextInt();
                    if (check == 1) {
                        kiosk.addCart(orderProduct, quantity);
                    } else {
                        System.out.println("장바구니에 넣기를 취소하셨습니다. 메뉴판으로 돌아갑니다.");
                        System.out.println();
                    }


//                    int sizeChoice = sc.nextInt();
//                    boolean isDouble = kiosk.choiceSize(choiceProduct, menu, sizeChoice);
//                    int quantity = sc.nextInt();
//                    kiosk.addCart(choiceProduct, isDouble, quantity);


//                    sizeAndQuantity(menu, kiosk, sc, choiceProduct);
            }
        }

    }

//    private static void sizeAndQuantity(Menu menu, Kiosk kiosk, Scanner sc, Product choiceProduct) {
//
//        boolean isDouble = false;
//        if (menu.getName().equals("햄버거")) {
//            System.out.println("1. 싱글사이즈   2. 더블사이즈(+2000원) 선택해주세요");
//            int sizeChoice = sc.nextInt();
//            isDouble = sizeChoice == 1 ? false : true;
//        }
//
//        // 개수 선택
//        System.out.println("몇 개를 장바구니에 넣으시겠습니까? 원치 않으시면 0을 입력해 주세요");
//        int quantity = sc.nextInt();
//        OrderProduct orderProduct = new OrderProduct(choiceProduct, isDouble, quantity);
//        kiosk.addCart(orderProduct, quantity);
//    }

    private static void kioskSet(Kiosk kiosk) {
        // 메뉴 추가
        Menu burgers = new Menu("햄버거", "롯데리아의 다양한 햄버거");
        Menu dessert = new Menu("디저트", "포테이토 등의 다양한 사이드 메뉴");
        Menu drinks = new Menu("음료", "음료수");
        Menu iceShot = new Menu("아이스샷", "달달하고 시원한 아이스샷");
        kiosk.menuRegistration(burgers);
        kiosk.menuRegistration(dessert);
        kiosk.menuRegistration(drinks);
        kiosk.menuRegistration(iceShot);

        // 상품 추가
        kiosk.productRegistration(burgers, new Product( "핫크리스피버거", "닭가슴살 패티로 만든 매콤 화끈한 프리미엄 치킨버거", 5900));
        kiosk.productRegistration(burgers, new Product( "데리버거", "쇠고기패티에 달콤 짭짤한 데리소스를 더한 가성비 버거", 3300));
        kiosk.productRegistration(burgers, new Product( "한우불고기버거", "브리오쉬번에 국내산 한우를 사용한 패티, 신선한 야채, 특제소스로 완성한 프리미엄 햄버거", 8400));
        kiosk.productRegistration(burgers, new Product( "새우버거", "새우살을 가득넣어 더 맛있어진 오리지널 새우버거", 4700));
        kiosk.productRegistration(burgers, new Product( "불고기버거", "두툼한 쇠고기패티와 한국적인 맛의 소스가 잘 조화된 롯데리아 대표 버거", 4700));

        kiosk.productRegistration(dessert, new Product("포테이토", "바로 튀겨낸 바삭바삭한 후렌치 포테이토", 1800));
        kiosk.productRegistration(dessert, new Product("양념감자", "시즈닝(오니언, 치즈, 칠리)을 한가지를 선택해 뿌려먹는 포테이토", 2300));
        kiosk.productRegistration(dessert, new Product("치즈스틱", "통모짜렐라치즈에 튀김옷을 입혀 만든 바삭하고 고소한 롯데리아 대표 디저트 메뉴", 2400));
        kiosk.productRegistration(dessert, new Product("치킨너겟", "닭안심살과 닭가슴살로 만든 담백하고 촉촉한 치킨너겟 - 5조각", 2700));

        kiosk.productRegistration(drinks, new Product("콜라", "톡 쏘는 시원 상쾌한 펩시콜라", 2000));
        kiosk.productRegistration(drinks, new Product("사이다", "톡 쏘는 시원 청량한 칠성사이다", 2000));
        kiosk.productRegistration(drinks, new Product("제로슈거콜라", "칼로리 걱정 없는 시원 상쾌한 제로 슈거 콜라", 2000));
        kiosk.productRegistration(drinks, new Product("레몬에이드", "상큼한 레몬 맛과 톡 쏘는 탄산의 청량함이 느껴지는 시원한 아이스 드링크", 2700));

        kiosk.productRegistration(iceShot, new Product("소프트콘", "부드러운 맛과 시원한 소프트 타입의 아이스콘 제품", 900));
        kiosk.productRegistration(iceShot, new Product("롯데리아빙수", "빙수팥과 후르츠칵테일이 풍성하게 토핑된 전형적인 롯데리아 빙수", 5300));
        kiosk.productRegistration(iceShot, new Product("토네이도 초코쿠키", "부드러운 아이스크림에 달콤한 초코쿠키를 넣어 만든 아이스크림", 2800));
    }
}