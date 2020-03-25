package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.vo.NewMenuProduct;
import kitchenpos.products.tobe.infra.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultProductService implements ProductService{

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<NewMenuProduct> findAllProductPrice(List<NewMenuProduct> newMenuProducts) {
        List<NewMenuProduct> findMenuProducts = new ArrayList<>();
        newMenuProducts.stream()
            .forEach(menuProduct ->{
                BigDecimal price = productRepository.findProductPriceById(menuProduct.getProductId());
                findMenuProducts.add(
                    new NewMenuProduct(
                        menuProduct.getProductId(),
                        menuProduct.getQuantity(),
                        price)
                );
            });

        return findMenuProducts;
    }
}
