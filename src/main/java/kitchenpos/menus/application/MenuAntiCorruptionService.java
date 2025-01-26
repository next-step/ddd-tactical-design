package kitchenpos.menus.application;

import kitchenpos.menus.application.dto.MenuCreateProductRequest;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MenuAntiCorruptionService {

    private final ProductRepository productRepository;

    public MenuAntiCorruptionService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public MenuProducts createMenuProducts(List<MenuCreateProductRequest> menuCreateProductRequests) {
        Assert.isTrue(!Objects.isNull(menuCreateProductRequests) && !menuCreateProductRequests.isEmpty(), "메뉴 상품 정보가 누락되었습니다.");

        final List<Product> products = productRepository.findAllByIdIn(
                menuCreateProductRequests.stream()
                        .map(MenuCreateProductRequest::getProductId)
                        .collect(Collectors.toList())
        );
        Assert.isTrue(products.size() == menuCreateProductRequests.size(), "메뉴 상품 정보가 등록 된 상품 정보와 일치하지 않습니다.");

        var productMap = products.stream()
                .map(it -> new kitchenpos.menus.tobe.domain.Product(it.getId(), it.getPrice()))
                .collect(Collectors.toMap(kitchenpos.menus.tobe.domain.Product::getProductId, Function.identity()));

        var menuProducts = menuCreateProductRequests.stream().map(it -> {
            var product = productMap.get(it.getProductId());
            return MenuProduct.newOne(product, it.getQuantity());
        }).collect(Collectors.toList());

        return MenuProducts.newOne(menuProducts);
    }
}
