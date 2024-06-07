package kitchenpos.menus.tobe.domain.application;

import kitchenpos.common.purgomalum.PurgomalumClient;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.menus.tobe.domain.vo.MenuPrice;
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
            final MenuProduct menuProduct = new MenuProduct();
            menuProduct.setProduct(product);
            menuProduct.setQuantity(quantity);
            menuProducts.add(menuProduct);
        }
        if (menuPrice.getValue().compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
        final String name = menucreateDto.getName();
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        final Menu menu = new Menu();
        menu.setId(UUID.randomUUID());
        menu.setName(name);
        menu.setPrice(menuPrice);
        menu.setMenuGroup(menuGroup);
        menu.setDisplayed(menucreateDto.isDisplayed());
        menu.setMenuProducts(menuProducts);
        return menuRepository.save(menu);
    }
}