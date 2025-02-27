package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.application.exception.InvalidProductServiceException;
import kitchenpos.products.ui.dto.ChangeProductRequest;
import kitchenpos.products.ui.dto.ChangeProductResponse;
import kitchenpos.products.ui.dto.CreateProductRequest;
import kitchenpos.products.ui.dto.CreateProductResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.vo.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.common.infra.PurgomalumClient;
import kitchenpos.products.ui.dto.FindProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

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
    public CreateProductResponse create(final CreateProductRequest request) {
        validateProfanity(request.name());
        final Product product = new Product(request.name(), request.price());

        return CreateProductResponse.from(productRepository.save(product));
    }

    private void validateProfanity(final String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new InvalidProductServiceException("상품의 이름에 부적절한 단어(비속어가) 포함되면 안됩니다.");
        }
    }

    @Transactional
    public ChangeProductResponse changePrice(final UUID productId, final ChangeProductRequest request) {
        final ProductPrice price = new ProductPrice(request.price());

        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new InvalidProductServiceException("해당 상품이 존재하지 않습니다"));
        product.updatePrice(price.getPrice());

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
        return ChangeProductResponse.from(product);
    }

    @Transactional(readOnly = true)
    public List<FindProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> FindProductResponse.from(product))
                .toList();
    }
}
