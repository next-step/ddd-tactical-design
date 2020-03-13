package kitchenpos.menus.tobe.v2.application;

import kitchenpos.menus.tobe.v2.domain.*;
import kitchenpos.menus.tobe.v2.dto.MenuProductRequestDto;
import kitchenpos.menus.tobe.v2.dto.MenuRequestDto;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuProductRepository menuProductRepository;
    private final ProductRepository productRepository;

    public MenuService(MenuRepository menuRepository,
                       MenuGroupRepository menuGroupRepository,
                       MenuProductRepository menuProductRepository,
                       ProductRepository productRepository) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.menuProductRepository = menuProductRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Menu create(MenuRequestDto menuRequestDto) {
        Menu menu = menuRequestDto.toEntity();
        MenuGroup menuGroup = menuGroupRepository
                .findById(menuRequestDto.getMenuGroupId())
                .orElseThrow(IllegalArgumentException::new);

        menu.changeMenuGroup(menuGroup);

        List<MenuProductRequestDto> menuProductRequestDtos = menuRequestDto.getMenuProducts();
        List<MenuProduct> menuProducts = new ArrayList<>();

        BigDecimal sum = BigDecimal.ZERO;

        for (MenuProductRequestDto menuProductRequestDto : menuProductRequestDtos) {
            Long productId = menuProductRequestDto.getProductId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(IllegalArgumentException::new);

            MenuProduct menuProduct = new MenuProduct(product, menuProductRequestDto.getQuantity());

            sum = sum.add(menuProduct.getTotalPrice());

            menuProducts.add(menuProduct);
        }

        menu.validPriceLessThanTotalPrice(sum);

        Menu savedMenu = menuRepository.save(menu);

        for (MenuProduct menuProduct : menuProducts) {
            menuProduct.changeMenu(savedMenu);
            menuProductRepository.save(menuProduct);
        }

        savedMenu.changeMenuProducts(menuProducts);

        return savedMenu;
    }

    public List<Menu> list() {
        return menuRepository.findMenuAll();
    }
}
