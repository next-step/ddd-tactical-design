package kitchenpos.products.tobe.domain

import kitchenpos.products.domain.Product
import kitchenpos.products.domain.ProductRepository
import org.springframework.data.jpa.repository.JpaRepository

interface JpaProductRepository : JpaRepository<Product, Long>, ProductRepository {
}
