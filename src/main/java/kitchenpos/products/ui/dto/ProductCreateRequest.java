package kitchenpos.products.ui.dto;

import java.math.BigDecimal;
import kitchenpos.products.domain.PurgomalumClient;
import kitchenpos.products.domain.tobe.Product;

public class ProductCreateRequest {

    private String name;

    private BigDecimal price;

    public ProductCreateRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product to(PurgomalumClient purgomalumClient){
        return new Product(name, purgomalumClient, price);
    }
}
