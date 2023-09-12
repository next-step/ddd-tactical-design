package kitchenpos.product.adapter.out.persistence;

import kitchenpos.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// TODO: public 접근 제어자 제거
public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
