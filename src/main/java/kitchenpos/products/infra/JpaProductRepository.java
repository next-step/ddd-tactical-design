package kitchenpos.products.infra;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository("JpaProductRepository")
public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, Long> {


}
