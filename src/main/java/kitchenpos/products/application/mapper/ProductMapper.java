package kitchenpos.products.application.mapper;

import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.products.dto.ProductRequest;
import kitchenpos.products.dto.ProductResponse;
import kitchenpos.products.tobe.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product toEntity(ProductRequest request, ProfanityPolicy profanityPolicy) {
        return Product.of(request.getName(), request.getPrice(), profanityPolicy);
    }

    public static ProductResponse toDto(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getNameValue(),
                product.getPriceValue());
    }

    public static List<ProductResponse> toDtos(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
