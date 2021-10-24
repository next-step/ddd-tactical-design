package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.ProductName;
import kitchenpos.menus.tobe.domain.ProductPrice;
import kitchenpos.menus.tobe.domain.TobeMenuProduct;
import kitchenpos.menus.tobe.domain.TobeProduct;
import kitchenpos.menus.tobe.ui.MenuProductForm;
import kitchenpos.products.tobe.application.TobeProductService;
import kitchenpos.products.tobe.ui.ProductForm;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MenuHandlerAdaptor implements MenuAdaptor {
    private final TobeProductService productService;

    public MenuHandlerAdaptor(final TobeProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<TobeMenuProduct> productFindAllByIdIn(List<MenuProductForm> menuProductForms) {
        List<TobeMenuProduct> menuProducts = menuProductForms.stream()
                .map(form -> {
                    final TobeProduct product = productFormToProduct(productService.findById(form.getProductId()));
                    return new TobeMenuProduct(
                        product,
                        form.getQuantity()
                    );
                })
                .collect(Collectors.toList());

        return menuProducts;
    }

    private TobeProduct productFormToProduct(ProductForm form) {
        return new TobeProduct(
                form.getId(),
                new ProductName(form.getName()),
                new ProductPrice(form.getPrice())
        );
    }
}
