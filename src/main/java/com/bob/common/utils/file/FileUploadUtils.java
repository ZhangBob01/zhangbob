package com.bob.common.utils.file;

import com.bob.common.config.BobConfig;
import com.bob.common.constant.Constants;
import com.bob.common.exception.file.FileNameLengthLimitExceededException;
import com.bob.common.exception.file.FileSizeLimitExceededException;
import com.bob.common.exception.file.InvalidExtensionException;
import com.bob.common.utils.DateUtils;
import com.bob.common.utils.StringUtils;
import com.bob.common.utils.uuid.IdUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author: zhang bob
 * @date: 2021-06-17 13:04
 * @description: 上传文件工具类
 */
public class FileUploadUtils {
    /**
     * 默认文件大小最大值50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    /**
     * 默认文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir = BobConfig.getProfile();
    public static String getDefaultBaseDir() {
        return defaultBaseDir;
    }

    public static void setDefaultBaseDir(String defaultBaseDir) {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    /**
     * 文件上传
     * @param file
     * @return
     * @throws IOException
     */
    public static final String upload(MultipartFile file) throws IOException {
        return upload(getDefaultBaseDir(), file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
    }

    /**
     * 上传文件
     * @param defaultBaseDir
     * @param file
     * @param defaultAllowedExtension 允许上传的文件类型
     * @return
     * @throws IOException
     */
    public static String upload(String defaultBaseDir, MultipartFile file, String[] defaultAllowedExtension) throws IOException {
        int fileNameLength = file.getOriginalFilename().length();
        // 校验文件名称长度
        if (fileNameLength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }
        // 校验文件大小
        assertAllowed(file, defaultAllowedExtension);
        // 转换文件名
        String fileName = extracFileName(file);

        File desc = getAbsoluteFile(defaultBaseDir, fileName);

        file.transferTo(desc);
        String pathFileName = getPathFileName(defaultBaseDir, fileName);
        return pathFileName;
    }

    /**
     * 校验文件大小
     * @param file
     * @param defaultAllowedExtension
     */
    public static void assertAllowed(MultipartFile file, String[] defaultAllowedExtension)
            throws FileSizeLimitExceededException, InvalidExtensionException {
        // 获取文件大小
        long size = file.getSize();
        // 校验文件大小
        if (size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件扩展名
        String extension = getExtension(file);

        if (defaultAllowedExtension != null && !isAllowedExtension(extension, defaultAllowedExtension)) {
            if (defaultAllowedExtension == MimeTypeUtils.IMAGE_EXTENSION) {
                throw new InvalidExtensionException.InvalidImageExtensionException(defaultAllowedExtension, extension,
                        fileName);
            } else if (defaultAllowedExtension == MimeTypeUtils.FLASH_EXTENSION) {
                throw new InvalidExtensionException.InvalidFlashExtensionException(defaultAllowedExtension, extension,
                        fileName);
            } else if (defaultAllowedExtension == MimeTypeUtils.MEDIA_EXTENSION) {
                throw new InvalidExtensionException.InvalidMediaExtensionException(defaultAllowedExtension, extension,
                        fileName);
            } else if (defaultAllowedExtension == MimeTypeUtils.VIDEO_EXTENSION) {
                throw new InvalidExtensionException.InvalidVideoExtensionException(defaultAllowedExtension, extension,
                        fileName);
            } else {
                throw new InvalidExtensionException(defaultAllowedExtension, extension, fileName);
            }
        }
    }

    /**
     * 转换文件名
     * @param file
     * @return
     */
    public static String extracFileName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        fileName = DateUtils.getDatePath() + "/" + IdUtils.fastUUID() + "." + extension;
        return fileName;
    }

    /**
     * 获取文件名后缀
     * @param file
     * @return
     */
    public static String getExtension(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeUtils.getExtension(file.getContentType());
        }
        return extension;
    }

    private static final File getAbsoluteFile(String uploadDir, String fileName) throws IOException {
        File desc = new File(uploadDir + File.separator + fileName);

        if (!desc.exists()) {
            if (!desc.getParentFile().exists()) {
                desc.getParentFile().mkdirs();
            }
        }
        return desc;
    }

    private static final String getPathFileName(String uploadDir, String fileName) throws IOException {
        int dirLastIndex = BobConfig.getProfile().length() + 1;
        String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
        String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
        return pathFileName;
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static final boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据文件路径上传
     *
     * @param baseDir 相对应用的基目录
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException
     */
    public static final String upload(String baseDir, MultipartFile file) throws IOException
    {
        try
        {
            return upload(baseDir, file, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
        }
        catch (Exception e)
        {
            throw new IOException(e.getMessage(), e);
        }
    }

}
