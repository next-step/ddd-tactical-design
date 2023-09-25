package kitchenpos.eatinorders.tobe.domain;


import java.util.List;
import java.util.UUID;


public interface EatInMenuRepository {
    EatInMenu save(EatInMenu newMenu);

    List<EatInMenu> findAllByIdIn(List<UUID> ids);
}

