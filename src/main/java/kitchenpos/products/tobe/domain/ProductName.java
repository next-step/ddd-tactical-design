package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.util.Objects;

public class ProductName {
    private String name;
    private final PurgomalumClient purgomalumClient;

    public ProductName(String name, PurgomalumClient purgomalumClient) {
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
