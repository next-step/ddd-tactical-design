package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.TobeMenuGroupRepository;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTobeMenuGroupRepository extends TobeMenuGroupRepository, JpaRepository<TobeMenuGroup, UUID> {
}
