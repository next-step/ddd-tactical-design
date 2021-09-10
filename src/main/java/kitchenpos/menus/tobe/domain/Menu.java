package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
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

    protected Menu() {}

    private Menu(final UUID id, final MenuName menuName, final MenuPrice menuPrice, final MenuGroup menuGroup, final boolean displayed, final List<MenuProduct> menuProducts, final UUID menuGroupId) {
        this.id = id;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
    }

    public Menu(final MenuName menuName, final MenuPrice menuPrice, final MenuGroup menuGroup, final boolean displayed, final List<MenuProduct> menuProducts, final UUID menuGroupId) {
        validateMenu(menuName, menuPrice, menuGroup, displayed, menuProducts, menuGroupId);
        this.id = UUID.randomUUID();
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
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
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
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
            throw new IllegalArgumentException("변경할 메뉴의 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
        }
        this.menuPrice = menuPrice;
    }

    private boolean isValidPrice() {
        return isValidPrice(menuPrice);
    }

    private boolean isValidPrice(final MenuPrice menuPrice) {
        return isValidPrice(menuPrice, menuProducts);
    }

    private boolean isValidPrice(final MenuPrice menuPrice, final List<MenuProduct> menuProducts) {
        return menuPrice.getPrice()
                .compareTo(sumMenuProductPrice(menuProducts)) <= 0;
    }

    private BigDecimal sumMenuProductPrice(final List<MenuProduct> menuProducts) {
        return menuProducts.stream().map(menuProduct -> menuProduct.getProduct()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(menuProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, (acc, curr) -> acc.add(curr));
    }

    private void validateMenu(final MenuName menuName, final MenuPrice menuPrice, final MenuGroup menuGroup, final boolean displayed, final List<MenuProduct> menuProducts, final UUID menuGroupId) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴는 반드시 한개 이상의 상품으로 구성되어야 합니다.");
        }
        if (!isValidPrice(menuPrice, menuProducts)) {
            throw new IllegalArgumentException("메뉴의 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
        }
    }
}
