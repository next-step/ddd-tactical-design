package kitchenpos.menus.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menugroups.domain.MenuGroupRepository;
import kitchenpos.menugroups.domain.tobe.MenuGroup;
import kitchenpos.menus.application.dto.MenuCreateRequest;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.menus.domain.tobe.MenuPrice;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.ProfanityValidator;
import kitchenpos.products.domain.tobe.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;

    private final ProductRepository productRepository;
    private final ProfanityValidator profanityValidator;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            ProductRepository productRepository,
            final ProfanityValidator profanityValidator
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.profanityValidator = profanityValidator;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        request.validateName(profanityValidator);
        final MenuGroup menuGroup = findMenuGroup(request.getMenuGroupId());
        List<Product> products = findProducts(request.getProductIds());
        return menuRepository.save(request.to(menuGroup, products));
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuPrice request) {
        final Menu menu = findMenu(menuId);
        menu.changePrice(request);
        return menu;
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = findMenu(menuId);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = findMenu(menuId);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    private MenuGroup findMenuGroup(UUID menuGroupId) {
        return menuGroupRepository.findById(menuGroupId)
                .orElseThrow(NoSuchElementException::new);
    }

    private List<Product> findProducts(List<UUID> productIds) {
        return productRepository.findAllByIdIn(productIds);
    }

    private Menu findMenu(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }
}
