package kitchenpos.menus.tobe.domain.menugroup;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, Long> {

}
