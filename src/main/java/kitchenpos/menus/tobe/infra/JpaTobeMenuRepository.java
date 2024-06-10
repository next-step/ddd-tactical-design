package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.TobeMenuRepository;
import kitchenpos.menus.tobe.domain.menu.TobeMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTobeMenuRepository extends TobeMenuRepository, JpaRepository<TobeMenu, UUID> {
}
