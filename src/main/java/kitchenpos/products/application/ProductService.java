package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.ui.dto.ProductChangePriceRequest;
import kitchenpos.products.ui.dto.ProductChangePriceResponse;
import kitchenpos.products.ui.dto.ProductCreateRequest;
import kitchenpos.products.ui.dto.ProductCreateResponse;
import kitchenpos.products.ui.dto.ProductResponse;
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
        return ProductCreateResponse.of(
                saveProduct.getId(),
                saveProduct.getName().getName(),
                saveProduct.getPrice().getPrice())
                ;
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
                sum = sum.add(
                        menuProduct.getProduct()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                                .getPrice()
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
        return ProductChangePriceResponse.of(
                product.getId(),
                product.getName().getName(),
                product.getPrice().getPrice());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(product -> ProductResponse.of(product.getId(), product.getName().getName(), product.getPrice().getPrice()))
                .collect(Collectors.toList());
    }
}
