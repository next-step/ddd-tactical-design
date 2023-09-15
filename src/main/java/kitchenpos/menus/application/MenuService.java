package kitchenpos.menus.application;

import kitchenpos.menus.dto.MenuChangePriceRequest;
import kitchenpos.menus.dto.MenuCreateRequest;
import kitchenpos.menus.dto.MenuDetailResponse;
import kitchenpos.menus.dto.MenuProductElement;
import kitchenpos.menus.mapper.MenuMapper;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.support.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static java.util.stream.Collectors.toUnmodifiableList;
import static kitchenpos.menus.mapper.MenuMapper.toMenuDetailResponse;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;

    public MenuService(MenuRepository menuRepository, MenuGroupRepository menuGroupRepository, ProductRepository productRepository, PurgomalumClient purgomalumClient) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public MenuDetailResponse create(final MenuCreateRequest request) {
        final List<MenuProductElement> menuProductRequests = request.getMenuProducts();

        List<Product> products = productRepository.findAllByIdIn(menuProductRequests.parallelStream()
                .map(MenuProductElement::getProductId)
                .collect(toUnmodifiableList()));

        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException("메뉴 상품에 존재하지 않는 상품이 포함되어 있습니다.");
        }

        final MenuGroup menuGroup = menuGroupRepository.findById(request.getMenuGroupId())
                .orElseThrow(() -> new NoSuchElementException("메뉴 그룹이 존재하지 않습니다."));

        final List<MenuProduct> menuProducts = new ArrayList<>();
        for (MenuProductElement menuProductElement : menuProductRequests) {
            final Product product = productRepository.findById(menuProductElement.getProductId())
                    .orElseThrow(NoSuchElementException::new);
            menuProducts.add(MenuProduct.create(product, MenuProductQuantity.create(menuProductElement.getQuantity())));
        }

        final Menu menu = Menu.create(
                MenuName.create(request.getName(), purgomalumClient),
                MenuPrice.create(request.getPrice()),
                menuGroup,
                MenuDisplay.create(request.isDisplayed()),
                menuProducts
        );
        return toMenuDetailResponse(menuRepository.save(menu));
    }

    @Transactional
    public MenuDetailResponse changePrice(final UUID menuId, final MenuChangePriceRequest request) {
        final Menu menu = getMenuById(menuId);
        menu.changePrice(MenuPrice.create(request.getPrice()));
        return toMenuDetailResponse(menu);
    }

    @Transactional
    public MenuDetailResponse display(final UUID menuId) {
        final Menu menu = getMenuById(menuId);
        menu.display();
        return toMenuDetailResponse(menu);
    }

    @Transactional
    public MenuDetailResponse hide(final UUID menuId) {
        final Menu menu = getMenuById(menuId);
        menu.hide();
        return toMenuDetailResponse(menu);
    }

    @Transactional(readOnly = true)
    public List<MenuDetailResponse> findAll() {
        return menuRepository.findAll()
                .stream()
                .map(MenuMapper::toMenuDetailResponse)
                .collect(toUnmodifiableList());
    }

    private Menu getMenuById(UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementException("메뉴가 존재하지 않습니다."));
    }
}
