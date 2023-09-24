package kitchenpos.menus.ui.dto;

import kitchenpos.menus.application.dto.*;
import kitchenpos.menus.ui.dto.request.MenuChangePriceRestRequest;
import kitchenpos.menus.ui.dto.request.MenuCreateRestRequest;
import kitchenpos.menus.ui.dto.request.MenuProductRestRequest;
import kitchenpos.menus.ui.dto.response.MenuProductRestResponse;
import kitchenpos.menus.ui.dto.response.MenuRestResponse;

import java.util.List;
import java.util.stream.Collectors;

public class MenuRestMapper {

    public static MenuChangePriceRequest toDto(MenuChangePriceRestRequest restRequest) {
        return new MenuChangePriceRequest(
                restRequest.getPrice()
        );
    }

    public static MenuCreateRequest toDto(MenuCreateRestRequest restRequest) {
        return new MenuCreateRequest(
                restRequest.getPrice(),
                restRequest.getMenuGroupId(),
                toDtos(restRequest.getMenuProducts()),
                restRequest.getName(),
                restRequest.isDisplayed()
        );
    }

    public static MenuProductRequest toDto(MenuProductRestRequest restRequest) {
        return new MenuProductRequest(
                restRequest.getProductId(),
                restRequest.getQuantity()
        );
    }

    private static List<MenuProductRequest> toDtos(List<MenuProductRestRequest> restRequests) {
        return restRequests.stream()
                .map(MenuRestMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public static MenuProductRestResponse toRestDto(MenuProductResponse response) {
        return new MenuProductRestResponse(
                response.getSeq(),
                response.getProductId(),
                response.getQuantity()
        );
    }

    private static List<MenuProductRestResponse> toRestMenuProuctDtos(List<MenuProductResponse> responses) {
        return responses.stream()
                .map(MenuRestMapper::toRestDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public static MenuRestResponse toRestDto(MenuResponse response) {
        return new MenuRestResponse(
                response.getId(),
                response.getName(),
                response.getPrice(),
                response.getMenuGroupId(),
                response.isDisplayed(),
                toRestMenuProuctDtos(response.getMenuProducts())
        );
    }

    public static List<MenuRestResponse> toRestDtos(List<MenuResponse> responses) {
        return responses.stream()
                .map(MenuRestMapper::toRestDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
