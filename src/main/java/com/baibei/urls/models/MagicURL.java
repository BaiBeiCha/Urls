package com.baibei.urls.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "magic_urls", indexes = @Index(columnList = "shortId"))
public class MagicURL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2048)
    private String originalUrl;

    @Column(unique = true, length = 64)
    private String shortId;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private Long clicks = 0L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    @Column(nullable = false)
    private Boolean noAds = false;

}
