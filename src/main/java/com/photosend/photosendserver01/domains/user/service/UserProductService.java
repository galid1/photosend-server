package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.common.event.EmailEvent;
import com.photosend.photosendserver01.domains.user.domain.*;
import com.photosend.photosendserver01.domains.user.domain.exception.FileDeleteException;
import com.photosend.photosendserver01.domains.user.domain.exception.ProductNotFoundException;
import com.photosend.photosendserver01.domains.user.domain.exception.UserNotFoundException;
import com.photosend.photosendserver01.domains.user.infra.file.ImageType;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.FoundProductInformation;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.ProductFullInformation;
import com.photosend.photosendserver01.domains.user.presentation.request_reponse.ProductImageUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserProductService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Value("${photosend.aws.s3.upload-path.prefix}")
    private String pathPrefix;

    public ProductFullInformation getProductInformation(Long userId, Long productId) {
        ProductEntity findEntity = productRepository.findByPidAndUserUserIdAndProductStateNot(productId, userId, ProductState.ORDERED);
        ProductInformation findEntityInformation = findEntity.getProductInformation();

        ProductFullInformation productFullInformation = ProductFullInformation.builder()
                .productId(findEntity.getPid())
                .productImagePath(findEntity.getProductImagePath())
                .foundProductInformation(FoundProductInformation.builder()
                        .brand(findEntityInformation.getBrand())
                        .name(findEntityInformation.getName())
                        .price(findEntityInformation.getPrice())
                        .size(findEntityInformation.getSize())
                        .build())
                .build();

        return productFullInformation;
    }

    // Product의 이미지, 정보, 상태를 반환
    public List<ProductFullInformation> getAllProductInformation(Long userId) {
        List<ProductEntity> productEntities = productRepository.findByUserUserIdAndProductStateNot(userId, ProductState.ORDERED);
        List<ProductFullInformation> productFullInformationList = new ArrayList<>();

        productEntities.stream().forEach(v -> {
            ProductFullInformation.ProductFullInformationBuilder productFullInformationBuilder = ProductFullInformation.builder()
                    .productId(v.getPid())
                    .productImagePath(v.getProductImagePath())
                    .productState(v.getProductState());

            if(v.getProductInformation() != null) {
                productFullInformationBuilder.foundProductInformation(FoundProductInformation.builder()
                        .brand(v.getProductInformation().getBrand())
                        .name(v.getProductInformation().getName())
                        .price(v.getProductInformation().getPrice())
                        .size(v.getProductInformation().getSize())
                        .build());
            }

            productFullInformationList.add(productFullInformationBuilder.build());
        });

        return productFullInformationList;
    }

    @Transactional
    public List<ProductImageUrl> uploadProductImages(long userId, ProductLocation[] productLocations, MultipartFile[] productImageFiles) {
        verifyProductLocationsAndProductImageFilesCount(productLocations.length, productImageFiles.length);
        List<ProductImageUrl> productImageUrls = new ArrayList<>();
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));

        // 스토리지에 이미지 업로드
        uploadImageToStorage(userId, productImageFiles, productImageUrls);

        // 이메일 전송 이벤트 발생
        publishEmailEvent(Long.toString(userId), productImageFiles[0]);

        // 영속화 (유저 엔티티에 이미지 경로 추가)
        addProduct(userId, productLocations, productImageUrls);

        List<ProductImageUrl> returnProductImageUrlList = new ArrayList<>();
        userEntity.getProductList().stream().forEach(v -> {
                        returnProductImageUrlList.add(ProductImageUrl.builder()
                                                                     .pid(v.getPid())
                                                                     .productImageUrl(v.getProductImagePath())
                                                                     .build());
        });
        return returnProductImageUrlList;
    }

    private void verifyProductLocationsAndProductImageFilesCount(int productLocationsSize, int productImageFilesSize) {
        if(productLocationsSize != productImageFilesSize)
            throw new IllegalArgumentException("Product 위치리스트와 이미지리스트의 개수가 일치하지 않습니다.");
    }

    private void uploadImageToStorage(long userId, MultipartFile[] productImageFiles, List<ProductImageUrl> productImageUrls) {
        for (MultipartFile file : productImageFiles){
            String uploadPath = fileUtil.makeFileUploadPath(userId, file.getOriginalFilename(), ImageType.PRODUCT);
            fileUtil.uploadFile(uploadPath, file);
            productImageUrls.add(ProductImageUrl.builder().productImageUrl(pathPrefix + uploadPath).build());
        }
    }

    private void addProduct(long userId, ProductLocation[] productLocations, List<ProductImageUrl> productImageUrls) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다.")); // productImage url들을 추가할 UserEntity 얻어오기

        for(int i = 0; i < productImageUrls.size(); i++) {
            ProductEntity productEntity = ProductEntity.builder()
                    .productImagePath(productImageUrls.get(i).getProductImageUrl())
                    .productLocation(productLocations[i])
                    .userEntity(userEntity)
                    .build();
            productRepository.save(productEntity);
            userEntity.addProduct(productEntity);
        }

        userRepository.save(userEntity);
    }

    private void publishEmailEvent(String userName, MultipartFile multipartFile) {
        eventPublisher.publishEvent(new EmailEvent(userName, multipartFile));
    }

    @Transactional
    public void addProductFromCatalog(long userId, long productId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));

        ProductEntity productFoundFromCatalog = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("존재하지 않는 상품입니다."));

        ProductEntity productEntity = ProductEntity.builder()
                                        .productImagePath(productFoundFromCatalog.getProductImagePath())
                                        .userEntity(productFoundFromCatalog.getUser())
                                        .build();
        productEntity.putProductInformation(productFoundFromCatalog.getProductInformation());
        productRepository.save(productEntity);

        userEntity.addProduct(productEntity);
    }

}
