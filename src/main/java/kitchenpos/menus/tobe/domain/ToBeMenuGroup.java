package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class ToBeMenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    @Embedded
    private MenuGroupName name;

    protected ToBeMenuGroup() {
    }

    public ToBeMenuGroup(String name) {
        this.id = UUID.randomUUID();
        this.name = new MenuGroupName(name);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getMenuName();
    }

}
