package com.sixlab.logistics.common.shared.exception;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

/*
* order-service 에서 발생할 수 있는 예외로
* 주문하는 특정 물품 수량이 허브에서 관리하는 수량보다 많을 경우 주문이 성립되지 못하도록 해당 예외를 발생시킵니다.
* */
@Slf4j
public class OutOfStockException extends RuntimeException {
    private HttpStatus status;
    private String message;

    // 기본생성자, lombok 으로도 처리할 수 있음: @NoArgsConstructor
    public OutOfStockException() {}

    public OutOfStockException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public OutOfStockException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
