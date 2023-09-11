package kitchenpos.products.ui;

import kitchenpos.products.tobe.domain.DisplayedName;
import kitchenpos.products.tobe.domain.Price;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class ProductCreateRequest {

    private DisplayedName name;
    private Price price;

    public ProductCreateRequest() {
    }

    @NotEmpty
    public void setName(String name) {
        this.name = DisplayedName.create(name);
    }

    public void setPrice(BigDecimal price) {
        this.price = Price.create(price);
    }

    public DisplayedName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
}
