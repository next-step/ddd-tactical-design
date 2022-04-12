package kitchenpos.products.tobe.domain;


import java.math.BigDecimal;
import java.util.UUID;

import kitchenpos.common.DomainEntity;
import kitchenpos.products.application.port.out.PurgomalumClient;
import kitchenpos.products.tobe.domain.vo.ProductName;
import kitchenpos.products.tobe.domain.vo.ProductPrice;

@DomainEntity
public final class Product {

    private UUID id;

    private ProductName productName;

    private ProductPrice productPrice;

    private Product() {
    }

    public Product(final String name, final BigDecimal price, final PurgomalumClient purgomalumClient) {
        id = UUID.randomUUID();
        productName = new ProductName(name, purgomalumClient);
        productPrice = new ProductPrice(price);
    }


}
