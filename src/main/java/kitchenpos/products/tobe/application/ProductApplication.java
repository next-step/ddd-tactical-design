package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProductApplication {
    private ProductRepository productRepository;

    public ProductApplication (final ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public ProductData registerNewProduct (ProductData productData) {

        Product newProduct = new Product(productData.getName(), productData.getPrice());
        productRepository.save(newProduct);

        return convertToData(newProduct);
    }

    private ProductData convertToData(Product product) {
        ProductData productData = new ProductData();
        productData.setId(product.getId());
        productData.setName(product.getName());
        productData.setPrice(product.getPrice());
        return productData;
    }

    public List<ProductData> listProduct(){
        return productRepository.findAll()
                .stream()
                .map(product->convertToData(product))
                .collect(Collectors.toList());
    }

}
