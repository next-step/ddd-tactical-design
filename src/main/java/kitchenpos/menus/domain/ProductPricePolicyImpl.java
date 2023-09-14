package kitchenpos.menus.domain;

import kitchenpos.common.DomainService;
import kitchenpos.products.tobe.domain.ProductPricePolicy;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;

import java.util.List;

@DomainService
public class ProductPricePolicyImpl implements ProductPricePolicy {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;

    public ProductPricePolicyImpl(ProductRepository productRepository, MenuRepository menuRepository) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
    }

    public void changePrice(Product product, ProductPrice price) {
        product.changePrice(price);
        final List<Menu> menus = menuRepository.findAllByProductId(product.getId());
        for (final Menu menu : menus) {
            menu.changeMenuProductPrice(product);
        }
        productRepository.save(product);
        menuRepository.saveAll(menus);
    }
}
