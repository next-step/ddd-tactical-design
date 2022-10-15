package kitchenpos.products.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.global.event.ChangeProductPriceEvent;
import kitchenpos.products.domain.DisplayedName;
import kitchenpos.products.domain.Price;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.ProfanityValidator;
import kitchenpos.products.dto.ProductChangeRequest;
import kitchenpos.products.dto.ProductCreateRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProfanityValidator profanityValidator;
    private final ApplicationEventPublisher applicationEventPublisher;
    public ProductService(
        final ProductRepository productRepository,
        final ProfanityValidator profanityValidator,
        final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.productRepository = productRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.profanityValidator = profanityValidator;
    }

    @Transactional
    public Product create(final ProductCreateRequest request) {
        return productRepository.save(
                new Product(DisplayedName.of(request.getName(), profanityValidator), new Price(request.getPrice()))
        );
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductChangeRequest request) {
        final Price changePrice = new Price(request.getPrice());
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);

        product.changePrice(changePrice);
        applicationEventPublisher.publishEvent(new ChangeProductPriceEvent(productId));
        // 추후 메뉴 리펙토링 시 참조 주석
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
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
