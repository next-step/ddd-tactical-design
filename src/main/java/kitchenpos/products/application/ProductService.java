package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.shared.dto.request.ProductChangePriceRequest;
import kitchenpos.products.shared.dto.request.ProductCreateRequest;
import kitchenpos.products.shared.dto.response.ProductDto;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.common.domain.PurgomalumClient;
import kitchenpos.shared.util.ConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
            final ProductRepository productRepository,
            final MenuRepository menuRepository,
            final PurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public ProductDto create(final ProductCreateRequest productCreateRequest) {
        Product product = Product.of(
                new ProductName(productCreateRequest.getName(), purgomalumClient),
                new ProductPrice(productCreateRequest.getPrice())
        );
        return ConvertUtil.convert(productRepository.save(product), ProductDto.class);
    }

    @Transactional
    public ProductDto changePrice(final UUID productId, final ProductChangePriceRequest productChangePriceRequest) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changeProductPrice(productChangePriceRequest.getPrice());
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        validateMenuPrice(menus);
        return ConvertUtil.convert(product, ProductDto.class);
    }

    private static void validateMenuPrice(List<Menu> menus) {
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                        menuProduct.getProduct()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        return ConvertUtil.convertList(productRepository.findAll(), ProductDto.class);
    }
}
