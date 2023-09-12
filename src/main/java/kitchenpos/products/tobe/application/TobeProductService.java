package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.PurgomalumChecker;
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
    private final PurgomalumChecker purgomalumChecker;
    private final ApplicationEventPublisher eventPublisher;

    public TobeProductService(final TobeProductRepository tobeProductRepository, final PurgomalumChecker purgomalumChecker,
                              final ApplicationEventPublisher eventPublisher) {
        this.tobeProductRepository = tobeProductRepository;
        this.purgomalumChecker = purgomalumChecker;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public TobeProduct create(BigDecimal price, String name) {
        final TobeProduct product = new TobeProduct(UUID.randomUUID(),
                                                    new ProductName(name, purgomalumChecker),
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
