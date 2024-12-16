package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.domain.*;
import com.munchymarket.MunchyMarket.dto.product.ProductDto;
import com.munchymarket.MunchyMarket.dto.product.ProductPageResponseDto;
import com.munchymarket.MunchyMarket.dto.product.RegisteredProductDto;
import com.munchymarket.MunchyMarket.dto.initsampledata.CustomMultipartFile;
import com.munchymarket.MunchyMarket.dto.initsampledata.SampleProductRequestDto;
import com.munchymarket.MunchyMarket.dto.wrapper.ErrorCode;
import com.munchymarket.MunchyMarket.exception.GcsFileUploadFailException;
import com.munchymarket.MunchyMarket.exception.ProductRegisterException;
import com.munchymarket.MunchyMarket.repository.category.CategoryRepository;
import com.munchymarket.MunchyMarket.repository.image.ImageRepository;
import com.munchymarket.MunchyMarket.repository.packagingtype.PackagingTypeRepository;
import com.munchymarket.MunchyMarket.repository.product.ProductRepository;
import com.munchymarket.MunchyMarket.request.ProductRequestDto;
import com.munchymarket.MunchyMarket.service.common.CommonEntityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.munchymarket.MunchyMarket.utils.FileSizeUtils.readableFileSize;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final GcsUploadService gcsUploadService;

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final CommonEntityService commonEntityService;

    @Transactional(rollbackFor = Exception.class)
    public RegisteredProductDto registerProduct(ProductRequestDto productRequestDto) {

        PackagingType packagingType = commonEntityService.findPackagingTypeById(productRequestDto.getPackagingTypeId());
        Category category = commonEntityService.findCategoryById(productRequestDto.getCategoryId());

        Map<String, MultipartFile> images = productRequestDto.getImages();
        Image mainImage = null;
        Image subImage = null;
        // GCS 에 이미지 등록
        try {

            mainImage = buildAndSaveImage(images.get("mainImage"));
            subImage = buildAndSaveImage(images.get("subImage"));

        } catch (IOException e) {
            throw new GcsFileUploadFailException(ErrorCode.GCS_FILE_UPLOAD_ERROR, ErrorCode.DetailMessage.GCS_FILE_UPLOAD_ERROR);
        }

        Product product = productRequestDto.toEntity(category, packagingType, mainImage, subImage);
        Long savedProductId = productRepository.save(product).getId();

        return productRepository.findByProductId(savedProductId);
    }

    private Image buildAndSaveImage(MultipartFile file) throws IOException {
        String clientFilename = file.getOriginalFilename();
        String fileSize = readableFileSize(file.getSize());
        String serverFilename = gcsUploadService.uploadToGcs(file);

        Image image = Image.builder()
                .clientFilename(clientFilename)
                .serverFilename(serverFilename)
                .fileSize(fileSize)
                .build();

        return imageRepository.save(image);
    }


    public ProductPageResponseDto getProductsByCategoryId(Long categoryId, int page, int size, Long sortId) {
        SortType sortType = commonEntityService.findSortTypeById(sortId);

        Sort sort = Sort.by(sortType.getSortTypeField());
        sort = sortType.getSortDirection()
                .equals("desc") ? sort.descending() : sort.ascending();

        PageRequest pageRequest = PageRequest.of(page - 1, size, sort);

        Page<ProductDto> productsByCategoryId = productRepository.findProductsByCategoryId(categoryId, pageRequest);
        return new ProductPageResponseDto(productsByCategoryId, pageRequest, sortType);
    }

    public RegisteredProductDto getProduct(Long productId) {
        RegisteredProductDto product = productRepository.findByProductId(productId);
        if (product == null) {
            throw new EntityNotFoundException(String.format(ErrorCode.DetailMessage.RESOURCE_NOT_FOUND, "商品", productId));
        }
        return product;
    }


    /**
     * 샘플데이터 등록용 메서드
     */
    @Transactional
    public RegisteredProductDto registerSampleProduct(SampleProductRequestDto sampleProductRequestDto) {

        PackagingType packagingType = commonEntityService.findPackagingTypeById(sampleProductRequestDto.getPackagingTypeId());
        Category category = commonEntityService.findCategoryById(sampleProductRequestDto.getCategoryId());

        Map<String, String> images = sampleProductRequestDto.getProductImages();
        Image mainImage = null;
        Image subImage = null;
        // GCS 에 이미지 등록
        try {
            mainImage = buildAndSaveImage(new CustomMultipartFile(images.get("mainImage")));
            subImage = buildAndSaveImage(new CustomMultipartFile(images.get("subImage")));
        } catch (IOException e) {
            throw new GcsFileUploadFailException(ErrorCode.GCS_FILE_UPLOAD_ERROR, ErrorCode.DetailMessage.GCS_FILE_UPLOAD_ERROR);
        }

        Product product = sampleProductRequestDto.toEntity(category, packagingType, mainImage, subImage);
        log.info("product : {}", product);

        Long savedProductId = productRepository.save(product).getId();
        log.info("savedProductId : {}", savedProductId);

        return productRepository.findByProductId(savedProductId);
    }
}
