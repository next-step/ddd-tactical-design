package kitchenpos.products.tobe.domain;

import kitchenpos.products.domain.ProductRepository;

public class ProductService {
    private final Product product;
    private final ProductRepository productRepository;
    private final NameValidator nameValidator;

    public ProductService(Product product, ProductRepository repository, NameValidator nameValidator) {
        this.product = product;
        this.productRepository = repository;
    }

    public void register() {
        try {
            this.product.validateProperty();
        } catch (Exception e) {
            // 에러에 맞는 response detail 추가
            return Response(e);
        }

        this.productRepository.save(this.product);
    }

    public void changeName(int productId, String name) {
        this.nameValidator.execute(name);

        Product product = this.productRepository.get(productId);
        Product changedProduct = new Product(product.getPrice(), name);
        this.productRepository.update(changedProduct);
    }

    public Array<Product> getList(){
        return this.productRepository.getAll();
    }
}
