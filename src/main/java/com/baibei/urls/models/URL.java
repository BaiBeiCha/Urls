package com.baibei.urls.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "urls")
public class URL {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 2048)
    private String originalUrl;

    @Column(unique = true, length = 64)
    private String shortId;

    public URL(String originalUrl, String shortId) {
        this.originalUrl = originalUrl;
        this.shortId = shortId;
    }
}
