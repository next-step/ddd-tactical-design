package kitchenpos.products.application;

import kitchenpos.menus.domain.PriceAdjustment;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Money;
import kitchenpos.products.tobe.domain.Name;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.ui.ProductVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final PriceAdjustment priceAdjustment;

    public ProductService(
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient,
            final PriceAdjustment priceAdjustment
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.priceAdjustment = priceAdjustment;
    }

    @Transactional
    public Product create(final ProductVo request) {
        final String name = request.getName();
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("no profanity allowed");
        }
        final Product product = new Product(UUID.randomUUID(), new Name(request.getName()), new Money(request.getPrice()));

        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        request.getPrice().changePrice(request.getPrice(), product);

        priceAdjustment.changeMenuPrice(productId);
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
