package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.DisplayedName;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.domain.ProfanityClient;
import kitchenpos.products.ui.dto.ChangePriceRequest;
import kitchenpos.products.ui.dto.CreateProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final ProfanityClient profanityClient;

    public ProductService(ProductRepository productRepository, MenuRepository menuRepository, ProfanityClient profanityClient) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.profanityClient = profanityClient;
    }

    @Transactional
    public Product create(CreateProductRequest request) {
        DisplayedName displayedName = new DisplayedName(request.getName(), profanityClient);
        Price price = new Price(request.getPrice());

        Product newProduct = new Product(displayedName, price);
        return productRepository.save(newProduct);
    }

    @Transactional
    public Product changePrice(UUID productId, ChangePriceRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow();

        List<Menu> menus = menuRepository.findAllByProductId(product.getId());
        for (final Menu menu : menus) {
            Price sum = new Price(menu.calculateProductPrice());
            if (new Price(menu.getPrice()).compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }

        product.changePrice(request.getPrice());
        return product;
    }


    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
