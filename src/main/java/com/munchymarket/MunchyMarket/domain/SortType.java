package com.munchymarket.MunchyMarket.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Table(name = "sort_types")
@Entity
public class SortType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sort_type_id")
    private Long id;

    @Column(name = "sort_type_field")
    private String sortTypeField;

    @Column(name = "sort_direction")
    private String sortDirection;

    @Column(name = "display_name")
    private String displayName;
}
