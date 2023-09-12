package kitchenpos.product.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.product.application.exception.NotExistProductException;
import kitchenpos.product.application.port.in.ProductUseCase;
import kitchenpos.product.application.port.out.ProductNewRepository;
import kitchenpos.product.application.port.out.ProductPriceChangeEventPublisher;
import kitchenpos.product.domain.Name;
import kitchenpos.product.domain.ProductNameFactory;
import kitchenpos.product.domain.ProductNew;
import kitchenpos.product.domain.ProductPrice;
import org.springframework.transaction.annotation.Transactional;

public class DefaultProductUseCase implements ProductUseCase {

    private final ProductNameFactory nameFactory;
    private final ProductNewRepository repository;
    private final ProductPriceChangeEventPublisher publisher;

    public DefaultProductUseCase(final ProductNameFactory nameFactory,
        final ProductNewRepository repository,
        final ProductPriceChangeEventPublisher publisher) {

        this.nameFactory = nameFactory;
        this.repository = repository;
        this.publisher = publisher;
    }

    @Override
    public void register(final Name nameCandidate, final ProductPrice price) {
        final ProductNew product
            = ProductNew.newOf(nameFactory.create(nameCandidate), price);

        repository.save(product);
    }

    @Transactional
    @Override
    public void changePrice(final UUID id, final ProductPrice price) {
        final ProductNew product = repository.findById(id)
            .orElseThrow(() -> new NotExistProductException(id));

        product.changePrice(price);

        publisher.publish(id);
    }

    @Override
    public List<ProductDTO> findAll() {
        return repository.findAll()
            .stream()
            .map(ProductDTO::new)
            .collect(Collectors.toUnmodifiableList());
    }
}
