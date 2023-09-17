package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.PurgomalumClient;
import kitchenpos.products.ui.dto.request.ProductChangePriceRequest;
import kitchenpos.products.ui.dto.request.ProductCreateRequest;
import kitchenpos.products.ui.dto.response.ProductChangePriceResponse;
import kitchenpos.products.ui.dto.response.ProductCreateResponse;
import kitchenpos.products.ui.dto.response.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public ProductCreateResponse create(final ProductCreateRequest request) {
        final Product product = new Product(request.getName(), purgomalumClient, request.getPrice());
        Product saveProduct = productRepository.save(product);
        return ProductCreateResponse.of(saveProduct);
    }

    @Transactional
    public ProductChangePriceResponse changePrice(final UUID productId, final ProductChangePriceRequest request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(request.getPrice());
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                final Product findProductByMenuProduct = productRepository.findById(menuProduct.getProductId())
                        .orElseThrow(NoSuchElementException::new);
                sum = sum.add(findProductByMenuProduct.multiplyPrice(menuProduct.getQuantity()).getValue());
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.hide();
            }
        }
        return ProductChangePriceResponse.of(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
