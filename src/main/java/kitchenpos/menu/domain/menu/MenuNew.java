package kitchenpos.menu.domain.menu;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Objects;
import java.util.UUID;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kitchenpos.menu.domain.menugroup.MenuGroupNew;

@Table(name = "menu_new")
@Entity
public class MenuNew {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name"))
    private MenuName name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private MenuPrice price;

    @Embedded
    private MenuProducts menuProducts;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroupNew menuGroup;

    private DisplayingStatus displayingStatus;

    protected MenuNew() {
    }

    private MenuNew(final UUID id, final MenuName name, final MenuPrice price,
        final MenuProducts menuProducts, final MenuGroupNew menuGroup) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.menuProducts = menuProducts;
        this.menuGroup = menuGroup;
    }

    public static MenuNew create(final MenuName name, final MenuPrice price,
        final MenuProducts menuProducts, final MenuGroupNew menuGroup) {

        if (menuProducts.totalAmount() < price.getValue()) {
            throw new IllegalArgumentException("price is greater than menuProducts' totalAmount");
        }

        return new MenuNew(UUID.randomUUID(), name, price, menuProducts, menuGroup);
    }

    public void changeProductPrice(final UUID productId, final ProductPrice productPrice) {
        checkNotNull(productId, "productId");
        checkNotNull(productPrice);

        menuProducts.getValues()
            .stream()
            .filter(menuProduct -> productId.equals(menuProduct.getProductId()))
            .findFirst()
            .orElseThrow()
            .changeProductPrice(productPrice);

        rearrangeDisplaying();
    }

    private void rearrangeDisplaying() {
        if (menuProducts.totalAmount() < price.getValue()) {
            hide();
        }
    }

    public void hide() {
        displayingStatus = DisplayingStatus.NOT_DISPLAYED;
    }

    public void display() {
        checkCanDisplay();

        displayingStatus = DisplayingStatus.DISPLAYED;
    }

    private void checkCanDisplay() {
        if (menuProducts.totalAmount() < price.getValue()) {
            throw new IllegalStateException(String.format(
                "price is bigger than menuProducts totalAmount. totalAmount: %s, price: %s",
                menuProducts.totalAmount(), price));
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MenuNew menuNew = (MenuNew) o;
        return Objects.equal(id, menuNew.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public UUID getId() {
        return id;
    }

    public MenuName getName() {
        return name;
    }

    public MenuPrice getPrice() {
        return price;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }
}
