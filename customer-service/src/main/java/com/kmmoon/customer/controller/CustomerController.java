package com.kmmoon.customer.controller;import io.swagger.v3.oas.annotations.Operation;import lombok.extern.slf4j.Slf4j;import org.springframework.cloud.context.config.annotation.RefreshScope;import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.PathVariable;import org.springframework.web.bind.annotation.RequestHeader;import org.springframework.web.bind.annotation.RestController;import java.net.http.HttpHeaders;@Slf4j@RefreshScope@RestController(value = "/user")public class CustomerController {    @GetMapping("/test")    @Operation(summary = "Test Ribbon")    public String greeting(@PathVariable String message, @RequestHeader HttpHeaders headers) {        log.info("### Received: /greeting/" + message);        return "[" + "test" + "] " + "asdf";    }}