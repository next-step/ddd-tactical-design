package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.DisplayName;
import kitchenpos.menus.tobe.domain.Displayed;
import kitchenpos.menus.tobe.domain.MenuPrice;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.TobeMenu;
import kitchenpos.menus.tobe.domain.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository;
import kitchenpos.menus.tobe.domain.TobeMenuProduct;
import kitchenpos.menus.tobe.domain.TobeMenuRepository;
import kitchenpos.menus.tobe.dto.request.MenuCreateRequest;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static kitchenpos.menus.tobe.domain.Displayed.isDisplayed;

@Service
public class TobeMenuService {
    private final TobeMenuRepository menuRepository;
    private final TobeMenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;

    public TobeMenuService(
            final TobeMenuRepository menuRepository,
            final TobeMenuGroupRepository menuGroupRepository,
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
    }

    /**
     * 상품 생성
     *
     * @param request the request
     * @return the menu
     */
    @Transactional
    public TobeMenu create(final MenuCreateRequest request) {

        DisplayName displayName = DisplayName.of(request.name(), purgomalumClient);
        MenuPrice menuPrice = MenuPrice.of(request.price());
        List<MenuCreateRequest.MenuProductRequest> menuProductRequests = request.menuProductRequests();
        Displayed displayed = isDisplayed(request.display());

        final TobeMenuGroup menuGroup = menuGroupRepository.findById(request.menuGroupId())
                .orElseThrow(() -> new NoSuchElementException("메뉴 그룹이 존재하지 않습니다."));

        MenuProducts menuProducts = MenuProducts.of(menuProductRequests.stream()
                .map(p -> {
                    Product product = productRepository.findById(p.productId())
                            .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다."));
                    return TobeMenuProduct.create(product.getId(), product.getPrice(), p.quantity());
                }).toList());


        final TobeMenu menu = TobeMenu.create(displayName, menuPrice, menuGroup.getId(), displayed, menuProducts);
        return menuRepository.save(menu);
    }
}
