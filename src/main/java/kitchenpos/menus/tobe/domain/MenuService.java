package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class MenuService {

    private MenuRepository menuRepository;
    private MenuGroupService menuGroupService;
    private ProductRepository productRepository;

    public MenuService(
            MenuRepository menuRepository,
            MenuGroupService menuGroupService,
            ProductRepository productRepository
    ) {
        this.menuRepository = menuRepository;
        this.menuGroupService = menuGroupService;
        this.productRepository = productRepository;
    }

    public Menu create(final Menu menu) {
        menu.validateByMenuGroup(menuGroupService.list());

        final List<MenuProduct> menuProducts = menu.getMenuProducts();

        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menuProducts) {
            final Product product = productRepository.findById(menuProduct.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품은 존재하지 않습니다."));
            sum = sum.add(menuProduct.applyQuantity(product.getPrice()));
        }
        menu.validate(sum);
        return menuRepository.save(menu);
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }
}
