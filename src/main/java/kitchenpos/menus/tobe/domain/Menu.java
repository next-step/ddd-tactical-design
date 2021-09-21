package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity(name = "tobeMenu")
public class Menu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

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

    public Menu(
            final UUID id,
            final Name name,
            final Price price,
            final MenuGroup menuGroup,
            final boolean displayed,
            final List<MenuProduct> menuProducts
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public Menu(
            final UUID id,
            final Name name,
            final Price price,
            final UUID menuGroupId,
            final boolean displayed,
            final List<MenuProduct> menuProducts
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public Menu(
            final Name name,
            final Price price,
            final UUID menuGroupId,
            final boolean displayed,
            final List<MenuProduct> menuProducts
    ) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public Menu(final Price price) {
        this.price = price;
    }

    public void changePrice(Price price) {
        validate(price, menuProducts);
        this.price = price;
    }

    public void display() {
        validate(price, menuProducts);
        displayed = true;
    }

    public void hide() {
        displayed = false;
    }

    public UUID getId() {
        return id;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public String getName() {
        return name.getName();
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    private void validate(Price price, List<MenuProduct> menuProducts) {
        for (final MenuProduct menuProduct : menuProducts) {
            final BigDecimal sum = menuProduct.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(menuProduct.getQuantity()));
            if (price.getValue().compareTo(sum) > 0) {
                throw new IllegalArgumentException("메뉴의 가격은 메뉴상품의 전체 가격보다 적거나 같아야 합니다.");
            }
        }
    }
}
