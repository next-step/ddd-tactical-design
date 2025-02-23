package kitchenpos.menu.application.dto;

import jakarta.validation.constraints.NotBlank;
import kitchenpos.menu.domain.model.MenuGroupVo;

public record MenuGroupRequest() {

    public record Create(
        @NotBlank(message = "메뉴그룹명은 필수입니다.")
        String name
    ) {

        public MenuGroupVo.Create toVo() {
            return new MenuGroupVo.Create(name);
        }
    }
}
