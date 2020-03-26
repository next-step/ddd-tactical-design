package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.vo.MenuProductVO;
import kitchenpos.menus.tobe.domain.menu.vo.MenuProducts;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    MenuProducts findAllPrice (MenuProducts menuProducts);
}
