package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "price_seq", nullable = false, foreignKey = @ForeignKey(name = "fk_product_to_price"))
    private Price price;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts = new MenuProducts();

    public Menu() {
    }

    public Menu(final BigDecimal price, final boolean displayed, final MenuProducts menuProducts) {
        this.id = UUID.randomUUID();
        this.price = new Price(price);
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public UUID getId() {
        return id;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void comparePriceToMenuProductsAndHideIfOver() {
        Price totalPrice = menuProducts.calculateTotalPrice();
        if (price.compareTo(totalPrice) > 0) {
            displayed = false;
        }
    }

    public boolean hasProduct(final UUID productId) {
        return menuProducts.hasProduct(productId);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }

        final Menu menu = (Menu) o;
        return id.equals(menu.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
