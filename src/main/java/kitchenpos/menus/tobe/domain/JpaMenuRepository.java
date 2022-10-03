package kitchenpos.menus.tobe.domain;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMenuRepository extends MenuRepository, JpaRepository<Menu, UUID> {

}
