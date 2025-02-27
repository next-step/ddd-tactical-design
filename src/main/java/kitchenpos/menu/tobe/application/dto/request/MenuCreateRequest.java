package kitchenpos.menu.tobe.application.dto.request;

import kitchenpos.common.tobe.Profanities;
import kitchenpos.menu.tobe.domain.menu.Menu;
import kitchenpos.menu.tobe.domain.menu.MenuProduct;
import kitchenpos.menu.tobe.domain.menu.MenuValidator;

import java.util.List;
import java.util.UUID;

public record MenuCreateRequest(
        String name,
        Long price,
        UUID menuGroupId,
        List<MenuProduct> menuProducts,
        boolean displayed
){};