package com.munchymarket.MunchyMarket.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
@Entity
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id", updatable = false)
    private Long id;

    @Column(name = "client_filename", nullable = false)
    private String clientFilename;

    @Column(name = "server_filename", nullable = false)
    private String serverFilename;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
