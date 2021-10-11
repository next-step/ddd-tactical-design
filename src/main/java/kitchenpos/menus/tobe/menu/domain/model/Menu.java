package kitchenpos.menus.tobe.menu.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.common.tobe.domain.Price;

@Table(name = "menu")
@Entity(name = "tobeMenu")
public class Menu {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private MenuPrice menuPrice;

    @Column(
        name = "menu_group_id",
        nullable = false,
        columnDefinition = "varbinary(16)"
    )
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(final DisplayedName name, final MenuPrice menuPrice, final UUID menuGroupId, final boolean displayed, final MenuProducts menuProducts) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.menuPrice = menuPrice;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        if (isNotValidPrice(menuPrice)) {
            hide();
        }
    }

    public Menu changePrice(final MenuPrice menuPrice) {
        if (isNotValidPrice(menuPrice)) {
            hide();
        }
        this.menuPrice = menuPrice;
        return this;
    }

    public Menu display() {
        if (isNotValidPrice(menuPrice)) {
            throw new IllegalArgumentException("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없습니다.");
        }
        this.displayed = true;
        return this;
    }

    public Menu hide() {
        this.displayed = false;
        return this;
    }

    private boolean isNotValidPrice(final Price price) {
        return price.compareTo(menuProducts.calculateTotalPrice()) > 0;
    }

    public boolean isNotValidPrice() {
        return isNotValidPrice(menuPrice);
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return name;
    }

    public MenuPrice getMenuPrice() {
        return menuPrice;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }
}
