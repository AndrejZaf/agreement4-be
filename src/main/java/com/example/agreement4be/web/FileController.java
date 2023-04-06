package com.example.agreement4be.web;

import com.example.agreement4be.dto.SnippetsDTO;
import com.example.agreement4be.dto.UpdatedSnippetsWrapperDTO;
import com.example.agreement4be.service.ProcessorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final ProcessorService processorService;

    @PostMapping
    public ResponseEntity<List<SnippetsDTO>> uploadFile(@RequestParam final MultipartFile file) throws IOException {
        final List<SnippetsDTO> processedSnippets = processorService.processFile(file);
        return new ResponseEntity<>(processedSnippets, HttpStatus.OK);
    }

    @PostMapping("/process")
    public ResponseEntity<InputStreamResource> processFile(@RequestParam final MultipartFile file, @RequestParam final String updatedSnippets, final HttpServletResponse response) throws IOException {
        // TODO: Find a way to resolve the DTO processing
        final UpdatedSnippetsWrapperDTO updatedSnippetsWrapperDTO = new ObjectMapper().readValue(updatedSnippets, UpdatedSnippetsWrapperDTO.class);
        final ByteArrayInputStream processedDocument = processorService.updateFile(file, updatedSnippetsWrapperDTO);
        return ResponseEntity.ok()
                .contentType(new MediaType("application", "vnd.openxmlformats-officedocument.wordprocessingml.document"))
                .header("Content-Disposition", file.getName())
                .body(new InputStreamResource(processedDocument));
    }
}
