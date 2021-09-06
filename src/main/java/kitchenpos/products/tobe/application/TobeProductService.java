package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.*;
import kitchenpos.products.tobe.ui.ProductForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TobeProductService {
    private final TobeProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final TobeProductValidation productValidation;

    public TobeProductService(
        final TobeProductRepository productRepository,
        final MenuRepository menuRepository,
        final TobeProductValidation productValidation
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.productValidation = productValidation;
    }

    @Transactional
    public TobeProduct create(final ProductForm request) {
        ProductName name = new ProductName(request.getName());
        productValidation.vaildationName(name);
        ProductPrice price = new ProductPrice(request.getPrice());
        return productRepository.save(new TobeProduct(name, price));
    }

    @Transactional
    public TobeProduct changePrice(final UUID productId, final ProductForm request) {
        final TobeProduct product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);

        product.changePrice(request.getPrice());

        beforeChangeMenuDisplay(productId);

        return product;
    }

    // TODO : Menu 리팩토링 시 수정해야함.
    private void beforeChangeMenuDisplay(UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
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
    }

    @Transactional(readOnly = true)
    public List<TobeProduct> findAll() {
        return productRepository.findAll();
    }
}
