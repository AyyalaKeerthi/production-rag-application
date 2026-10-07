package com.reg.rag_application.service;

import com.reg.rag_application.model.PaperChunk;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PaperVectorService {

    private final VectorStore vectorStore;

    public PaperVectorService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void storeChunks(
            List<PaperChunk> chunks,
            String paperName) {

        List<Document> documents = new ArrayList<>();

        for (PaperChunk chunk : chunks) {

            Map<String, Object> metadata = Map.of(
                    "paperName", paperName,
                    "pageNumber", chunk.getPageNumber(),
                    "chunkId", chunk.getChunkId()
            );

            Document document = new Document(
                    chunk.getText(),
                    metadata
            );

            documents.add(document);
        }

        vectorStore.add(documents);
    }
}