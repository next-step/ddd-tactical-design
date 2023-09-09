package kitchenpos.products;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.domain.Product;

public class ProductFixture {

    public static ProductCreateRequestBuilder createRequestBuilder() {
        return new ProductCreateRequestBuilder();
    }

    public static ProductUpdateRequestBuilder updateRequestBuilder() {
        return new ProductUpdateRequestBuilder();
    }

    public static Product product(String name, long price) {
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName(name);
        product.setPrice(BigDecimal.valueOf(price));

        return product;
    }

    public static class ProductCreateRequestBuilder {

        private BigDecimal price = BigDecimal.valueOf(0);
        private String name;

        ProductCreateRequestBuilder() {
        }

        public ProductCreateRequestBuilder name(final String name) {
            this.name = name;

            return this;
        }

        public ProductCreateRequestBuilder price(final BigDecimal price) {
            this.price = price;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.setName(this.name);
            product.setPrice(this.price);
            return product;
        }
    }

    public static class ProductUpdateRequestBuilder {

        private BigDecimal price = BigDecimal.valueOf(0);
        private String name;

        ProductUpdateRequestBuilder() {
        }

        public ProductUpdateRequestBuilder name(final String name) {
            this.name = name;

            return this;
        }

        public ProductUpdateRequestBuilder price(final BigDecimal price) {
            this.price = price;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.setName(this.name);
            product.setPrice(this.price);
            return product;
        }
    }
}
