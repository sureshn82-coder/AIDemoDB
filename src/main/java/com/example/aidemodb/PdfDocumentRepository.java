package com.example.aidemodb;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PdfDocumentRepository extends JpaRepository<PdfDocument, Long> {
    List<PdfDocument> findByContentContaining(String keyword);
}
