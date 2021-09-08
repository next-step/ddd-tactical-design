package kitchenpos.products.tobe.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.model.Price;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.dto.ProductRequestDto;
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
    public Product create(final ProductRequestDto productRequestDto) {
        final Product product = productRequestDto.toEntity();
        product.validateProfanity(purgomalumClient);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final Price price) {
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);

        final Product changedProduct = product.changePrice(price);

        //TODO menu display 판단 로직 개선
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(menuProduct.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(menuProduct.getQuantity())));
            }
            if (menu.getPrice()
                .compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }

        return changedProduct;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
