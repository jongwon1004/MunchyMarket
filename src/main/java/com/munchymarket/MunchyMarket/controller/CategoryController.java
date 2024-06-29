package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.dto.*;
import com.munchymarket.MunchyMarket.repository.category.CategoryRepository;
import com.munchymarket.MunchyMarket.repository.sorttype.SortTypeRepository;
import com.munchymarket.MunchyMarket.service.CategoryService;
import com.munchymarket.MunchyMarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final ProductService productService;
    private final SortTypeRepository sortTypeRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;


    @GetMapping
    public ResponseEntity<ResponseWrapper<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(new ResponseWrapper<>(categoryService.getAllCategories()));
    }


//    /**
//     * 상품 리스트 표시 페이지에서 상단에 카테고리 리스트 데이터
//     */
//    @GetMapping("/{categoryId}")
//    public ResponseEntity<?> categories(@PathVariable("categoryId") Long categoryId) {;
//        return ResponseEntity.ok().body(categoryRepository.findByIdWithChildren(categoryId));
//    }



    /**
     * TODO : Repository 바로 호출하기때문에 Service 추가할것
     * @return
     */
    @GetMapping("/sort-types")
    public ResponseEntity<ResponseWrapper<SortTypeDto>> sortType() {
        return ResponseEntity.ok().body(new ResponseWrapper<>(sortTypeRepository.getSortTypes()));
    }

    /**
     * TODO: Sort 테이블 만들어서 프론트에서 sorted_type= 0 이면 신상품순, 1이면 판매량순, 3이면 낮은가격순, 4이면 높은 가격순 으로 정렬
     */
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<ProductListResponseDto> products(@PathVariable("categoryId") Long categoryId,
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

        Page<ProductDto> productPage = productService.getProductsByCategoryId(categoryId, pageRequest);

        Slice<ProductDto> productSlice = new SliceImpl<>(productPage.getContent(), pageRequest, productPage.hasNext());
        ProductListResponseDto productListResponseDto =
                new ProductListResponseDto(productSlice,productPage.getTotalElements(), productPage.getTotalPages());

        return ResponseEntity.ok().body(productListResponseDto);
    }

}
