package kitchenpos.menus.application;

import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.domain.tobe.domain.TobeMenuGroupRepository;
import kitchenpos.menus.domain.tobe.domain.TobeMenuRepository;
import kitchenpos.menus.dto.MenuDisplayRequest;
import kitchenpos.menus.dto.MenuHideRequest;
import kitchenpos.menus.dto.MenuPriceChangeRequest;
import kitchenpos.menus.dto.MenuRegisterRequest;
import kitchenpos.products.domain.tobe.domain.TobeProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TobeMenuService {
    private final TobeMenuRepository menuRepository;
    private final TobeMenuGroupRepository menuGroupRepository;
    private final TobeProductRepository productRepository;

    public TobeMenuService(
            final TobeMenuRepository menuRepository,
            final TobeMenuGroupRepository menuGroupRepository,
            final TobeProductRepository productRepository
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public TobeMenu create(final MenuRegisterRequest request) {
        TobeMenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(NoSuchElementException::new);
        TobeMenu menu = new TobeMenu.Builder()
                .name(request.getName())
                .namingRule(request.getNamingRule())
                .price(request.getPrice())
                .pricingRule(request.getPricingRule())
                .menuProducts(request.getMenuProducts())
                .menuGroup(menuGroup)
                .displayed(request.isDisplayed())
                .build();
        return menuRepository.save(menu);
    }

    @Transactional
    public TobeMenu changePrice(final MenuPriceChangeRequest request) {
        TobeMenu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(NoSuchElementException::new);
        return menu.changePrice(request.getPrice(), request.getPricingRule());
    }

    @Transactional
    public TobeMenu display(final MenuDisplayRequest request) {
        TobeMenu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(NoSuchElementException::new);
        return menu.display(request.getPricingRule());
    }

    @Transactional
    public TobeMenu hide(final MenuHideRequest request) {
        TobeMenu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(NoSuchElementException::new);
        return menu.hide();
    }

    @Transactional(readOnly = true)
    public List<TobeMenu> findAll() {
        return menuRepository.findAll();
    }
}
