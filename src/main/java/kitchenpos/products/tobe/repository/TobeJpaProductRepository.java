package kitchenpos.products.tobe.repository;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.dto.response.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TobeJpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {

    @Query("select new kitchenpos.products.tobe.dto.response.ProductResponse(p.id, p.name, p.price) from Product p")
    List<ProductResponse> findAllProductResponse();
}
