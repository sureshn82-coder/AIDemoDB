package com.example.aidemodb.service;

import com.example.aidemodb.PdfDocument;
import com.example.aidemodb.PdfDocumentRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PdfService {

    @Autowired
    private PdfDocumentRepository pdfDocumentRepository;

    @Autowired
    private OpenAIService openAIService;

    public String extractTextFromPDF(String filePath) throws IOException {
        PDDocument document = PDDocument.load(new File(filePath));
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();
        return text;
    }

    public void savePdfDocument(String name, String content) {
        PdfDocument pdfDocument = new PdfDocument();
        pdfDocument.setName(name);
        pdfDocument.setContent(content);
        pdfDocumentRepository.save(pdfDocument);
    }

    public List<PdfDocument> searchPdfDocuments(String keyword) {
        return pdfDocumentRepository.findByContentContaining(keyword);
    }

    public String processQuery(String query) {
        List<PdfDocument> documents = searchPdfDocuments(query);
        StringBuilder combinedContent = new StringBuilder();
        for (PdfDocument doc : documents) {
            combinedContent.append(doc.getContent()).append("\n");
        }
        return openAIService.callOpenAI(combinedContent.toString());
    }
}

