package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.domain.ProfanitiesChecker;
import kitchenpos.products.tobe.dto.ChangePriceRequest;
import kitchenpos.products.tobe.dto.CreateProductRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class ProductService {
    private final ProductRepository productRepository;
    private final ProfanitiesChecker profanitiesChecker;

    public ProductService(ProductRepository productRepository, ProfanitiesChecker profanitiesChecker) {
        this.productRepository = productRepository;
        this.profanitiesChecker = profanitiesChecker;
    }

    @Transactional
    public Product create(final CreateProductRequest request) {
        Product product = new Product(request.name(), profanitiesChecker, request.price());
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ChangePriceRequest request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(new Price(request.price()));
        //todo 상품이 포함된 메뉴에 대한 가격 처리도 해줘야 한다.
        /*
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
        */
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
