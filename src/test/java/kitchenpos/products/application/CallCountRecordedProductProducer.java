package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.ProductProducer;

import java.util.UUID;

public class CallCountRecordedProductProducer implements ProductProducer {

    private int callCounter = 0;


    @Override
    public void publish(UUID productId) {
        callCounter++;
    }

    public int getCallCounter() {
        return callCounter;
    }

}
