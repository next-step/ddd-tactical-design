package kitchenpos.product.application;

import java.util.List;
import java.util.stream.Collectors;
import kitchenpos.product.application.port.in.ProductFindUseCase;
import kitchenpos.product.application.port.out.ProductNewRepository;

public class DefaultProductFindUseCase implements ProductFindUseCase {

    private final ProductNewRepository repository;

    public DefaultProductFindUseCase(final ProductNewRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductDTO> findAll() {
        return repository.findAll()
            .stream()
            .map(ProductDTO::new)
            .collect(Collectors.toUnmodifiableList());
    }
}
