package kitchenpos.products.tobe.application;


import kitchenpos.infra.PurgomalumClient;
import kitchenpos.products.tobe.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ProductService {

    private final ProductRepository productRepository;
    private final MenuDisplayService menuDisplayService;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
            final ProductRepository productRepository,
            final MenuDisplayService menuDisplayService,
            final PurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.menuDisplayService = menuDisplayService;
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

        menuDisplayService.display(productId, new ProductPrices(List.of(new ProductPrice(productId, price))));

        return product;
    }

}
