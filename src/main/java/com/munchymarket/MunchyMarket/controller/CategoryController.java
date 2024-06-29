package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.dto.ProductDto;
import com.munchymarket.MunchyMarket.dto.ResponseWrapper;
import com.munchymarket.MunchyMarket.dto.SortTypeDto;
import com.munchymarket.MunchyMarket.repository.category.CategoryRepository;
import com.munchymarket.MunchyMarket.repository.sorttype.SortTypeRepository;
import com.munchymarket.MunchyMarket.service.CategoryService;
import com.munchymarket.MunchyMarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final ProductService productService;
    private final SortTypeRepository sortTypeRepository;
    private final CategoryRepository categoryRepository;


    /**
     * 카테고리 누르고 상품 리스트에 들어와서 카테고리 리스트는 프론트쪽에서 상태로 데이터를 가지고 있기때문에, 이 메서드는 사용하지 않는것으로.
     * private final CategoryRepository categoryRepository;
     *
     * @GetMapping("/{categoryId}")
     * public ResponseEntity<?> categories(@PathVariable("categoryId") Long categoryId) {;
     *     return ResponseEntity.ok().body(categoryRepository.findByIdWithChildren(categoryId));
     * }
     */

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> categories(@PathVariable("categoryId") Long categoryId) {;
        return ResponseEntity.ok().body(categoryRepository.findByIdWithChildren(categoryId));
    }



    /**
     * TODO : Repository 바로 호출하기때문에 Service 추가할것
     * @return
     */
    @GetMapping("/sort-type")
    public ResponseEntity<ResponseWrapper<SortTypeDto>> sortType() {
        return ResponseEntity.ok().body(new ResponseWrapper<>(sortTypeRepository.getSortTypes()));
    }

    /**
     * TODO: Sort 테이블 만들어서 프론트에서 sorted_type= 0 이면 신상품순, 1이면 판매량순, 3이면 낮은가격순, 4이면 높은 가격순 으로 정렬
     */
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<Slice<ProductDto>> products(@PathVariable("categoryId") Long categoryId,
                                                      @RequestParam("page") int page,
                                                      @RequestParam(value = "size", defaultValue = "9") int size,
                                                      @RequestParam(value = "sorted_type", defaultValue = "id") String sort){

        /**
         * sorted_type = 0, 신상품 순
         * SortTypeRepository.findById(sorted_type) -> return 값으로 created_date, desc 를 가져옴
         * 예상 테이블 구성
         * SortType Table
         * +--------------+---------------------+----------------+
         * | sort_type_id | sort_type_field     | sort_direction |
         * +--------------+---------------------+----------------+
         * | 0            | createdDate        | desc           |
         * | 1            | salesCount         | desc           |
         * | 2            | price               | asc            |
         * | 3            | price               | desc           |
         * +--------------+---------------------+----------------+
         */

        PageRequest pageRequest = PageRequest.of(page-1, size, Sort.by(sort).descending());
        log.info("pageRequest = {}", pageRequest);
        log.info("pageRequest.getOffset() = {}", pageRequest.getOffset());

        return ResponseEntity.ok().body(productService.getProductsByCategoryId(categoryId, pageRequest));
    }

}
