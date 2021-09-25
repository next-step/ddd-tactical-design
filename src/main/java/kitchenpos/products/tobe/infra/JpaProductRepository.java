package kitchenpos.products.tobe.infra;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("tobeProductRepository")
public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {}
