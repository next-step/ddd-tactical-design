package kitchenpos.products.tobe.application

import kitchenpos.products.tobe.domain.Product
import kitchenpos.products.tobe.domain.ProductName
import kitchenpos.products.tobe.domain.ProductNameValidator
import kitchenpos.products.tobe.domain.ProductPrice
import kitchenpos.products.tobe.domain.ProductRepository
import kitchenpos.products.tobe.ui.dto.ProductResponse
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.util.UUID

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productNameValidator: ProductNameValidator,
    private val eventPublisher: ApplicationEventPublisher,
) {
    @Transactional
    fun create(
        name: String,
        price: BigDecimal,
    ): ProductResponse {
        val productName = ProductName(name, productNameValidator)
        val productPrice = ProductPrice(price)
        val newProduct = Product(productName, productPrice)

        save(newProduct)

        return ProductResponse.of(newProduct)
    }

    @Transactional
    fun changePrice(
        productId: UUID,
        newPrice: BigDecimal,
    ): ProductResponse {
        val productNewPrice = ProductPrice(newPrice)
        val product =
            productRepository.findById(productId)
                .orElseThrow { IllegalArgumentException("Product not found") }

        product.changePrice(productNewPrice)

        save(product)

        return ProductResponse.of(product)
    }

    @Transactional(readOnly = true)
    fun findAll(): List<ProductResponse> {
        return productRepository.findAll()
            .map(ProductResponse::of)
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
