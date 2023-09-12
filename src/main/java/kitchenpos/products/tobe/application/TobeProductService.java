package kitchenpos.products.tobe.application;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.TobeProduct;
import kitchenpos.products.tobe.domain.TobeProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class TobeProductService {
    private final TobeProductRepository tobeProductRepository;
    private final PurgomalumClient purgomalumClient;
    private final ApplicationEventPublisher eventPublisher;

    public TobeProductService(final TobeProductRepository tobeProductRepository, final PurgomalumClient purgomalumClient,
                              final ApplicationEventPublisher eventPublisher) {
        this.tobeProductRepository = tobeProductRepository;
        this.purgomalumClient = purgomalumClient;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public TobeProduct create(BigDecimal price, String name) {
        final TobeProduct product = new TobeProduct(UUID.randomUUID(),
                                                    new ProductName(name, purgomalumClient),
                                                    new ProductPrice(price));
        return tobeProductRepository.save(product);
    }

    @Transactional
    public TobeProduct changePrice(final UUID productId, BigDecimal price) {
        final TobeProduct product = tobeProductRepository.findById(productId)
                                                         .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);

        eventPublisher.publishEvent(product);

        return product;
    }

    @Transactional(readOnly = true)
    public List<TobeProduct> findAll() {
        return tobeProductRepository.findAll();
    }
}
