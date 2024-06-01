package kitchenpos.products.application.tobe;

import jakarta.transaction.Transactional;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.menus.domain.tobe.MenuRepository;
import kitchenpos.products.domain.tobe.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
    private final Product product;
    private final MenuRepository menuRepository;

    public ProductService(final Product product, final MenuRepository menuRepository) {
        this.product = product;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public Product changePrice(final BigDecimal price) {
        product.changePrice(price);

        List<Menu> menus = menuRepository.findAllByProductId(product.getId());
        menus.stream().forEach(a -> a.changeProductPrice(product.getId(), price));

        for (Menu menu : menus) {
            menuRepository.save(menu);
        }

        return product;
    }
}
