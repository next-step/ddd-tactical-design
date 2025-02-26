package kitchenpos.menu.domain.repository;

import java.util.List;
import kitchenpos.menu.domain.model.MenuSummary;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuQueryRepository {

    List<MenuSummary> findAll();
}
