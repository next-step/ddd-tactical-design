package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.exception.InvalidProductException;
import kitchenpos.products.tobe.domain.vo.Profanities;
import kitchenpos.products.tobe.ui.dto.ChangeProductRequest;
import kitchenpos.products.tobe.ui.dto.ChangeProductResponse;
import kitchenpos.products.tobe.ui.dto.CreateProductRequest;
import kitchenpos.products.tobe.ui.dto.CreateProductResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.vo.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.ui.dto.FindProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final Profanities profanities;

    public ProductService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final Profanities profanities
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.profanities = profanities;
    }

    @Transactional
    public CreateProductResponse create(final CreateProductRequest request) {
        final Product product = new Product(request.name(), request.price(), profanities);
        return CreateProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public ChangeProductResponse changePrice(final UUID productId, final ChangeProductRequest request) {
        final ProductPrice price = new ProductPrice(request.price());

        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new InvalidProductException("해당 상품이 존재하지 않습니다"));
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
