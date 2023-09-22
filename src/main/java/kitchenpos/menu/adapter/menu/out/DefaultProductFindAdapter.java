package kitchenpos.menu.adapter.menu.out;

import static kitchenpos.support.ParameterValidateUtils.checkNotEmpty;
import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menu.application.menu.port.out.Product;
import kitchenpos.menu.application.menu.port.out.ProductFindPort;
import kitchenpos.menu.application.menu.port.out.Products;
import kitchenpos.product.application.port.in.ProductFindUseCase;

public class DefaultProductFindAdapter implements ProductFindPort {

    private final ProductFindUseCase productFindUseCase;

    public DefaultProductFindAdapter(
        final ProductFindUseCase productFindUseCase) {

        this.productFindUseCase = productFindUseCase;
    }

    @Override
    public Products find(final List<UUID> productIds) {
        checkNotEmpty(productIds, "productIds");

        return new Products(productFindUseCase.findByIds(productIds)
            .stream()
            .map(product -> new Product(product.getId(), product.getPrice().getValue()))
            .collect(Collectors.toUnmodifiableList()));

    }


    @Override
    public Optional<Product> find(final UUID productId) {
        checkNotNull(productId, "productId");

        return productFindUseCase.findById(productId)
            .map(product -> new Product(product.getId(), product.getPrice().getValue()));
    }
}
