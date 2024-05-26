package kitchenpos.products.tobe.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.DisplayName;
import kitchenpos.products.tobe.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final MenuRepository menuRepository;


    public ProductService(ProductRepository productRepository, MenuRepository menuRepository, PurgomalumClient purgomalumClient) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    public ProductDto create(final ProductDto request) {
        final Product product = new Product(new DisplayName(validatePurgomalumnName(request.getName())), new Price(request.getPrice()));
        final Product save = productRepository.save(product);
        return ProductDto.from(save);
    }

    @Transactional
    public ProductDto changePrice(final UUID productDtoId, final ProductDto request) {
        final Product toProduct = new Product(new DisplayName(request.getName()), new Price(request.getPrice()));

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


    private String validatePurgomalumnName(final String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("상품명에 비속어가 포함되어 있습니다.");
        }
        return name;
    }

    private void menuPriceGraterThanMenuProductPrice(List<Menu> menus){
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                        menuProduct.getProduct()
                                .getPrice().getPrice()
                                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }

    }

}
