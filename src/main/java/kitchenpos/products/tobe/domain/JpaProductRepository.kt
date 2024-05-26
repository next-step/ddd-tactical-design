package kitchenpos.products.tobe.domain

import org.springframework.data.jpa.repository.JpaRepository

interface JpaProductRepository : JpaRepository<Product, Long>, ProductRepository
