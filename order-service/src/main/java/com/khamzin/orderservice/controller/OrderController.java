package com.khamzin.orderservice.controller;

import com.khamzin.orderservice.dto.order.OrderRequestDto;
import com.khamzin.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        log.info("Placing Order");
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequestDto));
    }

    public CompletableFuture<String> fallbackMethod(OrderRequestDto orderRequestDto, RuntimeException runtimeException) {
        log.info("Can not place order. Executing fallback logic");
        return CompletableFuture.supplyAsync(() -> "Something went wrong, please order after some time");
    }
}