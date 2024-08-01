package wind.laguna.mycloud5.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wind.laguna.mycloud5.MyCloud5ApplicationTests;
import wind.laguna.mycloud5.requests.EditFileTitleRequest;
import wind.laguna.mycloud5.responces.FilesListResponse;
import wind.laguna.mycloud5.services.FilesStorageService;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class FilesControllerTest extends MyCloud5ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilesStorageService filesStorageService;

    @Test
    @WithMockUser
    public void testGetAllFiles() throws Exception {
        List<FilesListResponse> filesList = List.of(
                new FilesListResponse("file1", 100L),
                new FilesListResponse("file2", 200L));

//        Mockito.when(filesStorageService.getFilesList(any(Integer.class))).thenReturn(filesList);
        Mockito.when(filesStorageService.getFilesList(10)).thenReturn(filesList);

        mockMvc.perform(MockMvcRequestBuilders.get("/list")
                        .param("limit", "10")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer mockToken"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fileTitle").value("file1"))
                .andExpect(jsonPath("$[1].fileTitle").value("file2"));
    }

    @Test
    @WithMockUser
    public void testUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "filename.txt", MediaType.TEXT_PLAIN_VALUE, "file content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/file")
                        .file(file)
                        .param("filename", "filename.txt"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testDownloadFile() throws Exception {
        byte[] fileContent = "file content".getBytes();
        Mockito.when(filesStorageService.downloadFile(anyString())).thenReturn(fileContent);

        mockMvc.perform(MockMvcRequestBuilders.get("/file")
                        .param("filename", "filename.txt"))
                .andExpect(status().isOk())
                .andExpect(content().bytes(fileContent));
    }

    @Test
    @WithMockUser
    public void testEditFileTitle() throws Exception {
        EditFileTitleRequest request = new EditFileTitleRequest("newTitle");
        String jsonRequest = "{\"fileTitle\": \"newTitle\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/file")
                        .param("filename", "filename.txt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testDeleteFile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/file")
                        .param("filename", "filename.txt"))
                .andExpect(status().isOk());
    }
}
