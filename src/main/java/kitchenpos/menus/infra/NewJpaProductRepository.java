package kitchenpos.menus.infra;

import kitchenpos.menus.tobe.domain.NewProduct;
import kitchenpos.menus.tobe.domain.ProductRepostiory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NewJpaProductRepository extends ProductRepostiory, JpaRepository<NewProduct, UUID> {
    List<NewProduct> findAllByIdIn(List<UUID> productIds);
}
