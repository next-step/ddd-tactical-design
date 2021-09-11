package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity(name = "TobeMenu")
public class Menu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName menuName;

    @Embedded
    private MenuPrice menuPrice;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    @Transient
    private UUID menuGroupId;

    protected Menu() {}

    private Menu(final UUID id, final MenuName menuName, final MenuPrice menuPrice, final MenuGroup menuGroup, final boolean displayed, final MenuProducts menuProducts, final UUID menuGroupId) {
        this.id = id;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
    }

    public Menu(final MenuName menuName, final MenuPrice menuPrice, final MenuGroup menuGroup, final boolean displayed, final MenuProducts menuProducts, final UUID menuGroupId) {
        this.id = UUID.randomUUID();
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
        validateMenu();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return menuName.getName();
    }

    public BigDecimal getPrice() {
        return menuPrice.getPrice();
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getMenuProducts();
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void display() {
        if (!isValidPrice(menuPrice)) {
            throw new IllegalStateException("Menu의 가격은 MenuProducts의 금액의 합보다 적거나 같아야 보일 수 있습니다.");
        }
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public void updateStatus() {
        if (!isValidPrice(menuPrice)) {
            this.displayed = false;
        }
    }

    public void changePrice(final MenuPrice menuPrice) {
        if (!isValidPrice(menuPrice)) {
            throw new IllegalArgumentException("변경할 메뉴의 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
        }
        this.menuPrice = menuPrice;
    }

    private boolean isValidPrice(final MenuPrice menuPrice) {
        return menuPrice.getPrice()
                .compareTo(menuProducts.sumProductPrice()) <= 0;
    }

    private void validateMenu() {
        if (!isValidPrice(menuPrice)) {
            throw new IllegalArgumentException("메뉴의 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
        }
    }
}
