package kitchenpos.products.tobe.repository;

import kitchenpos.products.tobe.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {}
