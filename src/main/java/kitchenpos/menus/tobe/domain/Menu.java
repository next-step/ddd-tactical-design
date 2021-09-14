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

    protected Menu() {}

    public Menu(final Builder builder) {
        this.id = UUID.randomUUID();
        this.menuName = builder.menuName;
        this.menuPrice = builder.menuPrice;
        this.menuGroup = builder.menuGroup;
        this.displayed = builder.displayed;
        this.menuProducts = builder.menuProducts;
        this.menuGroupId = builder.menuGroupId;
        validateMenu();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private MenuName menuName;
        private MenuPrice menuPrice;
        private MenuGroup menuGroup;
        private boolean displayed;
        private List<MenuProduct> menuProducts;
        private UUID menuGroupId;

        public Menu build() {
            return new Menu(this);
        }

        public Builder menuName(final MenuName menuName) {
            this.menuName = menuName;
            return this;
        }

        public Builder menuPrice(final MenuPrice menuPrice) {
            this.menuPrice = menuPrice;
            return this;
        }

        public Builder menuGroup(final MenuGroup menuGroup) {
            this.menuGroup = menuGroup;
            return this;
        }

        public Builder displayed(final boolean displayed) {
            this.displayed = displayed;
            return this;
        }

        public Builder menuProducts(final List<MenuProduct> menuProducts) {
            this.menuProducts = menuProducts;
            return this;
        }

        public Builder menuGroupId(final UUID menuGroupId) {
            this.menuGroupId = menuGroupId;
            return this;
        }
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
        final BigDecimal productPriceSum = menuProducts.stream().map(menuProduct -> menuProduct.getProduct()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(menuProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, (acc, curr) -> acc.add(curr));
        return menuPrice.getPrice()
                .compareTo(productPriceSum) <= 0;
    }

    private void validateMenu() {
        if (!isValidPrice(menuPrice)) {
            throw new IllegalArgumentException("메뉴의 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
        }
    }
}
