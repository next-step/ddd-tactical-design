package kitchenpos.products.tobe.application;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.TobeProduct;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class TobeProductService {
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final ApplicationEventPublisher eventPublisher;


    public TobeProductService(final ProductRepository productRepository, final PurgomalumClient purgomalumClient,
                              final ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public TobeProduct create(BigDecimal price, String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("이름에는 비속어가 포함될 수 없습니다. name: " + name);
        }

        final TobeProduct product = new TobeProduct(UUID.randomUUID(), name, price);
        return productRepository.save(product);
    }

    @Transactional
    public TobeProduct changePrice(final UUID productId, BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final TobeProduct product = productRepository.findById(productId)
                                                     .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);

        eventPublisher.publishEvent(product);

        return product;
    }

    @Transactional(readOnly = true)
    public List<TobeProduct> findAll() {
        return productRepository.findAll();
    }
}
