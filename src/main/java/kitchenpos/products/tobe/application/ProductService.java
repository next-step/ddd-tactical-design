package kitchenpos.products.tobe.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.tobe.menu.domain.model.Menu;
import kitchenpos.menus.tobe.menu.domain.repository.MenuRepository;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.model.ProductPrice;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.dto.ProductRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("TobeProductService")
public class ProductService {

    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;

    public ProductService(
        final ProductRepository productRepository,
        final MenuRepository menuRepository
        ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public Product create(final ProductRequestDto productRequestDto) {
        final Product product = productRequestDto.toEntity();
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductPrice price) {
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);

        final Product changedProduct = product.changePrice(price);

        //TODO menu display 판단 로직 개선
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            menu.isNotValidPrice();
        }

        return changedProduct;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
