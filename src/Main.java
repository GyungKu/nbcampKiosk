public class Main {

    public static void main(String[] args) {
        final Kiosk kiosk = new Kiosk();
        Customer customer = new Customer();

        // 키오스크 메뉴, 상품 추가
        kioskSet(kiosk);

        // 키오스크 동작
        EXIT:
        while (true) {
            boolean flag = true;
            int select = -1;

            while (flag) {
                kiosk.printMenus(); // 메뉴판 출력
                select = kiosk.checkSelect(customer.select()); // 메뉴가 있는지, 메뉴의 상품이 존재하는지 여부 체크하고 선택한 번호를 반환
                flag = select > -1 ? false : true;
            }


            switch (select) {
                case 0:
                    break EXIT;
                case 5:
                    while (!flag) {
                        kiosk.printCart();
                        flag = kiosk.order(customer.select());
                    }
                    break;
                case 6:
                    while (!flag) {
                        if (kiosk.printOrders()) {
                            kiosk.cancel(customer.select());
                            break;
                        }
                        flag = true;
                    }
                    break;
                case 7:
                    kiosk.sales();
                    break;
                case 8:
                    kiosk.salesDetails();
                    break;
                default:
                    Product choiceProduct;
                    while (true) {
                        kiosk.printProducts(select);
                        choiceProduct = kiosk.selectProduct(select, customer.select()); // 상품을 선택하고 선택한 상품을 출력함
                        if (choiceProduct != null) {
                            break;
                        }
                    }
                    if (choiceProduct.getName().equals("")) {
                        break;
                    }


                    String size = "";
                    while (true) {
                        if (kiosk.printSize(select)) {
                            size = kiosk.sizeChoice(customer.select());
                            if (!size.equals("?")) {
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    boolean isDouble = size.equals("더블") ? true : false;


                    int quantity;
                    while (true) {
                        kiosk.printQuantityInput();
                        quantity = kiosk.inputQuantity(customer.select());
                        if (quantity >= 0) {
                            break;
                        }
                    }

                    if (quantity == 0) {
                        break;
                    }

                    while (true) {
                        kiosk.printAddCart();
                        boolean addToCart = kiosk.addCart(choiceProduct, isDouble, quantity, customer.select());
                        if (addToCart) {
                            break;
                        }
                    }
            }
        }
    }

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