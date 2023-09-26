package kitchenpos.menus.domain;

import java.util.List;
import java.util.UUID;

import kitchenpos.menus.domain.vo.ProductVo;

public interface ProductApiRepository {
    List<ProductVo> findAllByIdIn(List<UUID> collect);
}
