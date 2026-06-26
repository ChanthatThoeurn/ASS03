package ecomes.iteecomest.feature.file.dto;

import lombok.Builder;

@Builder
public record FileUploadResponse(
        String name,
        String caption,
        String extension,
        Long size,
        String mediaType,
        String uri,
        String downLoadUri
) {
}
