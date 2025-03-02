package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuGroupEmptyException;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuPriceException;
import kitchenpos.menus.tobe.domain.vo.MenuName;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;
import kitchenpos.menus.tobe.domain.vo.MenuProducts;

import java.util.UUID;

/**
 * ### 메뉴
 * <p>
 * > 고객에게 판매할 메뉴를 관리하는 영역
 * >
 * > 상품을 묶어 메뉴를 구성하고, 메뉴를 묶어 메뉴 그룹을 구성할 때 사용한다.
 * <p>
 * | 한글명         | 영문명        | 설명                                                                                                                                             |
 * | -------------- | ------------- | ------------------------------------------------------------------------------------------------------------------------------------------------ |
 * | 메뉴           | Menu          | 고객에게 판매할 1개 이상의 상품을 포함한 리스트. e.g. 후라이드 치킨, 양념치킨, 반반치킨                                                              |
 * | 메뉴 이름      | MenuName      | 비속어가 포함되지 않은 메뉴의 이름                                                                                                               |
 * | 메뉴 가격      | MenuPrice     | 메뉴에 포함된 상품들의 총 가격(각 상품 가격 × 수량)                                                                                                |
 * | 메뉴 노출 여부 | displayed | 고객에게 해당 메뉴를 노출할지 여부. 판매 가능 시 `true`, 판매 불가능 시 `false`                                                                            |
 * | 메뉴 그룹      | MenuGroup     | 비슷한 메뉴를 묶는 카테고리. e.g. 메인 메뉴(후라이드 치킨, 양념치킨), 세트 메뉴(후라이드 치킨 + 감자튀김), 사이드 메뉴(감자 튀김), 추가(콜라, 생맥주) |
 * | 메뉴 상품      | MenuProduct   | 하나의 메뉴에 포함된 개별 상품과 그 수량을 의미. e.g. 후라이드 치킨(치킨 1마리, 허니머스타드 소스, 콜라 500ml)                                        |
 */
@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Embedded
    private MenuName name;

    @Embedded
    private MenuPrice price;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    protected Menu() {
    }

    public Menu(MenuGroup menuGroup, String name, int price, boolean displayed) {
        if (menuGroup == null) {
            throw new InvalidMenuGroupEmptyException("메뉴는 반드시 특정 메뉴 그룹에 속해야 합니다.");
        }
        this.id = UUID.randomUUID();
        this.menuGroup = menuGroup;
        this.name = new MenuName(name);
        this.price = new MenuPrice(price);
        this.displayed = displayed;
    }

    /**
     * 메뉴 가격은 포함된 상품들의 총 가격보다 클 수 없다.
     */
    public Menu(MenuGroup menuGroup, String name, int price, boolean displayed, MenuProducts menuProducts) {
        if (menuGroup == null) {
            throw new InvalidMenuGroupEmptyException("메뉴는 반드시 특정 메뉴 그룹에 속해야 합니다.");
        }
        if (price > menuProducts.total()) {
            throw new InvalidMenuPriceException("메뉴 가격은 포함된 상품들의 총 가격보다 클 수 없습니다.");
        }
        this.id = UUID.randomUUID();
        this.menuGroup = menuGroup;
        this.name = new MenuName(name);
        this.price = new MenuPrice(price);
        this.displayed = displayed;
    }
}
