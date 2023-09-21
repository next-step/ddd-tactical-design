package kitchenpos.menus.tobe.domain.menugroup;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class TobeMenuGroup {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private TobeMenuGroupName name;

    protected TobeMenuGroup() {
    }

    public TobeMenuGroup(UUID id, TobeMenuGroupName name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getStringName() {
        return name.getName();
    }
}
