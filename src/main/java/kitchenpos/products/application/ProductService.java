package kitchenpos.products.application;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.dto.CreateProductRequest;
import kitchenpos.products.dto.ModifyProductPriceRequest;
import kitchenpos.products.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(ProductRepository productRepository,
                          MenuRepository menuRepository,
                          PurgomalumClient purgomalumClient) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public ProductResponse create(final CreateProductRequest request) {
        if (purgomalumClient.containsProfanity(request.getName())) {
            throw new IllegalArgumentException("상품명에 비속어가 포함되어 있습니다.");
        }

        Product saved = productRepository.save(Product.of(request.getName(), request.getPrice()));
        return ProductResponse.from(saved);
    }

    @Transactional
    public ProductResponse changePrice(UUID productId, ModifyProductPriceRequest request) {

        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다."));

        product.changePrice(request.getPrice());

        List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = menuProduct.getProduct()
                                 .getPrice()
                                 .multiply(BigDecimal.valueOf(menuProduct.getQuantity()));
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }

        return ProductResponse.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                                .stream()
                                .map(ProductResponse::from)
                                .collect(collectingAndThen(toList(), Collections::unmodifiableList));
    }
}
