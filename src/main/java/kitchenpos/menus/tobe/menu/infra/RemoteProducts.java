package kitchenpos.menus.tobe.menu.infra;

import kitchenpos.menus.tobe.menu.domain.ProductPriceDto;
import kitchenpos.menus.tobe.menu.domain.Products;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RemoteProducts implements Products {

    private final ProductRepository productRepository;

    public RemoteProducts(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductPriceDto> getProductPricesByProductIds(final List<Long> productIds) {
        if (productIds.size() != productIds.parallelStream().distinct().count()) {
            throw new IllegalArgumentException("제품은 중복될 수 없습니다.");
        }
        final List<Product> products = productRepository.findAllById(productIds);
        return products.stream()
                .map(product -> new ProductPriceDto(product.getId(), product.getPrice()))
                .collect(Collectors.toList());
    }
}
