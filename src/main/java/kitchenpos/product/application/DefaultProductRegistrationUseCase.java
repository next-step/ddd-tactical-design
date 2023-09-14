package kitchenpos.product.application;

import static kitchenpos.product.support.constant.Name.PRICE;
import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import kitchenpos.product.application.port.in.ProductRegistrationUseCase;
import kitchenpos.product.application.port.out.ProductNewRepository;
import kitchenpos.product.domain.Name;
import kitchenpos.product.domain.ProductName;
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
    public ProductDTO register(final Name productNameCandidate, final ProductPrice price) {
        checkNotNull(productNameCandidate, "productNameCandidate");
        checkNotNull(price, PRICE);

        final ProductName productName = productNameFactory.create(productNameCandidate);

        final ProductNew product
            = repository.save(ProductNew.newOf(productName, price));

        return new ProductDTO(product);
    }
}
