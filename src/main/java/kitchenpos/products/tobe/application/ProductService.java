package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.exception.InvalidProductException;
import kitchenpos.products.tobe.domain.vo.Profanities;
import kitchenpos.products.tobe.event.ProductPriceChangedEvent;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final Profanities profanities;
    private final ApplicationEventPublisher eventPublisher;

    public ProductService(final ProductRepository productRepository,
                          final MenuRepository menuRepository,
                          final Profanities profanities,
                          final ApplicationEventPublisher eventPublisher) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
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
