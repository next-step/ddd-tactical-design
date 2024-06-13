package kitchenpos.products.tobe.domain.application;

import kitchenpos.common.purgomalum.PurgomalumClient;
import kitchenpos.products.tobe.domain.repository.ProductRepository;

public class CreateProductTestFixture extends DefaultCreateProduct {

    public CreateProductTestFixture(PurgomalumClient purgomalumClient,
                                    ProductRepository productRepository) {
        super(purgomalumClient, productRepository);
    }
}
