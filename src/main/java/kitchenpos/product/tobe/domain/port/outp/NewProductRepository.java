package kitchenpos.product.tobe.domain.port.outp;

import kitchenpos.product.tobe.domain.NewProduct;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewProductRepository {
    NewProduct save(NewProduct product);

    Optional<NewProduct> findById(UUID id);

    List<NewProduct> findAll();

    List<NewProduct> findAllByIdIn(List<UUID> ids);
}

