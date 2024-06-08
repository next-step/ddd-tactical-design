package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name = "menu_group")
@Entity
public class TobeMenuGroup {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    protected TobeMenuGroup() {
    }

    private TobeMenuGroup(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static TobeMenuGroup create(String name) {
        return new TobeMenuGroup(UUID.randomUUID(), name);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
