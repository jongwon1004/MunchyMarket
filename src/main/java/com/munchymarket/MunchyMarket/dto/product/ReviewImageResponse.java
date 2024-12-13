package com.munchymarket.MunchyMarket.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReviewImageResponse {

    private Long id;
    private String serverFilename;

    public ReviewImageResponse(Long id, String serverFilename) {
        this.id = id;
        this.serverFilename = serverFilename;
    }
}
