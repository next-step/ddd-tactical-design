package kitchenpos.menus.tobe.menu.domain;

import kitchenpos.menus.tobe.menu.application.dto.ProductQuantityDto;

import java.util.List;

public interface Products {
    List<MenuProduct> getMenuProductsByProductIdsAndQuantities(List<ProductQuantityDto> productQuantityDtos);
}
