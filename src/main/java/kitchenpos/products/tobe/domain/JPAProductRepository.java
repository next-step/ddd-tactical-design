package kitchenpos.products.tobe.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("tobeJPAProductRepository")
public interface JPAProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
