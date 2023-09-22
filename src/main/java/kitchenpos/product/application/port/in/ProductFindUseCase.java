package kitchenpos.product.application.port.in;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductFindUseCase {

    List<ProductDTO> findAll();

    List<ProductDTO> findByIds(final List<UUID> ids);

    Optional<ProductDTO> findById(final UUID id);
}
