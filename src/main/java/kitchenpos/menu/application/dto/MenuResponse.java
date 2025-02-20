package kitchenpos.menu.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menu.domain.model.MenuVo;

public record MenuResponse() {
    public record GetMenu(
        UUID id,
        String name,
        BigDecimal price,
        boolean displayed
    ) {
        public static GetMenu fromVo(MenuVo.MenuInfo vo) {
            return new GetMenu(vo.id(), vo.name(), vo.price(), vo.displayed());
        }
    }
}
