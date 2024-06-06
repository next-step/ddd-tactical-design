package kitchenpos.products.tobe.application

import kitchenpos.products.tobe.domain.TobeProduct
import kitchenpos.products.tobe.domain.TobeProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.NoSuchElementException
import java.util.UUID

@Service
class TobeProductService(
    private val tobeProductRepository: TobeProductRepository,
) {
    @Transactional
    fun create(request: TobeProduct): TobeProduct {
        return tobeProductRepository.save(request)
    }

    @Transactional
    fun changePrice(
        productId: UUID,
        request: TobeProduct,
    ): TobeProduct {
        val price = request.price
        val product =
            tobeProductRepository.findById(productId)
                .orElseThrow { NoSuchElementException() }
        product.changePrice(price)

        // TODO :  상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
        return tobeProductRepository.save(product)
    }

    @Transactional(readOnly = true)
    fun findAll(): MutableList<TobeProduct> {
        return tobeProductRepository.findAll()
    }
}
