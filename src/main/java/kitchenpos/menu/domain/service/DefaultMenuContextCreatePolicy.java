package kitchenpos.menu.domain.service;

import kitchenpos.menu.domain.model.MenuGroupNameValidator;
import kitchenpos.menu.domain.model.MenuNameValidator;
import org.springframework.stereotype.Service;

@Service
public class DefaultMenuContextCreatePolicy implements MenuCreatePolicy, MenuGroupCreatePolicy {


    @Override
    public String validateMenuName(String name, MenuPurgomalumClient purgomalumClient) {
        return new MenuNameValidator(name, purgomalumClient).name();
    }

    @Override
    public String validateGroupName(String name, MenuPurgomalumClient purgomalumClient) {
        return new MenuGroupNameValidator(name, purgomalumClient).name();
    }
}
