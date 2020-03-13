package kitchenpos.menus.tobe.v2.domain;

import org.thymeleaf.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private void validName(String name) {
        if (StringUtils.isEmptyOrWhitespace(name)) {
            throw new IllegalArgumentException();
        }
    }
}
