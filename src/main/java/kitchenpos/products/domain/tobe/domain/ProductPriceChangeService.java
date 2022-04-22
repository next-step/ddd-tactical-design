package kitchenpos.products.domain.tobe.domain;

import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.TobeMenuRepository;
import kitchenpos.products.dto.ProductPriceChangeRequest;
import kitchenpos.products.dto.ProductPriceChangeResponse;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class ProductPriceChangeService {
    private final TobeProductRepository productRepository;
    private final TobeMenuRepository menuRepository;

    public ProductPriceChangeService(TobeProductRepository productRepository, TobeMenuRepository menuRepository) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
    }

    public ProductPriceChangeResponse priceChange(ProductPriceChangeRequest request) {
        if (Objects.isNull(request)) {
            throw new IllegalArgumentException();
        }
        final TobeProduct product = productRepository.findById(request.getProductId()).orElseThrow(NoSuchElementException::new);
        product.changePrice(request.getPrice());
        menuRepository.findAllByProductId(product.getId())
                .stream()
                .filter(m -> m.getPrice().isGreaterThan(m.getMenuProducts().getSumOfMenuProductAmount()))
                .forEach(TobeMenu::hide);
        return new ProductPriceChangeResponse(product);
    }
}
