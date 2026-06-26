package ecomes.iteecomest.feature.file;

import ecomes.iteecomest.feature.file.dto.FileUploadResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    FileUploadResponse upload(MultipartFile file);
//    List<FileUploadResponse> uploadMultiFile(MultipartFile[] files);
    void deleteFile(String fileName);
    List<FileUploadResponse> uploadMultipleNew(List<MultipartFile> files);
    Page<FileUploadResponse> findAll(Integer page, Integer size);
    FileUploadResponse findByName(String fileName);
}
