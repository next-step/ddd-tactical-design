package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.dto.ProductChangePriceRequest;
import kitchenpos.products.dto.ProductCreateRequest;
import kitchenpos.products.dto.ProductDetailResponse;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDomainService productDomainService;
    private final PurgomalumClient purgomalumClient;
    private final MenuRepository menuRepository;

    public ProductService(ProductRepository productRepository, ProductDomainService productDomainService, PurgomalumClient purgomalumClient, MenuRepository menuRepository) {
        this.productRepository = productRepository;
        this.productDomainService = productDomainService;
        this.purgomalumClient = purgomalumClient;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public ProductDetailResponse create(final ProductCreateRequest request) {
        Product product = Product.create(
                ProductName.create(request.getName(), purgomalumClient),
                ProductPrice.create(request.getPrice())
        );
        return toProductDetailResponse(productRepository.save(product));
    }

    @Transactional
    public ProductDetailResponse changePrice(final UUID productId, final ProductChangePriceRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        List<Menu> menus = menuRepository.findAllByProductId(productId);
        return toProductDetailResponse(productDomainService.changePrice(product, request.getPrice(), menus));
    }

    @Transactional(readOnly = true)
    public List<ProductDetailResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::toProductDetailResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    private ProductDetailResponse toProductDetailResponse(Product product) {
        return new ProductDetailResponse(product.getId(), product.getName(), product.getPrice());
    }
}
