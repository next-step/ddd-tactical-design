package kitchenpos.products.domain.model;

import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.vo.ProductDisplayedName;
import kitchenpos.products.domain.vo.ProductPrice;
import kitchenpos.products.infra.PurgomalumClient;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductModel {
    private UUID id;
    private ProductDisplayedName displayedName;
    private ProductPrice price;

    public ProductModel(UUID id, String displayedName, BigDecimal price, PurgomalumClient purgomalumClient) {
        this.id = id;
        this.displayedName = new ProductDisplayedName(displayedName, purgomalumClient);
        this.price = new ProductPrice(price);
    }

    public ProductModel(Product product, PurgomalumClient purgomalumClient) {
        this(product.getId(), product.getDisplayedName(), product.getPrice(), purgomalumClient);
    }

    public Product toProduct() {
        return new Product(id, displayedName.getDisplayedName(), price.getPrice());
    }

    public ProductModel changePrice(BigDecimal price) {
        this.price = this.price.changePrice(price);
        return this;
    }
}
