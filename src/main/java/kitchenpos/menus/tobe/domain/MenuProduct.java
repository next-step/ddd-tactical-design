package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MenuProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Menu menu;

    @Column(name = "product_id")
    private Long productId;

    private long quantity;

    public MenuProduct() {
    }

    public MenuProduct(Long id, Menu menu, Long productId, long quantity) {
        this.id = id;
        this.menu = menu;
        this.productId = productId;
        this.quantity = quantity;
    }

    public BigDecimal calculatePrice(BigDecimal price) {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    public void registeredOn(Menu menu) {
        this.menu = menu;
    }

    public Long getId() {
        return id;
    }

    public Menu getMenu() {
        return menu;
    }

    public Long getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuProduct)) {
            return false;
        }
        MenuProduct that = (MenuProduct) o;
        return quantity == that.quantity &&
            Objects.equals(id, that.id) &&
            Objects.equals(menu.getId(), that.menu.getId()) &&
            Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, menu.getId(), productId, quantity);
    }
}
