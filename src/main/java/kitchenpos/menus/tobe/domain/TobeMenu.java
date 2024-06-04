package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class TobeMenu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private DisplayName name;

    @Embedded
    @Column(name = "price", nullable = false)
    private MenuPrice menuPrice;

    @Column(name = "menu_group_id", columnDefinition = "binary(16)")
    private UUID menuGroup;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "displayed", nullable = false)
    private Displayed displayed = Displayed.DISPLAYED;

    @Embedded
    private MenuProducts menuProducts;

    protected TobeMenu() {
    }

    public TobeMenu(UUID id, DisplayName name, MenuPrice menuPrice, UUID menuGroup, Displayed displayed, MenuProducts menuProducts) {
        // if (menuPrice.price().compareTo(menuProducts.calculateTotalPrice()) > 0) {
        if (menuProducts.isOverProductPrice(menuPrice.price())) {
            throw new IllegalArgumentException("메뉴의 가격은 메뉴에 속한 상품의 가격보다 적거나 같아야합니다.");
        }

        this.id = id;
        this.name = name;
        this.menuPrice = menuPrice;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    /**
     * 메뉴의 가격을 변경한다
     *
     * @param price the price
     */
    public void changePrice(BigDecimal price) {
        this.menuPrice = MenuPrice.of(price);
        updateDisplayStatusOnPriceChange();
    }

    /**
     * 상품의 가격이 변경될 경우 메뉴의 가격을 체크 해야한다.
     */
    public void updateDisplayStatusOnPriceChange() {
        if (menuProducts.isOverProductPrice(menuPrice.price())) {
            this.displayed = Displayed.HIDDEN;
        }
    }

    public UUID id() {
        return id;
    }

    public DisplayName name() {
        return name;
    }

    public BigDecimal price() {
        return menuPrice.price();
    }

    public UUID menuGroup() {
        return menuGroup;
    }

    public Displayed displayed() {
        return displayed;
    }

    public MenuProducts menuProducts() {
        return menuProducts;
    }
}
