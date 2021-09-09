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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    @Transient
    private UUID menuGroupId;

    public Menu() {}

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return menuName.getName();
    }

    public void setMenuName(final MenuName menuName) {
        this.menuName = menuName;
    }

    public BigDecimal getPrice() {
        return menuPrice.getPrice();
    }

    public void setMenuPrice(final MenuPrice menuPrice) {
        this.menuPrice = menuPrice;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(final MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(final boolean displayed) {
        this.displayed = displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(final List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(final UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public void display() {
        if (!isValidPrice()) {
            throw new IllegalStateException("Menu의 가격은 MenuProducts의 금액의 합보다 적거나 같아야 보일 수 있습니다.");
        }
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public void updateStatus() {
        if (!isValidPrice()) {
            this.displayed = false;
        }
    }

    public void changePrice(final MenuPrice menuPrice) {
        if (isValidPrice(menuPrice)) {
            throw new IllegalArgumentException("Menu의 가격은 MenuProducts의 금액의 합보다 적거나 같아야 합니다.");
        }
        this.menuPrice = menuPrice;
    }

    private BigDecimal sumMenuProductPrice() {
        return menuProducts.stream().map(menuProduct -> menuProduct.getProduct()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(menuProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, (acc, curr) -> acc.add(curr));
    }

    private boolean isValidPrice(final MenuPrice menuPrice) {
        return menuPrice.getPrice()
                .compareTo(sumMenuProductPrice()) <= 0;
    }

    private boolean isValidPrice() {
        return isValidPrice(menuPrice);
    }
}
