package kitchenpos.product.tobe.infra.jpa;

import kitchenpos.common.name.Name;
import kitchenpos.common.price.Price;
import kitchenpos.product.tobe.domain.entity.Product;
import kitchenpos.util.converter.Converter;
import org.springframework.stereotype.Component;

public class ProductEntityConverter {

    private ProductEntityConverter() {
    }

    @Component
    public static class ProductToProductEntityConverter
        implements Converter<Product, ProductEntity> {

        @Override
        public ProductEntity convert(Product source) {
            final ProductEntity productEntity = new ProductEntity();
            productEntity.id = source.id;
            productEntity.name = source.name.value;
            productEntity.price = source.price().value;
            return productEntity;
        }
    }

    @Component
    public static class ProductEntityToProductConverter
        implements Converter<ProductEntity, Product> {

        @Override
        public Product convert(ProductEntity source) {
            return new Product(source.id, new Name(source.name), new Price(source.price));
        }
    }
}
