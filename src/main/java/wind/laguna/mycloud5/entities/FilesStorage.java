package wind.laguna.mycloud5.entities;

import jakarta.persistence.*;
import jakarta.persistence.FetchType;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "files_storage")
public class FilesStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "file_title")
    private String fileTitle;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "file_content")
    @JdbcTypeCode(Types.BINARY)
    private byte[] fileContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserInfo userInfo;

    public FilesStorage(String fileTitle, Long fileSize, byte[] fileContent, UserInfo userInfo) {
        this.date = LocalDateTime.now();
        this.fileTitle = fileTitle;
        this.fileSize = fileSize;
        this.fileContent = fileContent;
        this.userInfo = userInfo;
    }
}
