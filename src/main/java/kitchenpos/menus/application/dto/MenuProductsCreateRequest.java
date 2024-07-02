package kitchenpos.menus.application.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record MenuProductsCreateRequest(@NotEmpty List<MenuProductCreateRequest> menuProducts) {

}
