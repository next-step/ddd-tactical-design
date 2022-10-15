package kitchenpos.menus.tobe.application;


import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.dto.menu.ChangePriceRequest;
import kitchenpos.menus.tobe.dto.menu.MenuCreateRequest;
import kitchenpos.menus.tobe.dto.menu.MenuProductRequest;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;

    public MenuService(final MenuRepository menuRepository, final ProductRepository productRepository) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final ChangePriceRequest request) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
        menu.changePrice(new Price(request.price()));
        return menu;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        for (MenuProductRequest menuProductRequest : request.getMenuProducts()) {
            productRepository.findById(menuProductRequest.getProductRequest().getProductId()).orElseThrow(() -> new NoSuchElementException("해당하는 상품이 업습니다."));
        }
        return new Menu(null, null, null);
    }
}
