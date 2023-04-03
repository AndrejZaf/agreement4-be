package com.example.agreement4be.service;

import com.example.agreement4be.dto.SnippetsDTO;
import com.example.agreement4be.dto.UpdatedSnippetsWrapperDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface ProcessorService {
    List<SnippetsDTO> processFile(MultipartFile file) throws IOException;

    ByteArrayInputStream updateFile(MultipartFile file, UpdatedSnippetsWrapperDTO snippetsDTO) throws IOException;
}
