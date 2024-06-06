package kitchenpos.product.tobe.domain;

import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.menu.tobe.domain.MenuRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class FakeProductService implements ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;

    public FakeProductService(ProductRepository productRepository, MenuRepository menuRepository) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public void syncMenuDisplayStatisWithProductPrices(UUID productId, BigDecimal newPrice) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);

        product.updateProductPrice(newPrice);
        productRepository.save(product);

        final List<Menu> menus = menuRepository.findAllByProductId(product.getId());
        for (final Menu menu : menus) {
// TODO
//            for (MenuProduct menuProduct : menu.getMenuProducts().getMenuProducts()) {
//                if (menuProduct.getProduct().getId().equals(product.getId())) {
//                }
//            }

            if (menu.getMenuPrice().compareTo(menu.getMenuProducts().getTotalPrice()) > 0) {
                menu.changeMenuDisplayStatus(false);
            }
        }
    }
}
