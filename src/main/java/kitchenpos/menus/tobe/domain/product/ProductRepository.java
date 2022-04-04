package kitchenpos.menus.tobe.domain.product;

import java.util.Optional;


public interface ProductRepository {

  Optional<Product> findById(Long id);

}
