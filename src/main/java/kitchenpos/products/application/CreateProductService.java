package kitchenpos.products.application;

import kitchenpos.common.domain.vo.Name;
import kitchenpos.common.domain.infra.PurgomalumValidator;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.common.domain.vo.Price;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CreateProductService {
    private final PurgomalumValidator purgomalumValidator;

    private final ProductRepository productRepository;

    public CreateProductService(
        final PurgomalumValidator purgomalumValidator,
        final ProductRepository productRepository
    ) {
        this.purgomalumValidator = purgomalumValidator;
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(final BigDecimal price, final String name) {
        final Price productPrice = new Price(price);
        final Name productName = new Name(name, purgomalumValidator);

        Product product = new Product(productName, productPrice);

        return productRepository.save(product);
    }
}
