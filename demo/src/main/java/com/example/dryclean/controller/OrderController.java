package com.example.dryclean.controller;

import com.example.dryclean.entity.Order;
import com.example.dryclean.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/customer")
    public List<Order> getCustomerOrders(@RequestParam String name) {
        return orderService.getOrdersByCustomer(name);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @PostMapping("/pay/{id}")
    public Order payOrder(@PathVariable Long id, @RequestParam String paymentId) {
        return orderService.markAsPaid(id, paymentId);
    }

    // ✅ NEW — Create Razorpay Payment Order
    @PostMapping("/payment")
    public String createPaymentOrder(@RequestBody Order order) throws Exception {
        return orderService.createPaymentOrder(order);
    }
}

