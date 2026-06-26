package ecomes.iteecomest.feature.file;
import ecomes.iteecomest.feature.file.dto.FileUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final FileUploadRepository fileUploadRepository;
    private final FileUploadMapper fileUploadMapper;
    @Value("${file.storage-location}")
    private String fileLocation;



    @Override
    public FileUploadResponse findByName(String fileName) {
        return fileUploadRepository.findByName(fileName)
                .map(fileUploadMapper::mapFileUploadToFileResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File has not been found"));
    }

    @Override
    public Page<FileUploadResponse> findAll(Integer page, Integer size) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(page, size, sortById);

        Page<FileUpload> fileUploadResponses = fileUploadRepository.findAll(pageRequest);

        return fileUploadResponses.map(fileUploadMapper::mapFileUploadToFileResponse);
    }

    @Override
    public FileUploadResponse upload(MultipartFile file) {
        // file information
        String fileName = UUID.randomUUID().toString();
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null || !originalFileName.contains(".")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid file name"
            );
        }
        String ext = originalFileName.substring(
                originalFileName.lastIndexOf(".") + 1
        );

//        fileName += "." + ext;
        Path path = Paths.get(fileLocation + fileName + ext);

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File has been failed to upload");
        }
        FileUpload fileUpload = new FileUpload();
        fileUpload.setName(originalFileName);
        fileUpload.setExtension(ext);
        fileUpload.setCaption("Image Upload");
        fileUpload.setSize(file.getSize());
        fileUploadRepository.save(fileUpload);
        return fileUploadMapper.mapFileUploadToFileResponse(fileUpload);
    }

    @Override
    public List<FileUploadResponse> uploadMultipleNew(List<MultipartFile> files) {
        return files.stream()
                .map(this::saveFile)
                .collect(Collectors.toList());
    }
//
//    @Override
//    public List<FileUploadResponse> uploadMultiFile(MultipartFile[] files) {
//        List<FileUploadResponse> responses = new ArrayList<>();
//        for (MultipartFile file : files) {
//            String originalName = file.getOriginalFilename();
//            if (originalName == null || !originalName.contains(".")) {
//                throw new ResponseStatusException(
//                        HttpStatus.BAD_REQUEST,
//                        "Invalid file name"
//                );
//            }
//            String ext = originalName.substring(
//                    originalName.lastIndexOf(".") + 1
//            );
//
//            String fileName = UUID.randomUUID() + "." + ext;
//
//            Path path = Paths.get(fileLocation, fileName);
//
//            try {
//                Files.createDirectories(path.getParent());
//                Files.copy(file.getInputStream(), path,
//                        StandardCopyOption.REPLACE_EXISTING);
//                responses.add(
//                        FileUploadResponse.builder()
//                                .name(fileName)
//                                .size(file.getSize())
//                                .mediaType(file.getContentType())
//                                .uri(fileLocation + fileName)
//                                .build()
//                );
//
//            } catch (IOException e) {
//                throw new ResponseStatusException(
//                        HttpStatus.INTERNAL_SERVER_ERROR,
//                        "Failed to upload file: " + originalName
//                );
//            }
//        }
//
//        return responses;
//    }
    private FileUploadResponse saveFile(MultipartFile file) {
        // Prepare file information
        // File name
        String name = UUID.randomUUID().toString();

        // mypro.file.png
        String ext = file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);

        // Create absolute path to store file
        Path path = Paths.get(fileLocation+"/" + name + "." + ext);

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "File has been failed to upload");
        }
        // Save information file into db
        FileUpload fileUpload = new FileUpload();
        fileUpload.setName(name);
        fileUpload.setExtension(ext);
        fileUpload.setCaption("ISTAD - Advanced IT Institute in Cambodia");
        fileUpload.setSize(file.getSize());
        fileUpload.setMediaType(file.getContentType());
        fileUploadRepository.save(fileUpload);

        return fileUploadMapper.mapFileUploadToFileResponse(fileUpload);
    }

    @Override
    public void deleteFile(String fileName) {
        FileUpload fileUpload = fileUploadRepository.findByName(fileName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File has not been found"));
        fileUploadRepository.delete(fileUpload);
        // Create absolute path to store file
        Path path = Paths.get(fileLocation + fileUpload.getName() + "." + fileUpload.getExtension());
        try {
            boolean isExisted = Files.deleteIfExists(path);
            if (!isExisted)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File has not been found");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File has been failed to delete");
        }
    }


//        Path path = Paths.get(fileLocation + fileName);
//        try{
//            boolean deleted = Files.deleteIfExists(path);
//            if(!deleted){
//                throw new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        "File not found");
//            }
//        }catch (IOException e){
//            throw new ResponseStatusException(
//                    HttpStatus.INTERNAL_SERVER_ERROR,
//                    "Failed to delete file: " + fileName
//            );
//        }
//
//    }

}
