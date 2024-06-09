package kitchenpos.menus.tobe.domain.application;


import kitchenpos.products.tobe.domain.repository.ProductRepository;

public class CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantityTestFixture extends DefaultCalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity {

    public CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantityTestFixture(
        ProductRepository productRepository) {
        super(productRepository);
    }
}
