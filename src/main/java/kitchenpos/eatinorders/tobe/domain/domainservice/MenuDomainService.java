package kitchenpos.eatinorders.tobe.domain.domainservice;

import java.util.Map;
import kitchenpos.eatinorders.tobe.domain.dto.MenuRes;

public interface MenuDomainService {

  Map<Long, MenuRes> findDisplayedMenus(Iterable<Long> ids);

}
