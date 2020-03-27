package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.vo.MenuProducts;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    MenuProducts findAllPrice (MenuProducts menuProducts);
}
