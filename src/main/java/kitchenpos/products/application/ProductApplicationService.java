package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.dto.ProductCreateRequest;
import kitchenpos.products.dto.ProductPriceChangeRequest;
import kitchenpos.products.dto.ProductResponse;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.mapper.ProductMapper;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductApplicationService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductApplicationService(
            final ProductMapper productMapper,
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final PurgomalumClient purgomalumClient
    ) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public ProductResponse create(final ProductCreateRequest request) {
        Product product = Product.createProductByNameAndPrice(request.getName(), request.getPrice(), purgomalumClient);
        product = productRepository.save(product);
        return productMapper.toProductResponse(product);
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductPriceChangeRequest request) {
        Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product = Product.changePrice(request.getProductId(), request.getPrice());

        product = productRepository.save(product);

        // Menu Product에 대한 업데이트는 Menu 리팩토링할때 진행하는게 좋을듯
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                if (menuProduct.getProduct().getId().getValue().equals(productId)) {
                    sum = sum.add(
                            request.getPrice()
                            .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                    );
                } else {
                    sum = sum.add(
                            menuProduct.getProduct()
                                    .getPrice()
                                    .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                    );
                }
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
        return productMapper.toProductResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(v -> productMapper.toProductResponse(v))
                .collect(Collectors.toList());
    }
}
