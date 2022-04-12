package kitchenpos.products.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import kitchenpos.products.domain.Product;
import kitchenpos.products.application.port.out.ProductRepository;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
