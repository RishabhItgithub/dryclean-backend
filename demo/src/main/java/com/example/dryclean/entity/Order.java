package com.example.dryclean.entity;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String serviceType;
    private String status; // e.g., PENDING, PAID
    private String paymentId;
}
