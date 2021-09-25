package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * ### 메뉴 모델링
 * - `MenuGroup`은 식별자와 이름을 가진다.
 * - `Menu`는 식별자와 `Displayed Name`, 가격, `MenuProducts`를 가진다.
 * - `Menu`는 특정 `MenuGroup`에 속한다.
 * - `Menu`의 가격은 `MenuProducts`의 금액의 합보다 적거나 같아야 한다.
 * - `Menu`의 가격이 `MenuProducts`의 금액의 합보다 크면 `NotDisplayedMenu`가 된다.
 * - `MenuProduct`는 가격과 수량을 가진다.
 * <p>
 * ## 메뉴 요구사항
 * - 1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.
 * - 상품이 없으면 등록할 수 없다.
 * - 메뉴에 속한 상품의 수량은 1 이상이어야 한다.
 * - 메뉴의 가격이 올바르지 않으면 등록할 수 없다.
 *   - 메뉴의 가격은 0원 이상이어야 한다.
 * - 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.
 * - 메뉴는 특정 메뉴 그룹에 속해야 한다.
 * - 메뉴의 이름이 올바르지 않으면 등록할 수 없다.
 *   - 메뉴의 이름에는 비속어가 포함될 수 없다.
 * - 메뉴의 가격을 변경할 수 있다.
 * - 메뉴의 가격이 올바르지 않으면 변경할 수 없다.
 *   - 메뉴의 가격은 0원 이상이어야 한다.
 * - 메뉴를 노출할 수 있다.
 * - 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.
 * - 메뉴를 숨길 수 있다.
 * - 메뉴의 목록을 조회할 수 있다.
 */
@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Embedded
    private Name displayedName;

    @Embedded
    private MenuProducts menuProducts;

    @ManyToOne
    private MenuGroup menuGroup;

    @Embedded
    private Price price;

    @Column
    private boolean isDisplayed;

    public void display() {
        if(menuProducts.getPriceSum().compareTo(price.getPrice()) < 0){
            throw new IllegalStateException("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없습니다.");
        }

        this.isDisplayed = true;
    }

    public void hide() {
        this.isDisplayed = false;
    }

    public Boolean isDisplayed(){
        return this.isDisplayed;
    }

    public Price getPrice() {
        return this.price;
    }

    public Menu() {

    }

    public Menu(MenuGroup menuGroup, Price price, Name displayedName, MenuProducts menuProducts) {
        this.menuGroup = menuGroup;
        this.price = price;
        this.displayedName = displayedName;
        this.isDisplayed = menuProducts.getPriceSum().compareTo(price.getPrice()) >= 0;
        this.menuProducts = menuProducts;
    }

    public void changePrice(BigDecimal newPriceValue){
        this.price = this.price.changePrice(newPriceValue);
        this.isDisplayed = menuProducts.getPriceSum().compareTo(this.price.getPrice()) >= 0;
    }

}
