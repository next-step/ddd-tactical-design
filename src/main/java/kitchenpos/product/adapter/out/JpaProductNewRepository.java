package kitchenpos.product.adapter.out;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import kitchenpos.product.application.port.out.ProductNewRepository;
import kitchenpos.product.domain.ProductNew;

public interface JpaProductNewRepository  extends ProductNewRepository, JpaRepository<ProductNew, UUID> {
}
