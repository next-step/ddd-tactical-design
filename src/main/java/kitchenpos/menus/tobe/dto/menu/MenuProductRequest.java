package kitchenpos.menus.tobe.dto.menu;

public class MenuProductRequest {

    private ProductRequest productRequest;

    public MenuProductRequest(ProductRequest productRequest) {
        this.productRequest = productRequest;
    }

    public ProductRequest getProductRequest() {
        return productRequest;
    }
}
