package ecomes.iteecomest.feature.file;

import ecomes.iteecomest.feature.file.dto.FileUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileUploadMapper {
    @Value("${file.base-uri}")
    private String baseUri;

    public FileUploadResponse mapFileUploadToFileResponse(FileUpload fileUpload) {
        return FileUploadResponse.builder()
                .name(fileUpload.getName())
                .extension(fileUpload.getExtension())
                .size(fileUpload.getSize())
                .mediaType(fileUpload.getMediaType())
                .uri(baseUri + "/" +fileUpload.getName() + "." + fileUpload.getExtension())
                .build();

    }
}
