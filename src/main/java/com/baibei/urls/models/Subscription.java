package com.baibei.urls.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private UserInfo user;

    @Column(nullable = false)
    private Date buyingDate;

    @Column(nullable = false)
    private Date expiryDate;

    @Column(nullable = false)
    private BigDecimal price;

    public String toString() {
        return "Subscription [user=" + user + ", buyingDate=" + buyingDate
                + ", expiryDate=" + expiryDate + ", price=" + price + "]";
    }
}
