package kitchenpos.menus.domain.tobe.domain;

import kitchenpos.menus.domain.tobe.domain.vo.MenuGroupId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TobeJpaMenuGroupRepository extends TobeMenuGroupRepository, JpaRepository<TobeMenuGroup, MenuGroupId> {
}
