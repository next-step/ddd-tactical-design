package kitchenpos.menus.tobe.domain.menugroup.support;

import kitchenpos.menus.tobe.domain.menugroup.entity.MenuGroup;

public class MenuGroupBuilder {

    private Long id;
    private String name;

    public MenuGroupBuilder id (Long id){
        this.id = id;
        return this;
    }

    public MenuGroupBuilder name (String name){
        this.name = name;
        return this;
    }

    public MenuGroup build (){
        return new MenuGroup(this.id, this.name);
    }
}
