package wind.laguna.mycloud5.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import wind.laguna.mycloud5.requests.EditFileTitleRequest;
import wind.laguna.mycloud5.responces.FilesListResponse;
import wind.laguna.mycloud5.services.FilesStorageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FilesController {

    private final FilesStorageService filesStorageService;

    @GetMapping("/list")
    public List<FilesListResponse> getAllFiles(@RequestParam("limit") Integer limit) {
        return filesStorageService.getFilesList(limit);
    }

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam("filename")
                                        String fileTitle, MultipartFile file) {
        filesStorageService.uploadFile(fileTitle, file);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/file")
    public ResponseEntity<Resource> downloadFile(@RequestParam("filename") String filename) {
        byte[] file = filesStorageService.downloadFile(filename);
        return ResponseEntity.ok().body(new ByteArrayResource(file));
    }

    @PutMapping(value = "/file")
    public ResponseEntity<?> editFileTitle(@RequestParam("filename") String filename,
                                           @RequestBody EditFileTitleRequest editFileTitleRequest) {
        filesStorageService.editFileTitle(filename, editFileTitleRequest);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestParam("filename") String filename) {
        filesStorageService.deleteFile(filename);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
