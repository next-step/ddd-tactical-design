package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.model.MenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface JpaMenuGroupRepository extends JpaRepository<MenuGroup, UUID>, MenuGroupRepository {

}
