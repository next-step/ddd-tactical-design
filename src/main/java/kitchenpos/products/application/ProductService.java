package kitchenpos.products.application;

import kitchenpos.products.domain.ProductProfanityCheckClient;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.ui.request.ProductRequest;
import kitchenpos.products.ui.response.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductProfanityCheckClient profanityCheckClient;

    public ProductService(
        ProductRepository productRepository,
        ProductProfanityCheckClient profanityCheckClient
    ) {
        this.productRepository = productRepository;
        this.profanityCheckClient = profanityCheckClient;
    }

    @Transactional
    public ProductResponse create(final ProductRequest request) {
        Product product = new Product(
            request.getName(),
            request.getPrice(),
            profanityCheckClient
        );

        return ProductResponse.from(productRepository.save(product));
    }

//    @Transactional
//    public Product changePrice(final UUID productId, final Product request) {
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
//
//    @Transactional(readOnly = true)
//    public List<Product> findAll() {
//        return productRepository.findAll();
//    }
}
