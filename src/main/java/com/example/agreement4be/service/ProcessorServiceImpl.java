package com.example.agreement4be.service;

import com.example.agreement4be.dto.PlaceholderLocationsDTO;
import com.example.agreement4be.dto.SnippetsDTO;
import com.example.agreement4be.dto.UpdatedSnippetsDTO;
import com.example.agreement4be.dto.UpdatedSnippetsWrapperDTO;
import com.example.agreement4be.util.DocumentProcessorUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Objects.nonNull;

@Service
public class ProcessorServiceImpl implements ProcessorService {

    @Override
    public List<SnippetsDTO> processFile(final MultipartFile file) throws IOException {
        final List<SnippetsDTO> snippets = new ArrayList<>();
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {
            final AtomicLong index = new AtomicLong(0);
            document.getParagraphs()
                    .forEach(paragraph -> {
                        String text = paragraph.getText();
                        if (text.contains("[")) {
                            snippets.add(new SnippetsDTO(index.getAndIncrement(), text));
                        }
                    });

            document.getTables()
                    .forEach(xwpfTable -> xwpfTable.getRows()
                            .forEach(tableRow -> tableRow.getTableCells()
                                    .forEach(rowCell -> rowCell.getParagraphs()
                                            .forEach(paragraph -> {
                                                if (paragraph.getText().contains("[")) {
                                                    snippets.add(new SnippetsDTO(index.getAndIncrement(), paragraph.getText()));
                                                }
                                            }))));
        }

        return snippets;
    }

    @Override
    public ByteArrayInputStream updateFile(final MultipartFile file, final UpdatedSnippetsWrapperDTO snippetsDTO) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             final FileOutputStream fileOutputStream = new FileOutputStream("document.docx");
             final XWPFDocument document = new XWPFDocument(file.getInputStream())) {
            final AtomicLong index = new AtomicLong(0);
            document.getParagraphs()
                    .forEach(paragraph -> {
                        if (paragraph.getText().contains("[")) {
                            final Optional<UpdatedSnippetsDTO> snippetsForParagraph = snippetsDTO.getUpdatedSnippets().stream()
                                    .filter(updatedSnippetsDTO -> updatedSnippetsDTO.getId() == index.get())
                                    .findFirst();
                            if (snippetsForParagraph.isPresent()) {
                                index.getAndIncrement();
                                final List<PlaceholderLocationsDTO> modifiedPositions = snippetsForParagraph.get().getPositions().stream()
                                        .filter(position -> nonNull(position.getNewPlaceholder()))
                                        .sorted(Comparator.comparing(PlaceholderLocationsDTO::getOldStartPosition).reversed())
                                        .toList();
                                modifiedPositions.forEach(position -> DocumentProcessorUtil.replace2(paragraph, position.getOldPlaceholder(), position.getNewPlaceholder(), position.getOldStartPosition().intValue()));
                            }
                        }
                    });

            document.getTables()
                    .forEach(xwpfTable -> xwpfTable.getRows()
                            .forEach(tableRow -> tableRow.getTableCells()
                                    .forEach(rowCell -> rowCell.getParagraphs()
                                            .forEach(paragraph -> {
                                                if (paragraph.getText().contains("[")) {
                                                    final Optional<UpdatedSnippetsDTO> snippetsForParagraph = snippetsDTO.getUpdatedSnippets().stream()
                                                            .filter(updatedSnippetsDTO -> updatedSnippetsDTO.getId() == index.get())
                                                            .findFirst();
                                                    if (snippetsForParagraph.isPresent()) {
                                                        index.incrementAndGet();
                                                        final List<PlaceholderLocationsDTO> modifiedPositions = snippetsForParagraph.get().getPositions().stream()
                                                                .filter(position -> nonNull(position.getNewPlaceholder()))
                                                                .sorted(Comparator.comparing(PlaceholderLocationsDTO::getOldStartPosition).reversed())
                                                                .toList();
                                                        modifiedPositions.forEach(position -> DocumentProcessorUtil.replace2(paragraph, position.getOldPlaceholder(), position.getNewPlaceholder(), position.getOldStartPosition().intValue()));
                                                    }
                                                }
                                            }))));
            document.write(fileOutputStream);
            document.write(outputStream);
            return new ByteArrayInputStream(outputStream.toByteArray());
        }
    }
}
