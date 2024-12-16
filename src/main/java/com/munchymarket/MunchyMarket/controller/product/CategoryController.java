package com.munchymarket.MunchyMarket.controller.product;

import com.munchymarket.MunchyMarket.domain.SortType;
import com.munchymarket.MunchyMarket.dto.product.*;
import com.munchymarket.MunchyMarket.dto.wrapper.ApiResponse;
import com.munchymarket.MunchyMarket.dto.wrapper.ResponseWrapper;
import com.munchymarket.MunchyMarket.exception.ErrorCode;
import com.munchymarket.MunchyMarket.repository.sorttype.SortTypeRepository;
import com.munchymarket.MunchyMarket.service.CategoryService;
import com.munchymarket.MunchyMarket.service.ProductService;
import com.munchymarket.MunchyMarket.service.SortTypeService;
import com.munchymarket.MunchyMarket.service.common.CommonEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final ProductService productService;
    private final SortTypeRepository sortTypeRepository;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {
        return ResponseEntity.ok(ApiResponse.ofSuccess(categoryService.getAllCategories()));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId) {
        CategoryDto category = categoryService.getCategoryWithChildren(categoryId);
        return ResponseEntity.ok(category);
    }


//    /**
//     * 상품 리스트 표시 페이지에서 상단에 카테고리 리스트 데이터
//     */
//    @GetMapping("/{categoryId}")
//    public ResponseEntity<?> categories(@PathVariable("categoryId") Long categoryId) {;
//        return ResponseEntity.ok().body(categoryRepository.findByIdWithChildren(categoryId));
//    }


    @GetMapping("/sort-types")
    public ResponseEntity<ApiResponse<List<SortTypeDto>>> sortType() {
        List<SortTypeDto> sortTypes = sortTypeRepository.getSortTypes();
        return ResponseEntity.ok().body(ApiResponse.ofSuccess(sortTypes));
    }


    @GetMapping("/{categoryId}/products")
    public ResponseEntity<ApiResponse<ProductListResponseDto>> products(@PathVariable("categoryId") Long categoryId,
                                                           @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                           @RequestParam(value = "size", defaultValue = "9") int size,
                                                           @RequestParam(value = "sorted_type", defaultValue = "1") long sortId){

        /**
         * sorted_type = 0, 신상품 순
         * SortTypeRepository.findById(sorted_type) -> return 값으로 created_date, desc 를 가져옴
         * 예상 테이블 구성
         * SortType Table
         * +--------------+---------------------+----------------+
         * | sort_type_id | sort_type_field     | sort_direction |
         * +--------------+---------------------+----------------+
         * | 1            | createdDate        | desc           |
         * | 2            | salesCount         | desc           |
         * | 3            | basePrice          | asc            |
         * | 4            | basePrice          | desc           |
         * +--------------+---------------------+----------------+
         */


        ProductPageResponseDto productsByCategoryId = productService.getProductsByCategoryId(categoryId, page, size, sortId);
        ProductListResponseDto productListResponseDto = getProductListResponseDto(productsByCategoryId);

        return ResponseEntity.ok().body(ApiResponse.ofSuccess(productListResponseDto));
    }

    private ProductListResponseDto getProductListResponseDto(ProductPageResponseDto productsByCategoryId) {
        Page<ProductDto> productPage = productsByCategoryId.getProductsByCategoryId();
        PageRequest pageRequest = productsByCategoryId.getPageRequest();
        SortType sortType = productsByCategoryId.getSortType();

        Slice<ProductDto> productSlice = new SliceImpl<>(productPage.getContent(), pageRequest, productPage.hasNext());
        return new ProductListResponseDto(sortType.getDisplayName(), productSlice, productPage.getTotalElements(), productPage.getTotalPages());
    }

}
