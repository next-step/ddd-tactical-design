package kitchenpos.eatinorders.infra;

import kitchenpos.eatinorders.domain.EatInMenu;
import kitchenpos.eatinorders.domain.EatInMenuRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInMenuRepository extends EatInMenuRepository, JpaRepository<EatInMenu, UUID> {
}
