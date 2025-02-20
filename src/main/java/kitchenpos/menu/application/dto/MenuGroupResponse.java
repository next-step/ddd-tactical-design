package kitchenpos.menu.application.dto;

import java.util.UUID;
import kitchenpos.menu.domain.model.MenuGroupVo;

public record MenuGroupResponse() {
    public record GetGroup(
        UUID id,
        String name
    ) {
        public static GetGroup fromVo(MenuGroupVo.GroupInfo vo) {
            return new GetGroup(vo.id(), vo.name());
        }
    }
}
