package kitchenpos.tobe.product.application

import kitchenpos.tobe.product.application.dto.request.ChangeProductPriceRequest
import kitchenpos.tobe.product.application.dto.request.CreateProductRequest
import kitchenpos.tobe.product.application.dto.response.ChangeProductPriceResponse
import kitchenpos.tobe.product.application.dto.response.CreateProductResponse
import kitchenpos.tobe.product.application.dto.response.GetProductResponse
import kitchenpos.tobe.product.domain.ProductPurgomalumClient
import kitchenpos.tobe.product.domain.entity.ProductV2
import kitchenpos.tobe.product.domain.repository.ProductRepositoryV2
import kitchenpos.tobe.product.infra.getProductById
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class ProductServiceV2(
    private val productRepository: ProductRepositoryV2,
    private val pugomalumClient: ProductPurgomalumClient,
    private val eventPublisher: ApplicationEventPublisher,
) {
    fun create(request: CreateProductRequest): CreateProductResponse {
        val saved =
            productRepository.save(
                ProductV2.from(
                    name = request.name,
                    price = request.price,
                    purgomalumClient = pugomalumClient,
                ),
            )

        return CreateProductResponse(
            id = saved.id,
            name = saved.getName(),
            price = saved.getPrice(),
        )
    }

    fun changePrice(
        productId: UUID,
        request: ChangeProductPriceRequest,
    ): ChangeProductPriceResponse {
        val product = productRepository.getProductById(productId)
        val changed = product.changePrice(request.price, eventPublisher)
        return ChangeProductPriceResponse(
            productId = changed.id,
            price = changed.getPrice(),
        )
    }

    @Transactional(readOnly = true)
    fun findAll(): List<GetProductResponse> {
        val products = productRepository.findAll()
        return products.map {
            GetProductResponse(
                id = it.id,
                name = it.getName(),
                price = it.getPrice(),
            )
        }
    }
}
