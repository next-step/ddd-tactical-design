package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityFilteredName;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.util.ObjectUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.UUID;

import static kitchenpos.menus.tobe.domain.MenuValidator.validatePriceNotBiggerThanSumOfProducts;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private ProfanityFilteredName profanityFilteredName;

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
    private MenuProducts menuProducts;

    @Transient
    private UUID menuGroupId;

    protected Menu() {
    }

    protected Menu(final ProfanityFilteredName profanityFilteredName,
                   final Price price,
                   final MenuGroup menuGroup,
                   final boolean displayed,
                   final List<MenuProduct> menuProducts) {
        this.id = UUID.randomUUID();
        this.profanityFilteredName = profanityFilteredName;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = MenuProducts.of(menuProducts);
    }

    private Menu(final UUID id,
                 final ProfanityFilteredName profanityFilteredName,
                 final Price price,
                 final MenuGroup menuGroup,
                 final boolean displayed,
                 final MenuProducts menuProducts) {
        this.id = id;
        this.profanityFilteredName = profanityFilteredName;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return profanityFilteredName.getValue();
    }

    public Price getPrice() {
        return price;
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

    public Menu changePrice(final Price price) {
        if (ObjectUtils.isEmpty(price)) {
            throw new IllegalArgumentException("price cannot be null");
        }

        validatePriceNotBiggerThanSumOfProducts(menuProducts, price);

        return new Menu(id, profanityFilteredName, price, menuGroup, displayed, menuProducts);
    }

    public Menu display() {
        if (displayed) {
            return this;
        }

        validatePriceNotBiggerThanSumOfProducts(menuProducts, price);
        return new Menu(id, profanityFilteredName, price, menuGroup, true, menuProducts);
    }

    public Menu hide() {
        if (!displayed) {
            return this;
        }

        return new Menu(id, profanityFilteredName, price, menuGroup, false, menuProducts);
    }

}
