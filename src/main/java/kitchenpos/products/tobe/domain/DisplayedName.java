package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.math.BigDecimal;
import java.util.Objects;

public class DisplayedName {
    private String name;
    private final PurgomalumClient purgomalumClient;

    public DisplayedName(String name, PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;

        checkDisplayedName(name);
        this.name = name;
    }

    public void checkDisplayedName(String name){
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }

    public String getDisplayedName(){
        return this.name;
    }

}
