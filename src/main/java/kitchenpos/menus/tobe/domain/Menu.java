package kitchenpos.menus.tobe.domain;

/*
    - 1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.
    - 상품이 없으면 등록할 수 없다.
    - 메뉴에 속한 상품의 수량은 0 이상이어야 한다.
    - 메뉴의 가격이 올바르지 않으면 등록할 수 없다.
      - 메뉴의 가격은 0원 이상이어야 한다.
    - 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.
    - 메뉴는 특정 메뉴 그룹에 속해야 한다.
    - 메뉴의 이름이 올바르지 않으면 등록할 수 없다.
      - 메뉴의 이름에는 비속어가 포함될 수 없다.
    - 메뉴의 가격을 변경할 수 있다.
    - 메뉴의 가격이 올바르지 않으면 변경할 수 없다.
      - 메뉴의 가격은 0원 이상이어야 한다.
    - 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.
    - 메뉴를 노출할 수 있다.
    - 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.
    - 메뉴를 숨길 수 있다.
    - 메뉴의 목록을 조회할 수 있다.
 */

public class Menu {

    private final String name;
    private final int price;
    private final Long menuGroupId;

    public Menu(String name, int price, Long menuGroupId) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
    }
}
