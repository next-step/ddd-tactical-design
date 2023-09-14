package kitchenpos.product.application.port.in;

import java.util.List;
import kitchenpos.product.application.ProductDTO;

public interface ProductFindUseCase {

    List<ProductDTO> findAll();
}
