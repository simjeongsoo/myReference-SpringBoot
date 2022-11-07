package com.sim.jpashop.exception;

public class NotEnoughStockException extends RuntimeException {
    //--상품엔티티 비즈니스로직, 재고수량에 대한 예외--//
    public NotEnoughStockException() {
        super();
    }

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }
}
