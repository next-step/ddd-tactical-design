package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.vo.MenuProductVO;
import kitchenpos.menus.tobe.domain.menu.vo.MenuProducts;
import kitchenpos.products.tobe.infra.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DefaultProductService implements ProductService{

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public MenuProducts findAllPrice (MenuProducts menuProducts){
        MenuProducts findMenuProducts = new MenuProducts();

        menuProducts.list()
            .stream()
            .forEach(menuProduct ->{
                BigDecimal price = productRepository.findProductPriceById(menuProduct.getProductId());
                findMenuProducts.add(
                    new MenuProductVO(
                        menuProduct.getProductId(),
                        menuProduct.getQuantity(),
                        price
                    )
                );
            });

        return findMenuProducts;
    }
}
