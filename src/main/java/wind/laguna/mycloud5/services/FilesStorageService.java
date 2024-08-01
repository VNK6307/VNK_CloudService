package wind.laguna.mycloud5.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wind.laguna.mycloud5.entities.FilesStorage;
import wind.laguna.mycloud5.entities.UserInfo;
import wind.laguna.mycloud5.exceptions.InputDataException;
import wind.laguna.mycloud5.exceptions.ServerException;
import wind.laguna.mycloud5.repositories.FilesStorageRepository;
import wind.laguna.mycloud5.repositories.UserInfoRepository;
import wind.laguna.mycloud5.requests.EditFileTitleRequest;
import wind.laguna.mycloud5.responces.FilesListResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilesStorageService {

    private final UserInfoRepository userInfoRepository;
    private final FilesStorageRepository filesStorageRepository;

    public void uploadFile(String fileTitle, MultipartFile file) {
        try {
            var userInfo = getUserFromSecurityContext();
            filesStorageRepository.save(new FilesStorage(fileTitle, file.getSize(), file.getBytes(), userInfo));
        } catch (InputDataException exception) {
            throw exception;
        } catch (Exception e) {
            throw new ServerException("Error occurred while uploading file", e.getMessage());
        }
    }

    private UserInfo getUserFromSecurityContext() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var user = userInfoRepository.findByUsername((String) username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new InputDataException(String.format("User with username %s is not found", username));
        }
    }

    public List<FilesListResponse> getFilesList(Integer limit) {
        try {
            var user = getUserFromSecurityContext();
            var storages = filesStorageRepository.findAllByUserInfo(user, Limit.of(limit));
            return storages.stream()
                    .map(o -> new FilesListResponse(o.getFileTitle(), o.getFileSize()))
                    .collect(Collectors.toList());
        } catch (InputDataException exception) {
            throw exception;
        } catch (Exception e) {
            throw new ServerException("Can't get file list because of error.", e.getMessage());
        }
    }

    public byte[] downloadFile(String filename) {
        try {
            var file = getFileByFilename(filename);
            return file.getFileContent();
        } catch (InputDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerException("Error occurred while downloading file", e.getMessage());
        }
    }

    private FilesStorage getFileByFilename(String filename) throws Exception {
        var user = getUserFromSecurityContext();
        var file = filesStorageRepository.findByUserInfoAndFileTitle(user, filename);
        if (file.isPresent()) {
            return file.get();
        } else {
            throw new InputDataException(String.format("File with filename %s is not found", filename));
        }
    }

    public void editFileTitle(String filename, EditFileTitleRequest editFileTitleRequest) {
        try {
            var file = getFileByFilename(filename);
            file.setFileTitle(editFileTitleRequest.getFileTitle());
            filesStorageRepository.save(file);
        } catch (InputDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerException("Error occurred while editing file", e.getMessage());
        }
    }

    @Transactional
    public void deleteFile(String filename) {
        try {
            var user = getUserFromSecurityContext();
            getFileByFilename(filename);
            filesStorageRepository.deleteByUserInfoAndFileTitle(user, filename);
        } catch (InputDataException e) {
            throw e;
        } catch (Exception e) {
            throw new ServerException("Error occurred while deleting file", e.getMessage());
        }
    }
}

