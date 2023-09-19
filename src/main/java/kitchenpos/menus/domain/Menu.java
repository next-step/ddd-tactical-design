package kitchenpos.menus.domain;

import kitchenpos.menus.domain.exception.InvalidMenuPriceException;
import kitchenpos.menus.domain.vo.MenuName;
import kitchenpos.menus.domain.vo.MenuPrice;
import kitchenpos.menus.domain.vo.MenuProducts;
import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private MenuName name;

    @Embedded
    @Column(name = "price", nullable = false)
    private MenuPrice price;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    @Transient
    private UUID menuGroupId;


    public Menu(UUID id, String name, BigDecimal price, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProductList, UUID menuGroupId, PurgomalumClient purgomalumClient) {

        this.id = id;
        this.name = new MenuName(name, purgomalumClient);
        this.price = new MenuPrice(price);
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = new MenuProducts(menuProductList);
        this.menuGroupId = menuGroupId;
    }

    public Menu(Menu request, PurgomalumClient purgomalumClient) {
        this(request.getId(),
                request.getName().getName(),
                request.getPrice(),
                request.getMenuGroup(),
                request.isDisplayed(),
                request.getMenuProducts().getMenuProducts(),
                request.getMenuGroupId(),
                purgomalumClient
        );
    }

    public Menu changePrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMenuPriceException();
        }
        if (price.compareTo(menuProducts.totalAmount()) < 0) {
            this.displayed = false;
        }
        this.price = new MenuPrice(price);
        return this;
    }

    public UUID getId() {
        return id;
    }

    public MenuName getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
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

    public Menu displayed() {
        this.displayed = true;
        return this;
    }

    public Menu hide() {
        this.displayed = false;
        return this;
    }
}
