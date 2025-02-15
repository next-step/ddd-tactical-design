package kitchenpos.product.application.service;

import kitchenpos.menu.application.port.out.MenuRepository;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.product.application.port.out.LoadProductPort;
import kitchenpos.product.application.port.out.SaveProductPort;
import kitchenpos.product.domain.exception.ProductNameValidationException;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductNameValidator;
import kitchenpos.product.domain.model.ProductPrice;
import kitchenpos.shared.port.out.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService {
    private final LoadProductPort loadProductPort;
    private final SaveProductPort saveProductPort;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
            final LoadProductPort loadProductPort,
            final SaveProductPort saveProductPort,
            final MenuRepository menuRepository,
            final PurgomalumClient purgomalumClient
    ) {
        this.loadProductPort = loadProductPort;
        this.saveProductPort = saveProductPort;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final Product request) {
        ProductName productName = ProductName.of(request.getName(), getProductNamePurgomalumValidator());
        ProductPrice productPrice = ProductPrice.of(request.getPrice());
        final Product product = new Product(UUID.randomUUID(), productName, productPrice);
        return saveProductPort.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        final Product product = loadProductPort.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(request.getPrice());
        saveProductPort.save(product);
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
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
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return loadProductPort.findAll();
    }

    private ProductNameValidator getProductNamePurgomalumValidator() {
        return name -> {
            if (purgomalumClient.containsProfanity(name)) {
                throw new ProductNameValidationException("상품명에 비속어가 포함되어 있습니다.");
            }
        };
    }
}
