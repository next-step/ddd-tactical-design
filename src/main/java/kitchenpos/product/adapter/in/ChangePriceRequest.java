package kitchenpos.product.adapter.in;

class ChangePriceRequest {
    private long price;

    ChangePriceRequest() {}

    ChangePriceRequest(long price) {
        this.price = price;
    }

    public long getPrice() {
        return price;
    }
}