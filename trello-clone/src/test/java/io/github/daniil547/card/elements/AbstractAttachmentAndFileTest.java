package io.github.daniil547.card.elements;

import io.github.daniil547.AbstractIntegrationTest;
import io.github.daniil547.CardProvider;
import io.github.daniil547.card.CardConverter;
import io.github.daniil547.card.CardDto;
import io.github.daniil547.card.elements.attachment.AttachmentDto;
import io.github.daniil547.card.elements.attachment.File;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractAttachmentAndFileTest extends AbstractIntegrationTest<CardDto> { // all the interactions with attachments are performed through card's endpoint
    @Autowired
    CardProvider cardProvider;
    @Autowired
    CardConverter cardConverter;
    private CardDto cardDto;
    private UUID fileId;

    public AbstractAttachmentAndFileTest() {
        super("/cards/");
    }

    @BeforeAll
    public void init() {
        cardDto = cardConverter.dtoFromEntity(cardProvider.ensureExists());
    }


    @Test
    @Order(1)
    public void testFileUploadAndAttachmentCreation() throws Exception {
        byte[] content = getTestFileContent("test-file.txt");
        String fileName = "My favourite text file";
        String extension = "txt";

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", // seems it takes the name of the request param
                                                                    "", // isn't used
                                                                    extension, content);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .multipart("/files/")
                                                      .file(mockMultipartFile)
                                     ).andExpect(status().isCreated())
                                     .andExpect(content().contentType(APPLICATION_JSON))
                                     .andDo(MockMvcResultHandlers.log())
                                     .andReturn();
        UUID fileId = UUID.fromString(mvcResult.getResponse()
                                               .getContentAsString()
                                               .substring(1, 37));

        AttachmentDto attachmentDto = new AttachmentDto();
        attachmentDto.setCardId(cardDto.getId());
        attachmentDto.setName(fileName);
        attachmentDto.setFileContentId(fileId);
        attachmentDto.setExtension(extension);

        cardDto.setActive(true);
        cardDto.setAttachmentDtos(List.of(attachmentDto));

        cardDto = objectMapper.readValue(super.testUpdate(cardDto).getResponse().getContentAsString(), CardDto.class);
        this.fileId = cardDto.getAttachmentDtos().get(0).getFileContentId();
    }

    @Test
    @Order(2)
    public void testFileDownload() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .get("/files/" + this.fileId.toString())
                                     ).andExpect(status().isOk())
                                     .andExpect(content().contentType(APPLICATION_JSON))
                                     .andExpect(jsonPath("$.id", is(this.fileId.toString())))
                                     .andExpect(jsonPath("$.content", not(empty())))
                                     .andDo(MockMvcResultHandlers.log())
                                     .andReturn();

        File downloadedFile = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), File.class);
        byte[] originalFileContent = getTestFileContent("test-file.txt");
        assertArrayEquals(downloadedFile.getContent(), originalFileContent);
    }


    @Test
    @Order(3)
    public void testFileUpdate() throws Exception {
        byte[] content = getTestFileContent("UPD-test-file.txt");
        String fileName = "My NEW favourite text file";
        String extension = "txt";

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", // seems it takes the name of the request param
                                                                    "", // isn't used
                                                                    extension, content);

        mockMvc.perform(MockMvcRequestBuilders
                                .multipart("/files/" + this.fileId.toString())
                                .file(mockMultipartFile)
                                .with(req -> { // by default MockMVC doesn't support multipart requests other than POST
                                    req.setMethod("PUT");
                                    return req;
                                })
               ).andExpect(status().isOk())
               .andDo(MockMvcResultHandlers.log())
               .andReturn();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                                                      .get("/files/" + this.fileId.toString())
                                     ).andExpect(status().isOk())
                                     .andExpect(content().contentType(APPLICATION_JSON))
                                     .andExpect(jsonPath("$.id", is(this.fileId.toString())))
                                     .andExpect(jsonPath("$.content", not(empty())))
                                     .andDo(MockMvcResultHandlers.log())
                                     .andReturn();

        File downloadedFile = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), File.class);
        byte[] originalFileContent = getTestFileContent("UPD-test-file.txt");
        assertArrayEquals(downloadedFile.getContent(), originalFileContent);
    }

    @Test
    @Order(4)
    public void testFileDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                                .delete("/files/" + this.fileId.toString())
               ).andExpect(status().isNoContent())
               .andDo(MockMvcResultHandlers.log())
               .andReturn();

        mockMvc.perform(MockMvcRequestBuilders
                                .get("/files/" + this.fileId.toString())
               ).andExpect(status().isNotFound())
               .andDo(MockMvcResultHandlers.log());
    }

    private byte[] getTestFileContent(String name) throws URISyntaxException, IOException {
        Path from = Path.of(ClassLoader.getSystemResource(name).toURI());
        return Files.readAllBytes(from);
    }
}
