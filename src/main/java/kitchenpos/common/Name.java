package kitchenpos.common;

import org.thymeleaf.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {
    private String name;

    protected Name () {}

    public Name (String name){
        this.name = validateName(name);
    }

    public String valueOf (){
        return this.name;
    }

    public String validateName (String name){
        String inputName = name != null ?
            name.replaceAll("\\p{Z}", "") : null;

        if(StringUtils.isEmpty(inputName)){
            throw new WrongNameException();
        }
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
