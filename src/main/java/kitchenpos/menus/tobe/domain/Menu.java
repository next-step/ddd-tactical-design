package kitchenpos.menus.tobe.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import kitchenpos.menus.tobe.domain.exception.MaximumMenuPriceException;
import kitchenpos.menus.tobe.domain.vo.MenuName;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;

@Table(name = "tb_menu")
@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
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

    protected Menu() {
        id = UUID.randomUUID();
    }

    public Menu(
            MenuName name,
            MenuGroup menuGroup,
            MenuProducts menuProducts,
            MenuPrice price,
            boolean displayed
    ) {
        this();
        validMenuPrice(price, menuProducts);
        this.name = name;
        this.menuGroup = menuGroup;
        this.price = price;
        this.menuProducts = menuProducts;
        this.displayed = displayed;
    }

    protected Menu(UUID id, MenuName name, MenuPrice price, MenuGroup menuGroup, boolean displayed,
            MenuProducts menuProducts, UUID menuGroupId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
    }

    public void changePrice(MenuPrice menuPrice) {
        validMenuPrice(menuPrice, menuProducts);
        this.price = menuPrice;
    }

    public Menu display() {
        validMenuPrice(price, menuProducts);
        displayed = true;
        return this;
    }

    private void validMenuPrice(MenuPrice menuPrice, MenuProducts menuProducts) {
        if (menuPrice.grateThan(menuProducts.sum())) {
            throw new MaximumMenuPriceException();
        }
    }

    public Menu hidden() {
        displayed = false;
        return this;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public MenuPrice getPrice() {
        return price;
    }
}
