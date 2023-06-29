package zipview_server.api;

import jdk.jfr.ContentType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import zipview_server.domain.ReviewImage;
import zipview_server.dto.review.ReviewImageDto;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileHandler {

    public List<ReviewImage> parseFileInfo(List<MultipartFile> multipartFiles) throws IOException {
        List<ReviewImage> fileList = new ArrayList<>();

        if (multipartFiles.isEmpty()) return fileList;

        if(!CollectionUtils.isEmpty(multipartFiles)) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String currentDate = now.format(dateTimeFormatter);
            String absolutePath = new File("").getAbsolutePath() + File.separator + File.separator;

            String path = "photo" + File.separator + currentDate;
            File file = new File(path);
            if(!file.exists()) {
                boolean wasSuccessful = file.mkdirs();
                // 디렉터리 생성에 실패했을 경우
                if(!wasSuccessful)
                    System.out.println("file: was not successful");
            }

            for(MultipartFile multipartFile : multipartFiles) {
                String originalFileExtension;
                String contentType = multipartFile.getContentType();
                if(ObjectUtils.isEmpty(contentType)) break;
                else {
                    if (contentType.contains("image/jpeg"))
                    originalFileExtension = ".jpg";

                    else {
                        if (contentType.contains("image/png"))
                            originalFileExtension = ".png";
                        else break;
                    }
                }
                String newFileName = System.nanoTime() + originalFileExtension;
                ReviewImage reviewImage = ReviewImage.builder()
                        .name(multipartFile.getOriginalFilename())
                        .path(path + File.separator + newFileName)
                        .fileSize(multipartFile.getSize())
                        .isHarm(false)
                        .build();
                fileList.add(reviewImage);

                file = new File(absolutePath + path + File.separator + newFileName);
                multipartFile.transferTo(file);

                file.setWritable(true);
                file.setReadable(true);

            }


        }

        return fileList;
    }
}
