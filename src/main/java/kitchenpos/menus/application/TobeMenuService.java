package kitchenpos.menus.application;

import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.domain.tobe.domain.TobeMenuGroupRepository;
import kitchenpos.menus.domain.tobe.domain.TobeMenuRepository;
import kitchenpos.menus.domain.tobe.domain.vo.MenuDisplayed;
import kitchenpos.menus.domain.tobe.domain.vo.MenuName;
import kitchenpos.menus.domain.tobe.domain.vo.MenuPrice;
import kitchenpos.menus.dto.*;
import kitchenpos.products.domain.tobe.domain.TobeProductRepository;
import kitchenpos.support.infra.profanity.Profanity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TobeMenuService {
    private final TobeMenuRepository menuRepository;
    private final TobeMenuGroupRepository menuGroupRepository;
    private final TobeProductRepository productRepository;
    private final Profanity profanity;


    public TobeMenuService(
            final TobeMenuRepository menuRepository,
            final TobeMenuGroupRepository menuGroupRepository,
            final TobeProductRepository productRepository,
            final Profanity profanity
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.profanity=profanity;
    }

    @Transactional
    public MenuRegisterResponse create(final MenuRegisterRequest request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        TobeMenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(NoSuchElementException::new);
        TobeMenu menu = new TobeMenu.Builder()
                .name(new MenuName(request.getName(), profanity))
                .price(new MenuPrice(request.getPrice()))
                .menuProducts(request.getMenuProducts())
                .menuGroup(menuGroup)
                .displayed(new MenuDisplayed(request.isDisplayed()))
                .build();
        return new MenuRegisterResponse(menuRepository.save(menu));
    }

    @Transactional
    public MenuPriceChangeResponse changePrice(final MenuPriceChangeRequest request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        TobeMenu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(NoSuchElementException::new);
        return new MenuPriceChangeResponse(menu.changePrice(new MenuPrice(request.getPrice())));
    }

    @Transactional
    public MenuDisplayResponse display(final MenuDisplayRequest request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        TobeMenu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(NoSuchElementException::new);
        return new MenuDisplayResponse(menu.display());
    }

    @Transactional
    public MenuHideResponse hide(final MenuHideRequest request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        TobeMenu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(NoSuchElementException::new);
        return new MenuHideResponse(menu.hide());
    }

    @Transactional(readOnly = true)
    public List<MenuDto> findAll() {
        return menuRepository.findAll().stream().map(MenuDto::new).collect(Collectors.toList());
    }
}
