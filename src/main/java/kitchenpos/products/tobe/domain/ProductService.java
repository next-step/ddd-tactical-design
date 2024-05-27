package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.vo.Name;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductService {
    private final Product product;
    private final InMemoryProductRepository productRepository;

    public ProductService(Product product, InMemoryProductRepository repository) {
        this.product = product;
        this.productRepository = repository;
    }

    public String register() {
        try {
            this.product.validateProperty();
        } catch (Exception e) {
            // 에러에 맞는 response detail 추가
            return "error occur";
        }

        this.productRepository.save(this.product);
        return "success";
    }

    public void changeName(UUID productId, String name) throws Exception {
//        this.nameValidator.execute(name);

        Optional<Product> optionalProduct = this.productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            // Handle the case when the product is not found
            throw new Exception("Product not found with id: " + productId);
        }

        Product product = optionalProduct.get();
        Product changedProduct = new Product(productId, new Name(name), product.getPrice());
        this.productRepository.update(changedProduct);
    }

    public List<Product> getList(){
        return this.productRepository.findAll();
    }

    public Optional<Product> findById(UUID id){
        return this.productRepository.findById(id);
    }
}
