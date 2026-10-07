package com.reg.rag_application.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RetrievalService {

    private final VectorStore vectorStore;

    public RetrievalService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public List<Document> retrieveRelevantChunks(String question) {

        SearchRequest searchRequest = SearchRequest.builder()
                .query(question)
                .topK(3)
                .build();

        List<Document> results =
                vectorStore.similaritySearch(searchRequest);

        // Remove duplicate chunks
        List<Document> uniqueResults = new ArrayList<>();
        Set<String> seenChunkIds = new HashSet<>();

        for (Document document : results) {

            String chunkId =
                    (String) document.getMetadata().get("chunkId");

            if (chunkId != null && seenChunkIds.add(chunkId)) {
                uniqueResults.add(document);
            }
        }

        return uniqueResults;
    }
}

