package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.*;
import kitchenpos.products.tobe.dto.ProductDto;
import kitchenpos.products.tobe.infra.Profanities;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service("tobeProductService")
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final DisplayNamePolicy displayNamePolicy;

    public ProductService(ProductRepository productRepository, MenuRepository menuRepository, DisplayNamePolicy displayNamePolicy) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.displayNamePolicy = displayNamePolicy;
    }

    public ProductDto create(final ProductDto request) {
        final Product product = new Product(request.getName(), new Price(request.getPrice()), displayNamePolicy);
        final Product save = productRepository.save(product);
        return ProductDto.from(save);
    }

    @Transactional
    public ProductDto changePrice(final UUID productDtoId, final ProductDto request) {
        final Product toProduct = new Product(request.getName(),new Price(request.getPrice()), displayNamePolicy);

        final Product findedProduct = productRepository.findById(productDtoId)
                .orElseThrow(IllegalArgumentException::new);

        final Product changePriceProduct = findedProduct.changePrice(toProduct.getPrice());
        List<Menu> menus = menuRepository.findAllByProductId(productDtoId);
        menuPriceGraterThanMenuProductPrice(menus);
        return ProductDto.from(changePriceProduct);
    }

    public List<ProductDto> findAll(){
        return ProductDto.ofList(productRepository.findAll());
    }

    private void menuPriceGraterThanMenuProductPrice(List<Menu> menus){
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                        menuProduct.getProduct()
                                .getPrice()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }

    }

}
