package kitchenpos.products.tobe.application

import kitchenpos.products.tobe.domain.Product
import kitchenpos.products.tobe.domain.ProductName
import kitchenpos.products.tobe.domain.ProductNameValidator
import kitchenpos.products.tobe.domain.ProductPrice
import kitchenpos.products.tobe.domain.ProductRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productNameValidator: ProductNameValidator,
    private val eventPublisher: ApplicationEventPublisher,
) {
    @Transactional
    fun create(
        name: ProductName,
        price: ProductPrice,
    ): Product {
        val product = Product(name, price, productNameValidator)
        return save(product)
    }

    @Transactional
    fun changePrice(
        productId: UUID,
        newPrice: ProductPrice,
    ): Product {
        val product =
            productRepository.findById(productId).orElseThrow {
                IllegalArgumentException("Product not found")
            }
        product.changePrice(newPrice)
        return save(product)
    }

    @Transactional(readOnly = true)
    fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    private fun save(product: Product): Product {
        val savedProduct = productRepository.save(product)
        dispatchEvents(savedProduct)
        return savedProduct
    }

    private fun dispatchEvents(product: Product) {
        product.getAndClearDomainEvents().forEach(eventPublisher::publishEvent)
    }
}
