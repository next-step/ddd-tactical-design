package kitchenpos.menus.domain;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kitchenpos.menus.domain.vo.MenuProductVo;
import kitchenpos.menus.domain.vo.MenuVo;
import kitchenpos.menus.domain.vo.ProductVo;

@Component
public class MenuFactory {
    private final MenuGroupRepository menuGroupRepository;
    private final MenuPricePolicy menuPricePolicy;
    private final PurgomalumClient purgomalumClient;
    private final ProductApiRepository productApiRepository;

    public MenuFactory(
            final MenuGroupRepository menuGroupRepository,
            final MenuPricePolicy menuPricePolicy,
            final PurgomalumClient purgomalumClient, ProductApiRepository productApiRepository) {
        this.menuGroupRepository = menuGroupRepository;
        this.menuPricePolicy = menuPricePolicy;
        this.purgomalumClient = purgomalumClient;
        this.productApiRepository = productApiRepository;
    }

    public Menu create(MenuVo menuVo) {
        final MenuGroup menuGroup = menuGroupRepository.findById(menuVo.getMenuGroupId())
                .orElseThrow(NoSuchElementException::new);

        final MenuProducts menuProducts = createMenuProducts(menuVo.getMenuProducts());

        return new Menu(
                UUID.randomUUID(),
                menuVo.getName(), purgomalumClient,
                menuVo.getPrice(),
                menuGroup,
                menuProducts,
                menuPricePolicy
        );
    }

    /**
     * menuProductsRequest로 MenuProducts를 만든다.
     * - menuProductsRequest의 productId가 모두 존재하는지 확인한다. (getPrice)
     */
    private MenuProducts createMenuProducts(List<MenuProductVo> menuProductsRequest) {
        if (menuProductsRequest == null) {
            throw new IllegalArgumentException();
        }
        List<ProductVo> products = productApiRepository.findAllByIdIn(menuProductsRequest.stream()
                .map(MenuProductVo::getProductId)
                .collect(Collectors.toList()));

        return new MenuProducts(menuProductsRequest.stream()
                .map(menuProduct -> createMenuProduct(products, menuProduct))
                .collect(Collectors.toList())
        );
    }

    private MenuProduct createMenuProduct(List<ProductVo> products, MenuProductVo menuProduct) {
        UUID productId = menuProduct.getProductId();
        ProductVo productVo = findProductDto(products, productId);
        return new MenuProduct(
                productId,
                new Quantity(menuProduct.getQuantity()),
                new Price(productVo.getPrice())
        );
    }

    private ProductVo findProductDto(List<ProductVo> products, UUID productId) {
        return products.stream()
                .filter(productVo -> productVo.getProductId().equals(productId))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
