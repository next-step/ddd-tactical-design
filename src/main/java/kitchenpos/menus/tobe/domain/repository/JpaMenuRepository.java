package kitchenpos.menus.tobe.domain.repository;

import kitchenpos.menus.tobe.domain.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface JpaMenuRepository extends JpaRepository<Menu, UUID>, MenuRepository {

}
