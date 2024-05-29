package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class MenuGroupName {
    @Column(name = "name", nullable = false)
    private final String value;

    protected MenuGroupName(){
        this.value = new String();
    }
    public MenuGroupName(String value) {
        checkGroupName(value);
        this.value = value;
    }

    private void checkGroupName(String name){
        if(Objects.isNull(name) || name.isBlank()){
            throw new IllegalArgumentException();
        }
    }

    public String getValue() {
        return this.value;
    }
}
