package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;
import kitchenpos.menus.tobe.application.MenuProducts;
import kitchenpos.products.tobe.Money;
import kitchenpos.products.tobe.Name;
import kitchenpos.products.tobe.ProductPrices;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private Name name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    @Transient
    private UUID menuGroupId;

    public Menu() {
    }


    public Menu(UUID uuid, Name name, Money price, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProducts) {
        this.id = uuid;
        this.name = name;
        this.price = price.value();
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroup.getId();
    }


    public void display(ProductPrices productPrices) {
        try{
            MenuProducts.of(menuProducts, new Money(price), productPrices);
            this.displayed = true;
        } catch (IllegalArgumentException e){
            throw new IllegalStateException("메뉴의 가격은 메뉴 상품의 가격보다 높을 수 없습니다.");
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
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


    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

}
