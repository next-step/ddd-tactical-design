package kitchenpos.common;

import org.thymeleaf.util.StringUtils;

import javax.persistence.Embeddable;

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

}
