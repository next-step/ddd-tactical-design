package kitchenpos.menus.tobe.application.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kitchenpos.menus.tobe.application.query.result.MenuQueryResult;
import kitchenpos.menus.tobe.ui.view.MenuProductViewModel;
import kitchenpos.menus.tobe.ui.view.MenuViewModel;
import org.springframework.stereotype.Component;

@Component
public class MenuConverter {
    private MenuConverter() {
    }

    public List<MenuViewModel> convert(List<MenuQueryResult> menuQueryResults) {
        Map<String, MenuViewModel> menuViewModelHashMap = new HashMap<>();
        for (MenuQueryResult r : menuQueryResults) {
            if (menuViewModelHashMap.containsKey(r.getMenuId())) {
                MenuViewModel dto = menuViewModelHashMap.get(r.getMenuId());
                dto.addMenuProductViewModel(MenuProductViewModel.from(r));
                menuViewModelHashMap.put(r.getMenuId(), dto);
            } else {
                MenuViewModel dto = MenuViewModel.from(r);
                menuViewModelHashMap.put(r.getMenuId(), dto);
            }
        }
        return new ArrayList<>(menuViewModelHashMap.values());
    }
}
