package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
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
import kitchenpos.products.tobe.domain.Product;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName displayedName;

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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    protected Menu() {
    }

    public Menu(String displayedName, BigDecimal price, MenuGroup menuGroup, boolean displayed,
        List<MenuProduct> menuProducts) {
        this(UUID.randomUUID(), new DisplayedName(displayedName), new Price(price), menuGroup,
            displayed, menuProducts);
    }

    public Menu(UUID id, DisplayedName displayedName, Price price, MenuGroup menuGroup,
        boolean displayed, List<MenuProduct> menuProducts) {
        validatePrice(price, menuProducts);
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    private void validatePrice(Price price, List<MenuProduct> menuProducts) {
        BigDecimal sum = BigDecimal.ZERO;
        for (MenuProduct menuProduct : menuProducts) {
            long quantity = menuProduct.getQuantity().getValue();
            Product product = menuProduct.getProduct();
            sum = sum.add(product.getPrice().getValue().multiply(BigDecimal.valueOf(quantity)));
        }

        if (price.compareTo(sum) > 0) {
            throw new IllegalArgumentException("가격은 메뉴 상품의 금액의 합보다 클 수 없습니다.");
        }
    }

    public void changePrice(BigDecimal value) {
        Price price = new Price(value);
        validatePrice(price, menuProducts);
        this.price = price;
    }

    public void display() {
        validatePrice(price, menuProducts);
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getDisplayedName() {
        return displayedName;
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

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
