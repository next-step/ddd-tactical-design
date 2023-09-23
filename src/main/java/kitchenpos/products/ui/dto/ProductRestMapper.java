package kitchenpos.products.ui.dto;

import kitchenpos.products.application.dto.ProductRequest;
import kitchenpos.products.application.dto.ProductResponse;
import kitchenpos.products.ui.dto.request.ProductRestRequest;
import kitchenpos.products.ui.dto.response.ProductRestResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ProductRestMapper {

    public static ProductRequest tDto(ProductRestRequest restRequest) {
        return new ProductRequest(
                restRequest.getName(),
                restRequest.getPrice()
        );
    }

    public static ProductRestResponse toRestDto(ProductResponse response) {
        return new ProductRestResponse(
                response.getId(),
                response.getName(),
                response.getPrice());
    }

    public static List<ProductRestResponse> toRestDtos(List<ProductResponse> responses) {
        return responses.stream()
                .map(ProductRestMapper::toRestDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
