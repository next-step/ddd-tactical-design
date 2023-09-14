package kitchenpos.product.application;

import kitchenpos.product.application.port.in.ProductRegistrationUseCase;
import kitchenpos.product.application.port.out.ProductNewRepository;
import kitchenpos.product.domain.Name;
import kitchenpos.product.domain.ProductNameFactory;
import kitchenpos.product.domain.ProductNew;
import kitchenpos.product.domain.ProductPrice;

public class DefaultProductRegistrationUseCase implements ProductRegistrationUseCase {

    private final ProductNameFactory productNameFactory;
    private final ProductNewRepository repository;


    public DefaultProductRegistrationUseCase(final ProductNameFactory productNameFactory,
        final ProductNewRepository repository) {

        this.productNameFactory = productNameFactory;
        this.repository = repository;
    }

    @Override
    public void register(final Name productNameCandidate, final ProductPrice price) {
        final ProductNew product
            = ProductNew.newOf(productNameFactory.create(productNameCandidate), price);

        repository.save(product);
    }
}
