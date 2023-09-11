package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.ToBeMenuProduct;
import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;

import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class ToBeMenu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private MenuName name;

    @Column(name = "price", nullable = false)
    @Embedded
    private MenuPrice price;


    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private ToBeMenuGroup menuGroup;


    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<ToBeMenuProduct> menuProducts;

    @Transient
    private UUID menuGroupId;

    public ToBeMenu() {
    }

    public ToBeMenu(long price, boolean displayed, ToBeMenuProduct menuProduct) {
        this.price = new MenuPrice(BigDecimal.valueOf(price));

        this.displayed = displayed;
        this.menuProducts = List.of(menuProduct);
    }

    public ToBeMenu(String name, long price, ToBeMenuGroup menuGroup, boolean displayed, PurgomalumClient purgomalumClient, List<ToBeMenuProduct> menuProducts) {
        this.id = UUID.randomUUID();
        this.name = new MenuName(name,purgomalumClient);
        this.price = new MenuPrice(BigDecimal.valueOf(price));
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public ToBeMenu(String name, long price, UUID menuGroupId, boolean displayed, PurgomalumClient purgomalumClient, List<ToBeMenuProduct> menuProducts) {
        this.id = UUID.randomUUID();
        this.name = new MenuName(name,purgomalumClient);
        this.price = new MenuPrice(BigDecimal.valueOf(price));
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public void changeDisplayed(final boolean displayed) {
        this.displayed = displayed;
    }

    public void changePrice(BigDecimal price) {
        this.price = new MenuPrice(price);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getMenuName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public ToBeMenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }


    public List<ToBeMenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }
}
