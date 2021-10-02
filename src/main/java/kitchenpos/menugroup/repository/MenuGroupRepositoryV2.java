package kitchenpos.menugroup.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menugroup.model.MenuGroupV2;

public interface MenuGroupRepositoryV2 {
    MenuGroupV2 save(MenuGroupV2 menuGroup);

    Optional<MenuGroupV2> findById(UUID id);

    List<MenuGroupV2> findAll();
}

