package kitchenpos.menus.tobe.menugroup.entity;

import kitchenpos.common.Name;
import kitchenpos.products.tobe.domain.Product;

<<<<<<< HEAD
import javax.persistence.Embedded;
=======
>>>>>>> 0d1e94fb190f30830130e9e491a54a89f691ce7c
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MenuGroup {

    @Id
    @GeneratedValue
    private Long id;

<<<<<<< HEAD
    @Embedded
    private Name name;

    protected MenuGroup () {};

    public MenuGroup (Builder builder){
        this.id = builder.getId();
        this.name = builder.getName();
    }

=======
    private Name name;

>>>>>>> 0d1e94fb190f30830130e9e491a54a89f691ce7c
    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name.valueOf();
    }

<<<<<<< HEAD
=======
    protected MenuGroup () {};

    public MenuGroup (Builder builder){
        this.id = builder.getId();
        this.name = builder.getName();
    }

>>>>>>> 0d1e94fb190f30830130e9e491a54a89f691ce7c
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
