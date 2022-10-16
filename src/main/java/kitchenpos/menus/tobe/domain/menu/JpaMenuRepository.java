package kitchenpos.menus.tobe.domain.menu;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, UUID> {

}
