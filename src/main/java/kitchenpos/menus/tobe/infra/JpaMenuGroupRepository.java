package kitchenpos.menus.tobe.infra;

import java.util.UUID;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMenuGroupRepository
    extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {}
