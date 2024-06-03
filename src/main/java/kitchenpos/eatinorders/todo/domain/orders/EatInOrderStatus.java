package kitchenpos.eatinorders.todo.domain.orders;

public enum EatInOrderStatus {
    WAITING {
        @Override
        public EatInOrderStatus next() {
            return ACCEPTED;
        }
    },
    ACCEPTED {
        @Override
        public EatInOrderStatus next() {
            return SERVED;
        }
    },
    SERVED {
        @Override
        public EatInOrderStatus next() {
            return COMPLETED;
        }
    },
    COMPLETED {
        @Override
        public EatInOrderStatus next() {
            throw new IllegalStateException();
        }
    };

    public abstract EatInOrderStatus next();
}
