package kitchenpos.menus.tobe.domain;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewJpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<NewMenuGroup, UUID> {
}
