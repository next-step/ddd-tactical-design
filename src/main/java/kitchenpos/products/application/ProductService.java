package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
        final ProductRepository productRepository,
        final MenuRepository menuRepository,
        final PurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final Product request) {
        String name = request.getName().getName();
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        Product product = new Product(request.getName(), request.getPrice());
        return productRepository.save(product);
    }

//    @Transactional
//    public Product changePrice(final UUID productId, final Product request) {
//        Product product1 = new Product(productId, request.getName(), request.getPrice());
//
//        final BigDecimal price = request.getPrice();
//        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
//            throw new IllegalArgumentException();
//        }
//        final Product product = productRepository.findById(productId)
//            .orElseThrow(NoSuchElementException::new);
//        product.setPrice(price);
//        final List<Menu> menus = menuRepository.findAllByProductId(productId);
//        for (final Menu menu : menus) {
//            BigDecimal sum = BigDecimal.ZERO;
//            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
//                sum = sum.add(
//                    menuProduct.getProduct()
//                        .getPrice()
//                        .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
//                );
//            }
//            if (menu.getPrice().compareTo(sum) > 0) {
//                menu.setDisplayed(false);
//            }
//        }
//        return product;
//    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
