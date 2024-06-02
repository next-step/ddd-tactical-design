package kitchenpos.menu.tobe.domain;

import kitchenpos.product.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.Objects;

public class MenuProduct {
    private final Product product;
    private final int quantity;
    private BigDecimal price;

    public MenuProduct(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        calculateMenuProductPrice(product, BigDecimal.valueOf(quantity));
    }

    private void calculateMenuProductPrice(Product product, BigDecimal quantity) {
        this.price = product.getProductPrice().multiply(quantity);
    }


    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProduct that = (MenuProduct) o;
        return quantity == that.quantity && Objects.equals(product, that.product) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity, price);
    }
}
