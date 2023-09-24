package kitchenpos.menus.application;

import kitchenpos.menus.shared.dto.MenuDto;
import kitchenpos.menus.shared.dto.request.MenuCreateRequest;
import kitchenpos.menus.shared.dto.request.MenuPriceChangeRequest;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuName;
import kitchenpos.menus.tobe.domain.menu.MenuPrice;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProductQuantity;
import kitchenpos.menus.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.ProductClient;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.shared.util.ConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductClient productClient;
    private final PurgomalumClient purgomalumClient;

    public MenuService(
        final MenuRepository menuRepository,
        final MenuGroupRepository menuGroupRepository,
        final ProductClient productClient,
        final PurgomalumClient purgomalumClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productClient = productClient;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public MenuDto create(final MenuCreateRequest request) {
        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
            .orElseThrow(NoSuchElementException::new);

        if (Objects.isNull(request.getMenuProducts()) || request.getMenuProducts().isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<MenuProduct> menuProducts = request.getMenuProducts().stream()
                .map(menuProductDto -> MenuProduct.of(
                        menuProductDto.getProductId(),
                        new MenuProductQuantity(menuProductDto.getQuantity())
                        , productClient)
                ).collect(Collectors.toList());

        final Menu menu = Menu.of(
                new MenuName(request.getName(), purgomalumClient),
                new MenuPrice(request.getPrice()),
                menuGroup,
                request.isDisplayed(),
                MenuProducts.from(menuProducts, productClient)
        );

        return ConvertUtil.convert(menuRepository.save(menu), MenuDto.class);
    }

    @Transactional
    public MenuDto changePrice(final UUID menuId, final MenuPriceChangeRequest request) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.changeMenuPrice(new MenuPrice(request.getPrice()));

        return ConvertUtil.convert(menu, MenuDto.class);
    }

    @Transactional
    public MenuDto display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);

        menu.display();
        return ConvertUtil.convert(menu, MenuDto.class);
    }

    @Transactional
    public MenuDto hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
            .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return ConvertUtil.convert(menu, MenuDto.class);
    }

    @Transactional(readOnly = true)
    public List<MenuDto> findAll() {
        return ConvertUtil.convertList(menuRepository.findAll(), MenuDto.class);
    }
}
