package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.ProductInfos;

import java.util.List;
import java.util.UUID;

public interface ProductClient {
    ProductInfos listByIds(List<UUID> productIds);
}
