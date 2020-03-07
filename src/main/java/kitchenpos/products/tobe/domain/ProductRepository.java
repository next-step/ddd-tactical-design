package kitchenpos.products.tobe.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{

    Product save(Product product);

    List<Product> findAll();
}
