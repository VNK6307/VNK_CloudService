package wind.laguna.mycloud5.responces;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilesListResponse {
    private String fileTitle;
    private Long fileSize;

    public String getFilename() {
        return fileTitle;
    }
}
