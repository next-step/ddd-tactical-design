package kitchenpos.products.ui;

import kitchenpos.products.tobe.domain.Price;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProductChangeRequest {

    @NotNull(message = "Price is mandatory")
    private Price price;

    public void setPrice(BigDecimal price) {
        this.price = Price.create(price);
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ProductChangeRequest{" +
                "price=" + price +
                '}';
    }
}
