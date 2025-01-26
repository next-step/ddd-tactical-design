package kitchenpos.menus.tobe.domain;

import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class MenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    public static MenuGroup newOne(String name) {
        Assert.isTrue(!Objects.isNull(name) && !name.isEmpty(), name);
        return new MenuGroup(UUID.randomUUID(), name);
    }

    public MenuGroup() {
    }

    public MenuGroup(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
