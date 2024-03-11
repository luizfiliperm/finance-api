package com.lv.finance.entities.wallet;

import com.lv.finance.entities.wallet.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
