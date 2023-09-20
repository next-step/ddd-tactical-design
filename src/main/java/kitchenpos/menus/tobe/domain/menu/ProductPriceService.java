package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.shared.dto.MenuProductDto;

import java.util.List;

public interface ProductPriceService {
    void validateMenuProductDtoRequest(List<MenuProductDto> menuProductRequests);
}
