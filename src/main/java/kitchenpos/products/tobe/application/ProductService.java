package kitchenpos.products.tobe.application;

import kitchenpos.global.exception.custom.NotFoundProductException;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.application.dto.ProductEvent;
import kitchenpos.products.tobe.application.dto.ProductInfo;
import kitchenpos.products.tobe.domain.*;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.ui.request.ProductChangeRequest;
import kitchenpos.products.ui.request.ProductCreateRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;

    private final ApplicationEventPublisher applicationEventPublisher;

    public ProductService(
        final ProductRepository productRepository,
        final PurgomalumClient purgomalumClient,
        final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public ProductInfo create(final ProductCreateRequest request) {
        ProductValidator productValidator = new ProductValidator(request.getName(), request.getPrice(), purgomalumClient);

        final Product product = productRepository.save(new Product(
                UUID.randomUUID(),
                new ProductName(productValidator.getNameValidator().getName()),
                new ProductPrice(productValidator.getPriceValidator().getPrice())
        ));

        return new ProductInfo(
                product.getId(),
                product.getName().getProductName(),
                product.getPrice().getProductPrice()
        );
    }

    public ProductInfo changePrice(final UUID productId, final ProductChangeRequest request) {
        final Product product = productRepository.findById(productId)
            .orElseThrow(NotFoundProductException::new);
        PriceValidator priceValidator = new PriceValidator(request.getPrice());
        product.changePrice(new ProductPrice(priceValidator.getPrice()));

        applicationEventPublisher.publishEvent(new ProductEvent(productId));

        return new ProductInfo(
                product.getId(),
                product.getName().getProductName(),
                product.getPrice().getProductPrice()
        );
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
