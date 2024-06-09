package kitchenpos.products.tobe.application

import kitchenpos.products.tobe.application.dto.ChangePriceRequest
import kitchenpos.products.tobe.application.dto.CreateProductRequest
import kitchenpos.products.tobe.application.dto.ProductResponse
import kitchenpos.products.tobe.domain.TobeProduct
import kitchenpos.products.tobe.domain.TobeProductRepository
import kitchenpos.shared.domain.DefaultProfanities
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException

@Service
class TobeProductService(
    private val tobeProductRepository: TobeProductRepository,
) {
    @Transactional
    fun create(request: CreateProductRequest): ProductResponse {
        val product = TobeProduct(request.id, request.name, request.price, DefaultProfanities())
        val savedProduct = tobeProductRepository.save(product)
        return savedProduct.toDto()
    }

    @Transactional
    fun changePrice(request: ChangePriceRequest): ProductResponse {
        val price = request.price
        val product =
            tobeProductRepository.findById(request.productId)
                .orElseThrow { NoSuchElementException() }
        product.changePrice(price)

        // TODO :  상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
        val savedProduct = tobeProductRepository.save(product)
        return savedProduct.toDto()
    }

    @Transactional(readOnly = true)
    fun findAll(): List<ProductResponse> {
        val products = tobeProductRepository.findAll()
        return products.map { it.toDto() }
    }
}
