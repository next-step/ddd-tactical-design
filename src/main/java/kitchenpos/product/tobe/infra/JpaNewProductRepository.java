package kitchenpos.product.tobe.infra;

import kitchenpos.product.tobe.domain.NewProduct;
import kitchenpos.product.tobe.domain.port.out.NewProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaNewProductRepository extends NewProductRepository, JpaRepository<NewProduct, UUID> {
}
