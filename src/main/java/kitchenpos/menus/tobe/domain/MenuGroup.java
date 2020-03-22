package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.util.StringUtils;

@Entity
public class MenuGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public MenuGroup() {
    }

    public MenuGroup(Long id, String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.name = name;
    }

    public MenuGroup(String name) {
        this(null, name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuGroup)) {
            return false;
        }
        MenuGroup menuGroup = (MenuGroup) o;
        return Objects.equals(id, menuGroup.id) &&
            Objects.equals(name, menuGroup.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
