package kitchenpos.products.model;

import java.math.BigDecimal;

public class ProductView {
    private Long id;
    private String name;
    private BigDecimal price;

    private ProductView() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Long id;
        private String name;
        private BigDecimal price;

        private Builder() {
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductView build() {
            ProductView productView = new ProductView();
            productView.price = this.price;
            productView.id = this.id;
            productView.name = this.name;
            return productView;
        }
    }
}
