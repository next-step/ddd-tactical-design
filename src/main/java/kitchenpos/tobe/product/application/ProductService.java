package kitchenpos.tobe.product.application;

import kitchenpos.tobe.product.application.dto.*;
import kitchenpos.tobe.product.application.exception.ProductNotFoundException;
import kitchenpos.tobe.product.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    //    private final MenuRepository menuRepository; // TODO: 리팩토링 필요
    private final ProductNameValidator productNameValidator;

    public ProductService(ProductRepository productRepository, /*MenuRepository menuRepository,*/ ProductNameValidator productNameValidator) {
        this.productRepository = productRepository;
//        this.menuRepository = menuRepository;
        this.productNameValidator = productNameValidator;
    }

    @Transactional
    public CreateProductResponse create(final CreateProductRequest request) {
        productNameValidator.validate(request.name());
        final Product product = new Product(new ProductName(request.name()), ProductPrice.of(request.price()));
        final Product savedProduct = productRepository.save(product);
        return CreateProductResponse.from(savedProduct);
    }

    @Transactional
    public ChangeProductPriceResponse changePrice(final UUID productId, final ChangeProductPriceRequest request) {
        final Product product = productRepository.findById(ProductId.of(productId))
                .orElseThrow(() -> new ProductNotFoundException(ProductId.of(productId)));
        product.changePrice(ProductPrice.of(request.price()));
        // TODO 메뉴 리팩토링
        /*final List<Menu> menus = menuRepository.findAllByProductId(productId);
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
        }*/
        return ChangeProductPriceResponse.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductListResponse> findAll() {
        return productRepository.findAll().stream()
                .map(ProductListResponse::from)
                .toList();
    }

}
