package kitchenpos.menus.application.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import kitchenpos.menus.domain.tobe.ProductQuantity;

public record MenuProductCreateRequest(@NotNull UUID productId,
                                       @NotNull ProductQuantity quantity) {

}
