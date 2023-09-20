package kitchenpos.products.tobe.domain;

import kitchenpos.menus.shared.dto.MenuProductDto;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuProductCreateService;
import kitchenpos.menus.tobe.domain.menu.MenuProductQuantity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuProductCreateServiceImpl implements MenuProductCreateService {

    private final ProductRepository productRepository;

    public MenuProductCreateServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<MenuProduct> getMenuProducts(List<MenuProductDto> menuProductRequests) {
        final List<MenuProduct> menuProducts = new ArrayList<>();
        for (final MenuProductDto menuProductRequest : menuProductRequests) {
            final long quantity = menuProductRequest.getQuantity();
            if (quantity < 0) {
                throw new IllegalArgumentException();
            }
            final Product product = productRepository.findById(menuProductRequest.getProductId())
                    .orElseThrow(NoSuchElementException::new);
            final MenuProduct menuProduct = new MenuProduct(product.getId(), new MenuProductQuantity(menuProductRequest.getQuantity()));
            menuProducts.add(menuProduct);
        }
        return menuProducts;
    }

    @Override
    public void valid(Menu menu) {
        List<MenuProduct> menuProducts = menu.getMenuProducts();
        BigDecimal sum = BigDecimal.ZERO;
        Map<UUID, Product> products = productRepository.findAllByIdIn(
                menuProducts.stream()
                        .map(MenuProduct::getProductId)
                        .collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(Product::getId, product -> product));

        for (MenuProduct menuProduct : menuProducts) {
            sum = sum.add(
                    products.get(menuProduct.getProductId()).getPrice()
                            .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }

        if (menu.getPrice().compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
    }
}
