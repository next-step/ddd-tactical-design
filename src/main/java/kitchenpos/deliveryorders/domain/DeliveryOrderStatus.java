package kitchenpos.deliveryorders.domain;

public enum DeliveryOrderStatus {
    WAITING {
        @Override
        public DeliveryOrderStatus next() {
            return ACCEPTED;
        }
    },
    ACCEPTED {
        @Override
        public DeliveryOrderStatus next() {
            return SERVED;
        }
    },
    SERVED {
        @Override
        public DeliveryOrderStatus next() {
            return DELIVERING;
        }
    },
    DELIVERING {
        @Override
        public DeliveryOrderStatus next() {
            return DELIVERED;
        }
    },
    DELIVERED {
        @Override
        public DeliveryOrderStatus next() {
            return COMPLETED;
        }
    },
    COMPLETED {
        @Override
        public DeliveryOrderStatus next() {
            throw new IllegalStateException();
        }
    };

    public abstract DeliveryOrderStatus next();
}
