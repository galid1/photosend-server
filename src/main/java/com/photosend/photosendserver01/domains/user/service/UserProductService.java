package com.photosend.photosendserver01.domains.user.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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

        // 영속화 (유저 엔티티에 이미지 경로 추가)
        addProductImageUrlAndLocationToUser(userId, productLocations, productImageUrls);

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

    private void addProductImageUrlAndLocationToUser(long userId, ProductLocation[] productLocations, List<ProductImageUrl> productImageUrls) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다.")); // productImage url들을 추가할 UserEntity 얻어오기

        for(int i = 0; i < productImageUrls.size(); i++) {
            ProductEntity productEntity = productImageUrls.get(i).toEntity(productLocations[i], userEntity);
            productRepository.save(productEntity);
            userEntity.putProductImagePath(productEntity);
        }

        userRepository.save(userEntity);
    }

    // ProductImage Delete Method
    @Transactional
    public void deleteProductImage(Long userId, Long productId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));

        // 스토리지에서 이미지 제거
        String productImagePath = userEntity.getProductList().get(0).getProductImagePath();
        deleteImageFromStorage(productImagePath);

        // UserEntity가 가지는 productEntity 리스트에서 제거
        int productIndex = findProductIndexById(userId, productId);
        if(productIndex == -1)
            throw new ProductNotFoundException("해당 id의 ProductEntity가 존재하지 않습니다.");
        userEntity.deleteProduct(productIndex);
        userRepository.save(userEntity);
    }

    // TODO archive 로 변경하기 (나중에 이미지를 사용할 것)
    private void deleteImageFromStorage(String productImagePath) {
        // local file delete
        File file = new File(productImagePath);
        if(!file.exists())
            throw new FileDeleteException("경로에 파일이 존재하지 않습니다.");
        file.delete();
    }

    private int findProductIndexById(Long uid, Long pid) {
        UserEntity userEntity = userRepository.findById(uid)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다."));
        int index = 0;
        ProductEntity productEntity = null;

        for (ProductEntity entity : userEntity.getProductList()) {
            if(entity.getPid() == pid) {
                productEntity = entity;
                break;
            }
            index ++;
        }

        return productEntity != null ? index : -1;
    }

}
