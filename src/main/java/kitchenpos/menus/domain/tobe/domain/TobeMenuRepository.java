package kitchenpos.menus.domain.tobe.domain;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.tobe.domain.vo.MenuId;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TobeMenuRepository {
    TobeMenu save(TobeMenu menu);

    Optional<TobeMenu> findById(MenuId id);

    List<TobeMenu> findAll();

    List<TobeMenu> findAllByIdIn(List<MenuId> ids);

    List<TobeMenu> findAllByProductId(ProductId productId);
}

