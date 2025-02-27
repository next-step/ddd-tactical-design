package kitchenpos.menu.infra.persistence;

import java.util.UUID;
import kitchenpos.menu.domain.model.MenuSummary;
import kitchenpos.menu.domain.repository.MenuSummaryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaMenuSummaryRepository extends JpaRepository<MenuSummary, UUID>, MenuSummaryRepository {
}
