package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductMenuService;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductMenuServiceImpl implements ProductMenuService {
    private MenuRepository menuRepository;
    private ProductRepository productRepository;

    public ProductMenuServiceImpl(MenuRepository menuRepository, ProductRepository productRepository) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void validateMenuPrice(UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException());
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                        product
                                .getPrice()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.hide();
            }
        }
    }
}
