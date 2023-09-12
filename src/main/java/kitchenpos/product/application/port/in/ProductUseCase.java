package kitchenpos.product.application.port.in;

import java.util.List;
import java.util.UUID;
import kitchenpos.product.application.ProductDTO;
import kitchenpos.product.application.exception.NotExistProductException;
import kitchenpos.product.domain.Name;
import kitchenpos.product.domain.ProductPrice;

public interface ProductUseCase {

    /**
     * @throws IllegalArgumentException nameCandidate에 비속어가 포함되어 있을 때
     */
    void register(final Name nameCandidate, final ProductPrice price);

    /**
     * @throws NotExistProductException id에 해당하는 product가 없을 때
     */
    void changePrice(final UUID id, final ProductPrice price);
    
    List<ProductDTO> findAll();
}
