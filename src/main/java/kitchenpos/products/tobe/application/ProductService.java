package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.exception.InvalidProductException;
import kitchenpos.products.tobe.domain.vo.Profanities;
import kitchenpos.products.tobe.domain.event.ProductPriceChangedEvent;
import kitchenpos.products.tobe.ui.dto.ChangeProductRequest;
import kitchenpos.products.tobe.ui.dto.ChangeProductResponse;
import kitchenpos.products.tobe.ui.dto.CreateProductRequest;
import kitchenpos.products.tobe.ui.dto.CreateProductResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.vo.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.ui.dto.FindProductResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final Profanities profanities;
    private final ApplicationEventPublisher eventPublisher;

    public ProductService(final ProductRepository productRepository,
                          final Profanities profanities,
                          final ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.profanities = profanities;
        this.eventPublisher = eventPublisher;
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

        // 트랜잭션이 성공적으로 커밋된 후 ProductPriceChangedEvent 이벤트 리스너가 실행됨
        eventPublisher.publishEvent(new ProductPriceChangedEvent(productId, price.getPrice()));
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
