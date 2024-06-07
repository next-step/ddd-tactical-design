package kitchenpos.menu.tobe.infra;

import kitchenpos.menu.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository("newMenuGroupRepository")
public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
