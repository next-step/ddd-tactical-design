package kitchenpos.menus.tobe.domain.menugroup.entity;

import kitchenpos.common.Name;

import javax.persistence.*;

@Entity
public class MenuGroup {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverride(
        name = "name",
        column = @Column(name = "name")
    )
    private Name name;

    protected MenuGroup () {};

    public MenuGroup (Long id, String name){
        this.id = id;
        this.name = new Name(name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name.valueOf();
    }
}
