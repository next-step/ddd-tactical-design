package kitchenpos.products.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.products.application.dto.ProductCreateRequest;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.ProfanityValidator;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductPrice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final ProfanityValidator profanityValidator;

    public ProductService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final ProfanityValidator profanityValidator
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.profanityValidator = profanityValidator;
    }

    @Transactional
    public Product create(final ProductCreateRequest request) {
        request.validateName(profanityValidator);
        return productRepository.save(request.to());
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductPrice request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(request);

        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        menus.forEach(menu -> menu.changeMenuProductPrice(productId, request));
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}