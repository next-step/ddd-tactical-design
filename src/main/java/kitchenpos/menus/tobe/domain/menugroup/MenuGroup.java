package kitchenpos.menus.tobe.domain.menugroup;

import org.thymeleaf.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * MenuGroup은 번호와 이름을 가진다.
 */
@Entity
public class MenuGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public MenuGroup() {
    }

    public MenuGroup(String name) {
        validName(name);

        this.name = name;
    }

    private void validName(String name) {
        if(Objects.isNull(name) || StringUtils.isEmptyOrWhitespace(name)) {
            throw new IllegalArgumentException();
        }
    }

    public String menuName() {
        return name;
    }
}
