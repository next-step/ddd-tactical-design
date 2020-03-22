package kitchenpos.products.tobe.infra;

import kitchenpos.products.tobe.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository{
    Product save (Product product);
    Optional<Product> findById (Long id);
    List<Product> list ();
<<<<<<< HEAD
    boolean findByName (String name);
=======
    boolean findByNameContaining (String name);
>>>>>>> 0d1e94fb190f30830130e9e491a54a89f691ce7c
}
