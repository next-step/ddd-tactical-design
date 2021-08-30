package kitchenpos.products.tobe.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProductPriceChangeService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;

    public ProductPriceChangeService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public Product changePrice(final UUID productId, final BigDecimal price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);

        List<Menu> menus = menuRepository.findAllByProductId(product.getId());
        Map<UUID, Product> products = productRepository.findAllById(menus.stream()
                .map(Menu::getProductIds)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
        menus.forEach(menu -> menu.comparePriceAndHideOrNotByProducts(products));

        return product;
    }
}
