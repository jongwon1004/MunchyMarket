package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.domain.Category;
import com.munchymarket.MunchyMarket.domain.Image;
import com.munchymarket.MunchyMarket.domain.PackagingType;
import com.munchymarket.MunchyMarket.domain.Product;
import com.munchymarket.MunchyMarket.dto.ProductDto;
import com.munchymarket.MunchyMarket.dto.RegisteredProductDto;
import com.munchymarket.MunchyMarket.exception.ErrorCode;
import com.munchymarket.MunchyMarket.exception.GcsFileUploadFailException;
import com.munchymarket.MunchyMarket.exception.ProductRegisterException;
import com.munchymarket.MunchyMarket.repository.category.CategoryRepository;
import com.munchymarket.MunchyMarket.repository.image.ImageRepository;
import com.munchymarket.MunchyMarket.repository.packagingtype.PackagingTypeRepository;
import com.munchymarket.MunchyMarket.repository.product.ProductRepository;
import com.munchymarket.MunchyMarket.request.ProductRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.munchymarket.MunchyMarket.utils.FileSizeUtils.readableFileSize;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {

    private final GcsUploadService gcsUploadService;

    private final ProductRepository productRepository;
    private final PackagingTypeRepository packagingTypeRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public RegisteredProductDto registerProduct(ProductRequestDto productRequestDto) {

        List<String> errors = new ArrayList<>();

        PackagingType packagingType = fetchPackagingType(productRequestDto.getPackagingTypeId(), errors);
        Category category = fetchCategory(productRequestDto.getCategoryId(), errors);

        if (!errors.isEmpty()) {
            throw new ProductRegisterException(errors);
        }

        Map<String, MultipartFile> images = productRequestDto.getImages();
        Image mainImage = null;
        Image subImage = null;
        // GCS 에 이미지 등록
        try {

            mainImage = buildAndSaveImage(images.get("mainImage"));
            subImage = buildAndSaveImage(images.get("subImage"));

        } catch (IOException e) {
            throw new GcsFileUploadFailException(ErrorCode.GCS_FILE_UPLOAD_ERROR.getMessage(), e);
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

    private PackagingType fetchPackagingType(Long packagingTypeId, List<String> errors) {
        return packagingTypeRepository.findById(packagingTypeId).orElseGet(() -> {
            errors.add(ErrorCode.PACKAGING_TYPE_NOT_FOUND.formatMessage(packagingTypeId));
            return null;
        });
    }

    private Category fetchCategory(Long categoryId, List<String> errors) {
        return categoryRepository.findById(categoryId).orElseGet(() -> {
            errors.add(ErrorCode.CATEGORY_NOT_FOUND.formatMessage(categoryId));
            return null;
        });
    }

    public Page<ProductDto> getProductsByCategoryId(Long categoryId, PageRequest pageRequest) {
        return productRepository.findProductsByCategoryId(categoryId, pageRequest);
    }
}
