package kitchenpos.menu.domain.service;

import java.math.BigDecimal;
import java.util.Objects;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.menu.domain.model.MenuGroupNameValidator;
import kitchenpos.menu.domain.model.MenuNameValidator;
import org.springframework.stereotype.Service;

@Service
public class DefaultMenuContextCreatePolicy implements MenuCreatePolicy, MenuGroupCreatePolicy {


    @Override
    public BigDecimal validatePrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(ErrorCode.MENU_PRICE_NOT_ALLOWED.toString());
        }
        return price;
    }

    @Override
    public String validateMenuName(String name, MenuPurgomalumClient purgomalumClient) {
        return new MenuNameValidator(name, purgomalumClient).name();
    }

    @Override
    public String validateGroupName(String name, MenuPurgomalumClient purgomalumClient) {
        return new MenuGroupNameValidator(name, purgomalumClient).name();
    }
}
