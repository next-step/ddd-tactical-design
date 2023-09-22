package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.common.infra.PurgomalumClient;
import kitchenpos.products.tobe.dto.ProductChangePriceRequest;
import kitchenpos.products.tobe.dto.ProductChangePriceResponse;
import kitchenpos.products.tobe.dto.ProductCreateRequest;
import kitchenpos.products.tobe.dto.ProductCreateResponse;
import kitchenpos.products.tobe.dto.ProductResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ProductService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final PurgomalumClient purgomalumClient,
            ApplicationEventPublisher applicationEventPublisher) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public ProductCreateResponse create(final ProductCreateRequest request) {
        final Product product = new Product(new DisplayedName(request.getName(), purgomalumClient), request.getPrice());
        productRepository.save(product);

        return ProductCreateResponse.of(product);
    }

    @Transactional
    public ProductChangePriceResponse changePrice(final UUID productId, final ProductChangePriceRequest request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(request.getPrice());

        final List<Menu> menus = menuRepository.findAllByProductId(productId);
//        menus.forEach(menu -> menu.changePrice(request.getPrice()));
        menus.forEach(menu -> applicationEventPublisher.publishEvent(new MenuPriceChangeEvent(menu.getId(), request.getPrice())));

        return ProductChangePriceResponse.of(
                product.getId(),
                product.getName().getValue(),
                product.getPrice().getValue());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(product -> ProductResponse.of(product.getId(), product.getName().getValue(), product.getPrice().getValue()))
                .collect(Collectors.toList());
    }
}