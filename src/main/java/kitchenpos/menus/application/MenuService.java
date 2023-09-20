package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.exception.NotFoundMenuException;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuCreateService menuCreateService;
    private final MenuChangePriceService menuChangePriceService;
    private final PurgomalumClient purgomalumClient;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuCreateService menuCreateService,
            final MenuChangePriceService menuChangePriceService,
            final PurgomalumClient purgomalumClient) {
        this.menuRepository = menuRepository;
        this.menuCreateService = menuCreateService;
        this.menuChangePriceService = menuChangePriceService;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Menu create(final Menu request) {
        this.menuCreateService.valid(request);
        this.menuChangePriceService.valid(request.getMenuProducts(), request.getPrice());
        return this.menuRepository.save(new Menu(request, purgomalumClient));
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final BigDecimal price) {
        return this.menuRepository.save(getMenu(menuId, price).changePrice(price));
    }

    public void changeProductPrice(final UUID productId, final BigDecimal price) {
        this.menuRepository.findAllByProductId(productId)
                .forEach(menu -> changePrice(menu.getId(), price));

    }

    @Transactional
    public Menu display(final UUID menuId) {
        this.menuChangePriceService.valid(getMenu(menuId).getMenuProducts(), getMenu(menuId).getPrice());
        return this.menuRepository.save(getMenu(menuId).displayed());
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        Menu menu = getMenu(menuId).hide();
        return this.menuRepository.save(menu);
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return this.menuRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Menu getMenu(UUID menuId) {
        Menu menu = this.menuRepository.findById(menuId)
                .orElseThrow(NotFoundMenuException::new);
        this.menuChangePriceService.valid(menu.getMenuProducts(), menu.getPrice());
        return menu;
    }

    @Transactional(readOnly = true)
    public Menu getMenu(UUID menuId, BigDecimal price) {
        Menu menu = this.menuRepository.findById(menuId)
                .orElseThrow(NotFoundMenuException::new);
        this.menuChangePriceService.valid(menu.getMenuProducts(), price);
        return menu;
    }

}
