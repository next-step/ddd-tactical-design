package kitchenpos.products.tobe.domain.vo;

import java.util.ArrayList;
import java.util.List;

public class DisplayedName {
    private final String value;
    public DisplayedName(String value){
        this.validate(value);
        this.value = value;
    }

    public String value(){
        return this.value;
    }

    private void validate(String word) {
        List<String> wrongWords = new ArrayList<>();
        wrongWords.add("wrong-name");

        if (wrongWords.contains(word)) {
            throw new IllegalArgumentException("The word '" + word + "' is not allowed.");
        }
    }

}
