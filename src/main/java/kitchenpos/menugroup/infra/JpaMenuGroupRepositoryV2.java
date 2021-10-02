package kitchenpos.menugroup.infra;

import java.util.UUID;
import kitchenpos.menugroup.model.MenuGroupV2;
import kitchenpos.menugroup.repository.MenuGroupRepositoryV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMenuGroupRepositoryV2 extends MenuGroupRepositoryV2, JpaRepository<MenuGroupV2, UUID> {
}
