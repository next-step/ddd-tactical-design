package kitchenpos.product.application.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.product.domain.ProductNew;

public interface ProductNewRepository {

    ProductNew save(final ProductNew product);

    Optional<ProductNew> findById(final UUID id);

    List<ProductNew> findAll();

    List<ProductNew> findAllByIdIn(final List<UUID> ids);
}

