package kitchenpos.products.application;

import kitchenpos.common.external.infra.ProfanityChecker;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.infra.ProductProfanity;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.ui.request.ProductCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final ProductProfanity productProfanityChecker;

    public ProductService(
        final ProductRepository productRepository,
        final MenuRepository menuRepository,
        final ProductProfanity productProfanityChecker
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.productProfanityChecker = productProfanityChecker;
    }

    @Transactional
    public Product create(final ProductCreateRequest request) {
        request.checkProfanityName(productProfanityChecker);
        Product product = request.toEntity();
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
