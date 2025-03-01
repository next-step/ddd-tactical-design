package kitchenpos.product.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.global.event.ProductEvent.ProductPriceChangedEvent;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.event.ProductEventPublisher;
import kitchenpos.product.domain.model.ProductId;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductPrice;
import kitchenpos.product.domain.model.ProductVo;
import kitchenpos.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DefaultProductService implements ProductQueryService, ProductCommandService {

    private final ProductRepository productRepository;
    private final ProductPurgomalumClient purgomalumClient;
    private final ProductEventPublisher productEventPublisher;

    public DefaultProductService(
        final ProductRepository productRepository,
        final ProductPurgomalumClient purgomalumClient,
        final ProductEventPublisher defaultProductEventPublisher
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.productEventPublisher = defaultProductEventPublisher;
    }

    @Override
    public ProductVo.ProductInfo create(final ProductVo.Create request) {
        final ProductPrice price = request.price();
        final ProductName name = ProductName.of(request.name(), purgomalumClient);

        return ProductVo.ProductInfo.fromEntity(
            productRepository.save(new Product(ProductId.of(UUID.randomUUID()), name, price))
        );
    }

    @Override
    public ProductVo.ProductInfo changePrice(final ProductVo.Update request) {
        final ProductId productId = request.productId();
        final ProductPrice price = request.price();
        final Product product = productRepository.findByProductId(productId)
                                        .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_PRODUCT.toString()));

        product.updatePrice(price);

        productEventPublisher.publish(new ProductPriceChangedEvent(request.productId()));

        return ProductVo.ProductInfo.fromEntity(product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductVo.ProductInfo> findAll() {
        return productRepository.findAll()
            .stream()
            .map(ProductVo.ProductInfo::fromEntity)
            .toList();
    }
}
