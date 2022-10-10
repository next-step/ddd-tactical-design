package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.model.MenuGroup;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("Tobe_MenuGroupRepository")
public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
