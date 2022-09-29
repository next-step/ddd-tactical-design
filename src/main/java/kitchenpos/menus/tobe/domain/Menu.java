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
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.Price;

@Table(name = "tb_menu")
@Entity(name = "tb_menu")
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private Name name;

    @Embedded
    private Price price;

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

    protected Menu() {
        id = UUID.randomUUID();
    }

    public Menu(
            Name name,
            MenuGroup menuGroup,
            MenuProducts menuProducts,
            Price price,
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

    protected Menu(UUID id, Name name, Price price, MenuGroup menuGroup, boolean displayed,
            MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public void changePrice(Price menuPrice) {
        validMenuPrice(menuPrice, menuProducts);
        this.price = menuPrice;
    }

    public Menu display() {
        validMenuPrice(price, menuProducts);
        displayed = true;
        return this;
    }

    private void validMenuPrice(Price menuPrice, MenuProducts menuProducts) {
        menuPrice.validateLessThan(menuProducts.sum());
    }

    public Menu hidden() {
        displayed = false;
        return this;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public Price getPrice() {
        return price;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }
}
