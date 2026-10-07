package com.reg.rag_application.controller;


import com.reg.rag_application.model.PaperChunk;
import com.reg.rag_application.model.PaperPage;
import com.reg.rag_application.service.ChunkingService;
import com.reg.rag_application.service.PaperService;
import com.reg.rag_application.service.PaperVectorService;
import com.reg.rag_application.service.EmbeddingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/papers")
public class PaperController {

    private final PaperService paperService;
    private final ChunkingService chunkingService;
    private final EmbeddingService embeddingService;
    private final PaperVectorService paperVectorService;

   public PaperController(
        PaperService paperService,
        ChunkingService chunkingService,
        EmbeddingService embeddingService,
        PaperVectorService paperVectorService) {

    this.paperService = paperService;
    this.chunkingService = chunkingService;
    this.embeddingService = embeddingService;
    this.paperVectorService = paperVectorService;
}
@GetMapping("/embedding-test")
public ResponseEntity<?> testEmbedding() {

    String text =
            "Transformer based methods are increasingly used "
            + "for medical image segmentation.";

    float[] embedding =
            embeddingService.generateEmbedding(text);

    return ResponseEntity.ok(embedding);
}
    @PostMapping("/upload")
    public ResponseEntity<?> uploadPaper(
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("Research paper cannot be empty");
        }

        try {

            List<PaperPage> pages =
                    paperService.extractText(file);
System.out.println("Pages extracted: " + pages.size());
            List<PaperChunk> chunks =
                    chunkingService.createChunks(pages);
                    paperVectorService.storeChunks(
        chunks,
        file.getOriginalFilename()
);
System.out.println("Chunks created: " + chunks.size());
           return ResponseEntity.ok(
        "Research paper processed and stored successfully"
);

        } catch (IOException e) {

            return ResponseEntity.internalServerError()
                    .body("Failed to process research paper");
        }
    }
}