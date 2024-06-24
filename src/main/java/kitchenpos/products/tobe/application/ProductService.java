package kitchenpos.products.tobe.application;


import kitchenpos.infra.PurgomalumClient;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

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
    public Product create(final CreateCommand createCommand) {
        final var price = new Money(createCommand.price());
        final var name = new Name(createCommand.name(), purgomalumClient);
        return productRepository.save(new Product(UUID.randomUUID(), name, price));
    }

    @Transactional
    public Product changePrice(final UUID productId, final UpdateCommand updateCommand) {

        final BigDecimal price = updateCommand.price();
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(new Money(price));

        // TODO: 메뉴 BC 리팩토링때 반영
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
