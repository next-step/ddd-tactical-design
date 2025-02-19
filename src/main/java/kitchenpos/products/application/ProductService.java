package kitchenpos.products.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.application.dto.ChangeProductPriceRequestDto;
import kitchenpos.products.application.dto.ChangeProductPriceResponseDto;
import kitchenpos.products.application.dto.CreateProductRequestDto;
import kitchenpos.products.application.dto.CreateProductResponseDto;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.model.DisplayedName;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.model.ProductPrice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CreateProductResponseDto create(final CreateProductRequestDto request) {
        final DisplayedName displayedName = new DisplayedName(request.getName(), purgomalumClient);
        final ProductPrice productPrice = new ProductPrice(request.getPrice());
        final Product product = new Product(UUID.randomUUID(), displayedName, productPrice);
        return CreateProductResponseDto.from(productRepository.save(product));
    }

    @Transactional
    public ChangeProductPriceResponseDto changePrice(final UUID productId,
        final ChangeProductPriceRequestDto request) {
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(request.toValueObject());

        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                    menuProduct.getProduct()
                        .getPrice().getValue()
                        .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
        return ChangeProductPriceResponseDto.from(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
