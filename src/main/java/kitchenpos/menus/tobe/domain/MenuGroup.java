package kitchenpos.menus.tobe.domain;

import org.thymeleaf.util.StringUtils;

import java.util.Objects;

/**
 * MenuGroup은 번호와 이름을 가진다.
 */
public class MenuGroup {
    private Long id;
    private String name;

    public MenuGroup(String name) {
        validName(name);

        this.name = name;
    }

    private void validName(String name) {
        if(Objects.isNull(name) || StringUtils.isEmptyOrWhitespace(name)) {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }
}
