package com.easyacg.controller.image;

import com.easyacg.core.entity.ResponseVo;
import com.easyacg.image.entity.input.MigrateInput;
import com.easyacg.image.entity.input.ReadByStorageBo;
import com.easyacg.image.entity.input.UploadInput;
import com.easyacg.image.entity.output.ImageVo;
import com.easyacg.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 照片服务
 *
 * @author brahma
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/image")
public class ImageController {
    @Resource
    private ImageService imageService;

    /**
     * 读取照片
     *
     * @return 是否成功
     */
    @GetMapping("/read")
    @Operation(description = "根据存储策略名称，读取下面所有的图片")
    public ResponseVo<List<ImageVo>> readImage(@Valid @RequestBody ReadByStorageBo storageBo) {
        return ResponseVo.success(imageService.readImage(storageBo));
    }

    /**
     * 上传照片
     *
     * @return 是否成功
     */
    @PostMapping(value = "/upload")
    @Operation(description = "将本地图像上传到存储策略")
    public ResponseVo<Void> uploadImage(@RequestPart MultipartFile file, @RequestPart UploadInput input) {
        log.info("开始处理文件迁移请求 - 文件名: {}, 大小: {} bytes", file.getOriginalFilename(), file.getSize());
        imageService.uploadImage(file, input);
        return ResponseVo.success();
    }

    /**
     * 迁移照片
     *
     * @return 是否成功
     */
    @PostMapping(value = "/migrate")
    @Operation(description = "将图片从A迁移到B")
    public ResponseVo<Void> migrateImages(@RequestBody MigrateInput input) {
        imageService.migrateImages(input);
        return ResponseVo.success();
    }
}
