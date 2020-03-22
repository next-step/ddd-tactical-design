package kitchenpos.menus.tobe.menugroup.entity;

import kitchenpos.common.Name;
import kitchenpos.products.tobe.domain.Product;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MenuGroup {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private Name name;

    protected MenuGroup () {};

    public MenuGroup (Builder builder){
        this.id = builder.getId();
        this.name = builder.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name.valueOf();
    }

    public static class Builder {
        private Long id;
        private Name name;

        public Builder id (Long id){
            this.id = id;
            return this;
        }

        public Builder name (String name){
            this.name = new Name(name);
            return this;
        }

        public MenuGroup build (){
            return new MenuGroup(this);
        }

        public Long getId() {
            return id;
        }

        public Name getName() {
            return name;
        }
    }
}
