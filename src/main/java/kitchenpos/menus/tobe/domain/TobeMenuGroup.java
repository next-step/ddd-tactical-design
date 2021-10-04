package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class TobeMenuGroup {

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuGroupName name;

    protected TobeMenuGroup() {
    }

    public TobeMenuGroup(MenuGroupName name) {
        this.id = UUID.randomUUID();
        this.name = name;
    }

    public TobeMenuGroup(UUID id, MenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    @Override
    public String toString() {
        return "TobeMenuGroup{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
