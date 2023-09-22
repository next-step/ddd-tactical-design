package kitchenpos.product.tobe.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.common.profanity.ProfanityClient;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.product.tobe.application.dto.ChangeProductPriceRequest;
import kitchenpos.product.tobe.application.dto.ChangeProductPriceResponse;
import kitchenpos.product.tobe.application.dto.CreateProductRequest;
import kitchenpos.product.tobe.application.dto.CreateProductResponse;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final ProfanityClient profanityClient;

    public ProductService(
        final ProductRepository productRepository,
        final MenuRepository menuRepository,
        final ProfanityClient profanityClient
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.profanityClient = profanityClient;
    }

    @Transactional
    public CreateProductResponse create(final CreateProductRequest request) {
        final String name = request.getName();
        if (profanityClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }

        final Product product = new Product(UUID.randomUUID(), name, request.getPrice());
        return CreateProductResponse.of(productRepository.save(product));
    }

    @Transactional
    public ChangeProductPriceResponse changePrice(final UUID productId, final ChangeProductPriceRequest request) {
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(request.getPrice());

        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        menus.forEach(Menu::checkPrice);

        return ChangeProductPriceResponse.of(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
