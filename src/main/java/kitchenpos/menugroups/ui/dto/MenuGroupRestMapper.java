package kitchenpos.menugroups.ui.dto;

import kitchenpos.menugroups.application.dto.MenuGroupCreateRequest;
import kitchenpos.menugroups.application.dto.MenuGroupResponse;
import kitchenpos.menugroups.ui.dto.request.MenuGroupCreateRestRequest;
import kitchenpos.menugroups.ui.dto.response.MenuGroupRestResponse;

import java.util.List;
import java.util.stream.Collectors;

public class MenuGroupRestMapper {

    public static MenuGroupCreateRequest toDto(MenuGroupCreateRestRequest restRequest) {
        return new MenuGroupCreateRequest(restRequest.getName());
    }

    public static MenuGroupRestResponse toRestDto(MenuGroupResponse response) {
        return new MenuGroupRestResponse(
                response.getId(),
                response.getName()
        );
    }

    public static List<MenuGroupRestResponse> toRestDtos(List<MenuGroupResponse> responses) {
        return responses.stream()
                .map(MenuGroupRestMapper::toRestDto)
                .collect(Collectors.toUnmodifiableList());
    }


}
