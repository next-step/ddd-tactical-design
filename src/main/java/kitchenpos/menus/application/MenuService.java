package kitchenpos.menus.application;


import kitchenpos.common.domain.DisplayNameChecker;
import kitchenpos.menus.application.dto.*;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.application.dto.MenuCreateRequest;
import kitchenpos.menus.tobe.domain.dto.MenuCreateDto;
import kitchenpos.menus.application.dto.MenuProductCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.menus.exception.MenuProductExceptionMessage.NOT_EQUAL_MENU_PRODUCT_SIZE;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final DisplayNameChecker displayNameChecker;
    private final ProductQueryService productQueryService;

    public MenuService(
            final MenuRepository menuRepository,
            final MenuGroupRepository menuGroupRepository,
            final DisplayNameChecker displayNameChecker,
            final ProductQueryService productQueryService
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.displayNameChecker = displayNameChecker;
        this.productQueryService = productQueryService;
    }

    @Transactional
    public MenuInfoResponse create(final MenuCreateRequest request) {
        final NewMenuGroup newMenuGroup = findById(request.getMenuGroupId());
        List<UUID> productIds = getProductIds(request.getMenuProducts());
        NewMenu menu = NewMenu.createMenu(
                productIds, productQueryService::findAllByIdIn, UUID.randomUUID(),
                displayNameChecker, newMenuGroup.getId(), createMenuCreateDto(request)
        );
        NewMenu savedMenu = menuRepository.save(menu);
        return createResponse(savedMenu);
    }

    @Transactional
    public MenuChangePriceResponse changePrice(final UUID menuId, MenuChangePriceRequest request) {
        final NewMenu newMenu = findMenuById(menuId);
        Map<UUID, BigDecimal> productPriceMap = productQueryService.findAllByIdIn(newMenu.getMenuProductIds());
        newMenu.changePrice(productPriceMap, request.getPrice());
        return new MenuChangePriceResponse(newMenu.getId(), newMenu.getPrice());
    }

    @Transactional
    public MenuDisplayResponse display(final UUID menuId) {
        final NewMenu newMenu = findMenuById(menuId);
        Map<UUID, BigDecimal> productPriceMap = productQueryService.findAllByIdIn(newMenu.getMenuProductIds());
        newMenu.displayed(productPriceMap);
        return new MenuDisplayResponse(newMenu.getId(), newMenu.isDisplayed());
    }

    @Transactional
    public MenuDisplayResponse hide(final UUID menuId) {
        final NewMenu newMenu = findMenuById(menuId);
        newMenu.notDisplayed();
        return new MenuDisplayResponse(newMenu.getId(), newMenu.isDisplayed());
    }

    @Transactional(readOnly = true)
    public List<MenuInfoResponse> findAll() {
        List<NewMenu> savedMenusList = menuRepository.findAll();
        return savedMenusList.stream()
                .map(this::createResponse)
                .collect(Collectors.toList());
    }

    private NewMenuGroup findById(UUID id) {
        return menuGroupRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    private NewMenu findMenuById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }

    private List<UUID> getProductIds(List<MenuProductCreateRequest> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new IllegalArgumentException(NOT_EQUAL_MENU_PRODUCT_SIZE);
        }
        return menuProducts.stream()
                .map(MenuProductCreateRequest::getProductId)
                .collect(Collectors.toList());
    }

    private MenuInfoResponse createResponse(NewMenu savedMenu) {
        List<NewMenuProduct> menuProductList = savedMenu.getMenuProductList();
        List<MenuProductInfoResponse> menuProductInfoResponseList = menuProductList.stream()
                .map(m -> new MenuProductInfoResponse(m.getProductId(), m.getQuantity()))
                .collect(Collectors.toList());
        return new MenuInfoResponse(
                savedMenu.getId(),
                savedMenu.getName(),
                savedMenu.getPrice(),
                savedMenu.getMenuGroupId(),
                savedMenu.isDisplayed(),
                menuProductInfoResponseList
        );
    }

    private MenuCreateDto createMenuCreateDto(MenuCreateRequest request) {
        List<MenuProductCreateRequest> menuProducts = request.getMenuProducts();
        Map<UUID, Long> productQuantityMap = menuProducts.stream()
                .collect(Collectors.toMap(MenuProductCreateRequest::getProductId, MenuProductCreateRequest::getQuantity));
        return MenuCreateDto.create(
                request.getPrice(),
                request.getMenuGroupId(),
                request.getName(),
                request.isDisplayed(),
                productQuantityMap
        );
    }

}
