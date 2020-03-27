package kitchenpos.menus.tobe.menu.infra;

import kitchenpos.menus.tobe.menu.application.dto.ProductQuantityDto;
import kitchenpos.menus.tobe.menu.domain.MenuProduct;
import kitchenpos.menus.tobe.menu.domain.Products;
import kitchenpos.products.tobe.application.ProductService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class RemoteProducts implements Products {

    private final ProductService productService;

    public RemoteProducts(final ProductService productService) {
        this.productService = productService;
    }

    // 외부(Product)가 일으키는 에러를 Menu Context가 알 필요가 없으므로, 에러가 났을 때 캐치하여 Menu가 알 수 있는 Error로 래핑하여 throw 해준다.
    @Override
    public List<MenuProduct> getMenuProductsByProductIdsAndQuantities(final List<ProductQuantityDto> productQuantityDtos) {

        final List<Long> productIds = productQuantityDtos.stream()
                .map(ProductQuantityDto::getProductId)
                .collect(Collectors.toList());

        final List<MenuProduct> menuProducts = productService.findAllById(productIds)
                .stream()
                .map(product -> {
                    Long quantity = productQuantityDtos.stream()
                            .filter(productQuantityDto -> product.getId().equals(productQuantityDto.getProductId()))
                            .findFirst()
                            .orElseThrow(IllegalArgumentException::new)
                            .getQuantity();
                    return new MenuProduct(product.getId(), product.getPrice(), quantity);
                }).collect(Collectors.toList());

        return menuProducts;
    }
}
