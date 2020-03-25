package kitchenpos.products.tobe.infra;

import kitchenpos.common.Price;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository{
    ProductEntity save (ProductEntity productEntity);
    Optional<ProductEntity> findById (Long id);
    List<ProductEntity> list ();
    boolean findByName (String name);
    BigDecimal findProductPriceById (Long id)
}
