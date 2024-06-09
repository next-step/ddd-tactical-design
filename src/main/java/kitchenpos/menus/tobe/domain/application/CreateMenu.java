package kitchenpos.menus.tobe.domain.application;

import kitchenpos.common.purgomalum.PurgomalumClient;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;
import kitchenpos.menus.tobe.domain.vo.MenuName;
import kitchenpos.menus.tobe.dto.MenuCreateDto;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@FunctionalInterface
public interface CreateMenu {
    Menu execute(MenuCreateDto menucreateDto);
}

@Service
class DefaultCreateMenu implements CreateMenu {
    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final PurgomalumClient purgomalumClient;

    public DefaultCreateMenu(MenuRepository menuRepository, ProductRepository productRepository, MenuGroupRepository menuGroupRepository, PurgomalumClient purgomalumClient) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public final Menu execute(MenuCreateDto menucreateDto) {
        final MenuPrice menuPrice = MenuPrice.of(menucreateDto.getPrice());
        final MenuGroup menuGroup = menuGroupRepository.findById(menucreateDto.getMenuGroupId())
                                                       .orElseThrow(NoSuchElementException::new);
        final List<MenuProduct> menuProductRequests = menucreateDto.getMenuProducts();
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Product> products = productRepository.findAllByIdIn(
                menuProductRequests.stream()
                                   .map(MenuProduct::getProductId)
                                   .toList()
        );
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<MenuProduct> menuProducts = new ArrayList<>();
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProductRequest : menuProductRequests) {
            final long quantity = menuProductRequest.getQuantity();
            if (quantity < 0) {
                throw new IllegalArgumentException();
            }
            final Product product = productRepository.findById(menuProductRequest.getProductId())
                                                     .orElseThrow(NoSuchElementException::new);
            sum = sum.add(
                    product.getPrice()
                           .multiply(BigDecimal.valueOf(quantity))
            );
            final MenuProduct menuProduct = new MenuProduct(product, quantity);
            menuProducts.add(menuProduct);
        }
        if (menuPrice.getValue().compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }

        final MenuName name = MenuName.of(menucreateDto.getName(), purgomalumClient);

        final Menu menu = new Menu(
            UUID.randomUUID(),
            name,
            menuPrice,
            menuGroup,
            menucreateDto.isDisplayed(),
            menuProducts
        );
        return menuRepository.save(menu);
    }
}
