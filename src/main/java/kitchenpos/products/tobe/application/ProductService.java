package kitchenpos.products.tobe.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.application.dto.ProductCreateRequest;
import kitchenpos.products.tobe.application.dto.ProductPriceUpdateRequest;
import kitchenpos.products.tobe.application.dto.ProductResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.domain.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository jpaProductRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
        final ProductRepository productRepository,
        final MenuRepository menuRepository,
        final PurgomalumClient purgomalumClient
                         ) {
        this.jpaProductRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        Product product = jpaProductRepository.save(Product.of(request.getName(), request.getPrice(), purgomalumClient));

        return ProductResponse.toResponse(product);
    }

    @Transactional
    public ProductResponse changePrice(UUID productId, ProductPriceUpdateRequest request) {

        Product product = jpaProductRepository.findById(productId)
                                              .orElseThrow(NoSuchElementException::new);
        product.changePrice(request.getPrice());

        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                    menuProduct.getProduct()
                               .getPrice()
                               .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                             );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
        return ProductResponse.toResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return jpaProductRepository.findAll()
                            .stream()
            .map(ProductResponse::toResponse)
            .toList();
    }
}
