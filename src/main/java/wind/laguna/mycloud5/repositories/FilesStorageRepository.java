package wind.laguna.mycloud5.repositories;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wind.laguna.mycloud5.entities.FilesStorage;
import wind.laguna.mycloud5.entities.UserInfo;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilesStorageRepository extends JpaRepository<FilesStorage, Long> {

    List<FilesStorage> findAllByUserInfo(UserInfo userInfo, Limit limit);

    Optional<FilesStorage> findByUserInfoAndFileTitle(UserInfo userInfo, String fileTitle);

    void deleteByUserInfoAndFileTitle(UserInfo userInfo, String fileTitle);
}
