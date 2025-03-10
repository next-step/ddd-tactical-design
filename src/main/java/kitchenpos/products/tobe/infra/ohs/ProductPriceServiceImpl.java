package kitchenpos.products.tobe.infra.ohs;

import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.annotations.OpenHostService;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.TobeProductRepository;
import kitchenpos.shared.domain.ProductPrice;

@OpenHostService(
    description = "상품 가격을 제공하는 서비스",
    downstreamContexts = {"MenusContext"}
)
public class ProductPriceServiceImpl {

    private final TobeProductRepository productRepository;

    public ProductPriceServiceImpl(TobeProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductPrice getPriceByProductId(UUID productId) {
        final Product product = productRepository.findById(productId).orElseThrow(
            () -> new NoSuchElementException("Product not found with id: " + productId));
        return new ProductPrice(product.getPrice());
    }
}
