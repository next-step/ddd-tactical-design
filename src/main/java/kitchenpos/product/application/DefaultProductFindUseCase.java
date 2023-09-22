package kitchenpos.product.application;

import static kitchenpos.support.ParameterValidateUtils.checkNotEmpty;
import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.product.application.port.in.ProductDTO;
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

    @Override
    public List<ProductDTO> findByIds(final List<UUID> ids) {
        checkNotEmpty(ids, "ids");

        return repository.findAllByIdIn(ids)
            .stream()
            .map(ProductDTO::new)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<ProductDTO> findById(final UUID id) {
        checkNotNull(id, "id");
        
        return repository.findById(id)
            .map(ProductDTO::new);
    }
}
