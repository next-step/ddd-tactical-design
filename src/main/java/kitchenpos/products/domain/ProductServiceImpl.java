package kitchenpos.products.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.dtos.ProductCommand;
import kitchenpos.products.domain.dtos.ProductInfo;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductServiceImpl(ProductRepository productRepository,
            MenuRepository menuRepository, PurgomalumClient purgomalumClient) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public ProductInfo create(ProductCommand.RegisterProductCommand command) {
        ProductPrice price = new ProductPrice(command.getPrice());
        ProductName name = new ProductName(command.getName(), purgomalumClient);

        Product product = productRepository.save(new Product(price, name));

        return ProductInfo.from(product);
    }

    @Override
    public ProductInfo changePrice(UUID productId, ProductCommand.ChangePriceCommand command) {
        Product product = productRepository.findById(productId)
                .orElseThrow(IllegalArgumentException::new);

        product.changePrice(command.getPrice());

        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = menuProduct.getProduct()
                        .getPrice()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(menuProduct.getQuantity()));
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
        return ProductInfo.from(product);
    }

    @Override
    public List<ProductInfo> findAll() {
        return productRepository.findAll().stream()
                .map(ProductInfo::from)
                .collect(Collectors.toList());
    }
}
