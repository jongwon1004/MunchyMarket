package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.domain.Category;
import com.munchymarket.MunchyMarket.domain.Image;
import com.munchymarket.MunchyMarket.domain.PackagingType;
import com.munchymarket.MunchyMarket.domain.Product;
import com.munchymarket.MunchyMarket.repository.category.CategoryRepository;
import com.munchymarket.MunchyMarket.repository.image.ImageRepository;
import com.munchymarket.MunchyMarket.repository.packagingtype.PackagingTypeRepository;
import com.munchymarket.MunchyMarket.repository.product.ProductRepository;
import com.munchymarket.MunchyMarket.request.ProductRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public void registerProduct(ProductRequestDto productRequestDto) {


        PackagingType packagingType = packagingTypeRepository.findById(productRequestDto.getPackagingTypeId())
                .orElseThrow(() -> new IllegalArgumentException("該当する包装タイプがありません"));

        Category category = categoryRepository.findById(productRequestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("該当するカテゴリがありません"));

        Image mainImage = null;
        Image subImage = null;
        // GCS 에 이미지 등록
        try {

            // 상품등록때 main,sub 로 나뉘어서 이미지가 등록되니까,
            // uploadToGcs 파라미터를 List<MultipartFile> 이 아닌 각각 MultipartFile 로 받음
            Map<String, MultipartFile> images = productRequestDto.getImages();
            for (Map.Entry<String, MultipartFile> entry : images.entrySet()) {

                if ("mainImage".equals(entry.getKey())) {
                    mainImage = buildAndSaveImage(entry.getValue());
                } else if ("subImage".equals(entry.getKey())) {
                    subImage = buildAndSaveImage(entry.getValue());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload images", e);
        }

        Product product = productRequestDto.toEntity(category, packagingType, mainImage, subImage);
        productRepository.save(product);
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

}
