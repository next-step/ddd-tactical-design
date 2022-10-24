package kitchenpos.product.tobe.infra.jpa;

import kitchenpos.common.name.NameFactory;
import kitchenpos.common.price.Price;
import kitchenpos.product.tobe.domain.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class ProductEntityConverter {

    private ProductEntityConverter() {
    }

    @Component
    public static class ProductToProductEntityConverter {

        public ProductEntity convert(Product source) {
            final ProductEntity productEntity = new ProductEntity();
            productEntity.id = source.id;
            productEntity.name = source.name.value;
            productEntity.price = source.price().value;
            return productEntity;
        }
    }

    @Component
    public static class ProductEntityToProductConverter {

        private final NameFactory nameFactory;

        @Autowired
        public ProductEntityToProductConverter(NameFactory nameFactory) {
            this.nameFactory = nameFactory;
        }

        public Product convert(ProductEntity source) {
            return new Product(
                source.id,
                this.nameFactory.create(source.name),
                new Price(source.price)
            );
        }
    }
}
