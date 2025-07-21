package com.example.dryclean.service;

import com.example.dryclean.entity.Order;
import com.example.dryclean.repository.OrderRepository;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private RazorpayClient razorpayClient;

    public OrderService() throws Exception {
        razorpayClient = new RazorpayClient("rzp_test_KI14cf7hjlzMLJ", "joYIdYhCyLMrDhRIkbNlxS0Y");
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByCustomer(String customer) {
        return orderRepository.findByCustomerName(customer);
    }

    public Order saveOrder(Order order) {
        order.setStatus("PENDING");
        return orderRepository.save(order);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setCustomerName(updatedOrder.getCustomerName());
        order.setServiceType(updatedOrder.getServiceType());
        order.setStatus(updatedOrder.getStatus());
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public Order markAsPaid(Long id, String paymentId) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        order.setStatus("PAID");
        order.setPaymentId(paymentId);
        return orderRepository.save(order);
    }

    public String createPaymentOrder(Order order) throws Exception {
        JSONObject options = new JSONObject();
        options.put("amount", 50000); // Amount in paise
        options.put("currency", "INR");
        options.put("receipt", "order_rcptid_" + order.getCustomerName());

        // ✅ Use fully qualified Razorpay Order class
        com.razorpay.Order razorpayOrder = razorpayClient.orders.create(options);

        order.setPaymentId(razorpayOrder.get("id"));
        order.setStatus("PENDING");
        orderRepository.save(order);

        return razorpayOrder.toString();
    }
}
