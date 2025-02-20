package kitchenpos.menu.application.dto;

import kitchenpos.menu.domain.model.MenuGroupVo;

public record MenuGroupRequest() {

    public record Create(
        String name
    ) {

        public MenuGroupVo.Create toVo() {
            return new MenuGroupVo.Create(name);
        }
    }
}
