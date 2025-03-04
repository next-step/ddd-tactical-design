package kitchenpos.order.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.repository.OrderTableRepository;
import kitchenpos.order.domain.fixture.OrderTableFixture;
import kitchenpos.order.eatin.domain.entity.OrderTable;
import kitchenpos.order.eatin.domain.exception.OrderTableGuestsException;
import kitchenpos.order.eatin.domain.model.OrderTableVo;
import kitchenpos.order.eatin.domain.repository.EatinOrderRepository;
import kitchenpos.order.eatin.domain.service.DefaultEatInService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderTableFacadeTest {

    @InjectMocks
    private DefaultEatInService eatinService;
    @Mock
    private OrderTableRepository orderTableRepository;
    @Mock
    private EatinOrderRepository orderRepository;
    private OrderTable orderTable;
    private OrderTableVo.Create createOrderTable;
    private OrderTableVo.Update updateOrderTable;

    @BeforeEach
    void setUp() {
        eatinService = new DefaultEatInService(orderRepository, orderTableRepository);
        orderTable = OrderTableFixture.init().toEntity();
        createOrderTable = OrderTableFixture.init().create();
    }

    @Nested
    @DisplayName("주문 테이블 조회")
    class 주문_테이블_조회 {

        @Test
        @DisplayName("성공 : 특정 조건 없이 상품의 모든 목록을 조회할 수 있다.")
        void 주문테이블_목록_조회() {
            when(orderTableRepository.findAll()).thenReturn(List.of(orderTable));
            List<OrderTable> result = orderTableRepository.findAll();

            assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertEquals(result.size(), 1)
            );
        }
    }

    @Nested
    @DisplayName("주문 테이블 등록")
    class 주문_테이블_등록 {

        @ParameterizedTest
        @DisplayName("성공")
        @ValueSource(strings = {"1번 테이블", "2번 테이블"})
        void 주문_테이블_등록성공(final String name) {

            mockSaveOrderTable();

            assertThatCode(() -> {
                createOrderTable = OrderTableFixture.test(name, 0, false).create();
                eatinService.create(createOrderTable);
            }).doesNotThrowAnyException();

        }

        @ParameterizedTest
        @DisplayName("테이블명은 공란일 수 없다.")
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   ", "\t", "\n"})
        void 테이블명_공란_검사(final String name) {
            assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    createOrderTable = OrderTableFixture.test(name, 0, false).create();
                    eatinService.create(createOrderTable);
                });

        }
    }

    @Nested
    @DisplayName("테이블 착석")
    class 테이블_착석 {

        @Test
        @DisplayName("성공")
        void 주문_테이블_착석성공() {
            mockFindByOrderTable();

            assertThatCode(() -> {
                eatinService.sit(orderTable.getOrderTableId());
            }).doesNotThrowAnyException();

        }

        @Test
        @DisplayName("테이블 사용중 처리한다.")
        void 테이블_사용처리() {
            mockFindByOrderTable();

            var result = eatinService.sit(orderTable.getOrderTableId());

            assertThat(result.occupied()).isTrue();

        }
    }

    @Nested
    @DisplayName("테이블 정리")
    class 테이블_정리 {

        @Test
        @DisplayName("성공")
        void 주문_테이블_정리성공() {

            mockFindByOrderTable();

            mockExistsByOrderTable(false);

            assertThatCode(() -> {
                eatinService.clear(orderTable.getOrderTableId());
            }).doesNotThrowAnyException();

        }

        @Test
        @DisplayName("주문 테이블이 있으면 주문상태가 **완료**이어야 한다.")
        void 주문상태가_완료가아니면_정리불가() {
            mockFindByOrderTable();

            mockExistsByOrderTable(true);

            assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> eatinService.clear(orderTable.getOrderTableId()));
        }

        @Test
        @DisplayName("빈 테이블로 설정한다.")
        void 빈테이블_처리() {
            mockFindByOrderTable();
            mockExistsByOrderTable(false);

            var result = eatinService.clear(orderTable.getOrderTableId());

            assertThat(result.occupied()).isFalse();
        }
    }

    @Nested
    @DisplayName("테이블 인원 변경")
    class 테이블_인원_변경 {

        @Test
        @DisplayName("성공")
        void 주문_테이블_인원변경_성공() {

            orderTable = OrderTableFixture.test("1법 테이블", 1, true).toEntity();

            mockFindByOrderTable();

            updateOrderTable = OrderTableFixture.test("1번 테이블", 3, true).update();
            assertThatCode(() -> {
                eatinService.changeNumberOfGuests(updateOrderTable);
            }).doesNotThrowAnyException();

        }

        @Test
        @DisplayName("테이블 사용중인 상태여야 한다.")
        void 테이블_사용여부_검사() {
            mockFindByOrderTable();
            updateOrderTable = OrderTableFixture.test("1번 테이블", 3, true).update();

            assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(
                    () -> eatinService.changeNumberOfGuests(updateOrderTable));
        }

        @Test
        @DisplayName("테이블 인원 수는 0명 이상이어야 한다.")
        void 테이블_인원수_허용범위_검사() {
            assertThatExceptionOfType(OrderTableGuestsException.class)
                .isThrownBy(
                    () -> {
                        updateOrderTable = OrderTableFixture.test("test", -1, false).update();
                        eatinService.changeNumberOfGuests(updateOrderTable);
                    });

        }
    }

    private void mockFindByOrderTable() {
        when(orderTableRepository.findById(Mockito.any()))
            .thenReturn(Optional.of(orderTable));
    }

    private void mockExistsByOrderTable(boolean status) {
        when(orderRepository.existsByOrderTableIdAndStatusNot(orderTable.getOrderTableId(), OrderStatus.COMPLETED))
            .thenReturn(status);
    }

    private void mockSaveOrderTable() {
        when(orderTableRepository.save(Mockito.any(OrderTable.class))).thenReturn(orderTable);
    }
}
