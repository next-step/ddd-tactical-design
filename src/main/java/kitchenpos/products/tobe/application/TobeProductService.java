package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.*;
import kitchenpos.products.tobe.ui.ProductForm;
import kitchenpos.tobeinfra.TobePurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TobeProductService {
    private final TobeProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final TobePurgomalumClient purgomalumClient;

    public TobeProductService(
        final TobeProductRepository productRepository,
        final MenuRepository menuRepository,
        final TobePurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public ProductForm create(final ProductForm request) {
        ProductName name = new ProductName(request.getName(), this.purgomalumClient);
        ProductPrice price = new ProductPrice(request.getPrice());
        TobeProduct saveProduct = productRepository.save(new TobeProduct(name, price));
        return ProductForm.of(saveProduct);
    }

    @Transactional
    public ProductForm changePrice(final UUID productId, final ProductForm request) {
        final TobeProduct product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);

        product.changePrice(request.getPrice());

        beforeChangeMenuDisplay(productId);

        return ProductForm.of(product);
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
    public List<ProductForm> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductForm::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductForm findById(UUID id) {
        return ProductForm.of(productRepository.findById(id)
                .orElseThrow(NoSuchElementException::new));
    }

    @Transactional(readOnly = true)
    public List<ProductForm> findAllByIdIn(List<UUID> ids) {
        return productRepository.findAllByIdIn(ids)
                .stream()
                .map(ProductForm::of)
                .collect(Collectors.toList());
    }
}
