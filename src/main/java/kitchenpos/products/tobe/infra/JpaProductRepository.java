package kitchenpos.products.tobe.infra;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;

@Repository(value = "TobeJpaProductRepository")
public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {

}
