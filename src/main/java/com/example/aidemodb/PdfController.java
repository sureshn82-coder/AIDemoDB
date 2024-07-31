package com.example.aidemodb;

import com.example.aidemodb.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping("/upload")
    public String uploadPdf(@RequestParam("file") MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile("uploaded", ".pdf");
        file.transferTo(tempFile.toFile());
        String content = pdfService.extractTextFromPDF(tempFile.toString());
        pdfService.savePdfDocument(file.getOriginalFilename(), content);
        return "File uploaded and processed successfully";
    }

    @PostMapping("/process")
    public String processPdfContent(@RequestParam("query") String query) {
        return pdfService.processQuery(query);
    }
}

