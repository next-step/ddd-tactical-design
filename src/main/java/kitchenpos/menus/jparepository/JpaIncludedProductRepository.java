package kitchenpos.menus.jparepository;

import kitchenpos.menus.tobe.domain.entity.IncludedProduct;
import kitchenpos.menus.tobe.domain.repository.IncludedProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaIncludedProductRepository extends JpaRepository<IncludedProduct, UUID>, IncludedProductRepository {
}
