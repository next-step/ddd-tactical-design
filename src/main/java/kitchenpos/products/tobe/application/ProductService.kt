package kitchenpos.products.tobe.application

import kitchenpos.common.Price
import kitchenpos.products.domain.ProductPriceChanged
import kitchenpos.products.tobe.domain.*
import kitchenpos.products.tobe.dto.int.ProductCreateRequest
import kitchenpos.products.tobe.dto.out.ProductResponse
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productNameValidatorService: ProductNameValidatorService,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun createProduct(request: ProductCreateRequest): ProductResponse {
        val product = Product(
            request.displayedName,
            request.price,
            productNameValidatorService
        )

        return productRepository.save(product)
            .let {
                ProductResponse(
                    id = it.id,
                    name = it.name,
                    price = it.price
                )
            }
    }

    @Transactional
    fun changePrice(productId: UUID, price: Price) {
        val product = productRepository.findById(productId) ?: throw NoSuchElementException("can not found product: $productId")
        product.changePrice(price)

        productRepository.save(product)

        applicationEventPublisher.publishEvent(
            ProductPriceChanged(
                productId = product.id,
                price = price
            )
        )
    }
}
