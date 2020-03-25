package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.common.Price;
import kitchenpos.menus.tobe.domain.menu.vo.NewMenuProduct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<NewMenuProduct> findAllProductPrice (List<NewMenuProduct> newMenuProducts);
}
