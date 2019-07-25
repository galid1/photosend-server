package com.photosend.photosendserver01.domains.user.service;

import com.photosend.photosendserver01.domains.user.domain.*;
import com.photosend.photosendserver01.domains.user.domain.exception.FileDeleteException;
import com.photosend.photosendserver01.domains.user.domain.exception.ProductException;
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
import java.util.Optional;

@Service
public class UserProductService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private FileUtil fileUtil;

    @Value("${file.upload-path.aws.prefix}")
    private String pathPrefix;

    public ProductFullInformation getProductInformation(String userId, Long productId) {
        ProductEntity findEntity = productRepository.findByPidAndUserWechatUidAndProductStateNot(productId, userId, ProductState.ORDERED);
        ProductInformation findEntityInformation = findEntity.getProductInformation();

        ProductFullInformation productFullInformation = ProductFullInformation.builder()
                .productImagePath(findEntity.getProductImagePath())
                .foundProductInformation(FoundProductInformation.builder()
                        .pid(findEntity.getPid())
                        .brand(findEntityInformation.getBrand())
                        .name(findEntityInformation.getName())
                        .price(findEntityInformation.getPrice())
                        .size(findEntityInformation.getSize())
                        .build())
                .build();

        return productFullInformation;
    }

    // Product의 이미지, 정보, 상태를 반환
    public List<ProductFullInformation> getAllProductInformation(String userId) {
        List<ProductEntity> productEntities = productRepository.findByUserWechatUidAndProductStateNot(userId, ProductState.ORDERED);

        List<ProductFullInformation> productFullInformationList = new ArrayList<>();

        productEntities.stream().forEach(v -> {
            productFullInformationList.add(ProductFullInformation.builder()
                    .productImagePath(v.getProductImagePath())
                    .foundProductInformation(FoundProductInformation.builder()
                                                                    .pid(v.getPid())
                                                                    .brand(v.getProductInformation().getBrand())
                                                                    .name(v.getProductInformation().getName())
                                                                    .price(v.getProductInformation().getPrice())
                                                                    .size(v.getProductInformation().getSize())
                                                                    .build())
                    .productState(v.getProductState())
                    .build());
        });

        return productFullInformationList;
    }

    @Transactional
    public List<ProductImageUrl> uploadProductImages(String userId, ProductLocation productLocation, MultipartFile[] productImageFiles) {
        List<ProductImageUrl> productImageUrls = new ArrayList<>();
        Optional<UserEntity> userEntity = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다.")));

        // 스토리지에 이미지 업로드
        uploadImageToStorage(userId, productImageFiles, productImageUrls);

        // 영속화 (유저 엔티티에 이미지 경로 추가)
        addProductImageUrlAndLocationToUser(userId, productLocation, productImageUrls);

        List<ProductImageUrl> returnProductImageUrlList = new ArrayList<>();
        userEntity.get()
                  .getProductList().stream().forEach(v -> {
                        returnProductImageUrlList.add(ProductImageUrl.builder()
                                                                     .pid(v.getPid())
                                                                     .productImageUrl(v.getProductImagePath())
                                                                     .build());
        });
        return returnProductImageUrlList;
    }

    private void uploadImageToStorage(String userId, MultipartFile[] productImageFiles, List<ProductImageUrl> productImageUrls) {
        for (MultipartFile file : productImageFiles){
            String uploadPath = fileUtil.makeFileUploadPath(userId, file.getOriginalFilename(), ImageType.PRODUCT);
            fileUtil.uploadFile(uploadPath, file);
            productImageUrls.add(ProductImageUrl.builder().productImageUrl(pathPrefix + uploadPath).build());
        }
    }

    private void addProductImageUrlAndLocationToUser(String userId, ProductLocation productLocation, List<ProductImageUrl> productImageUrls) {
        UserEntity userEntity = userRepository.findById(userId).get(); // productImage url들을 추가할 UserEntity 얻어오기

        productImageUrls.stream().forEach(v -> {
            ProductEntity productEntity = v.toEntity(productLocation, userEntity);
            productRepository.save(productEntity); // 새로 생성한 productEntity를 UserEntity의 ProductList에 저장하기 위해 우선 영속화시킴
            userEntity.putProductImagePath(productEntity);
        });

        userRepository.save(userEntity);
    }

    // ProductImage Delete Method
    @Transactional
    public void deleteProductImage(String userId, Long productId) {
        UserEntity userEntity = userRepository.findById(userId).get();

        // 스토리지에서 이미지 제거
        String productImagePath = userEntity.getProductList().get(0).getProductImagePath();
        deleteImageFromStorage(productImagePath);

        // UserEntity가 가지는 productEntity 리스트에서 제거
        int productIndex = findProductIndexById(userId, productId);
        if(productIndex == -1)
            throw new ProductException("해당 id의 ProductEntity가 존재하지 않습니다.");
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

    private int findProductIndexById(String uid, Long pid) {
        UserEntity userEntity = userRepository.findById(uid).get();
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
