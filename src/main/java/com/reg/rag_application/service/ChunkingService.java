package com.reg.rag_application.service;

import com.reg.rag_application.model.PaperChunk;
import com.reg.rag_application.model.PaperPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkingService {

    private static final int CHUNK_SIZE = 700;
    private static final int OVERLAP = 100;

    public List<PaperChunk> createChunks(List<PaperPage> pages) {

        List<PaperChunk> chunks = new ArrayList<>();

        int chunkCounter = 1;

        for (PaperPage page : pages) {

            String text = page.getText();

            if (text == null || text.isBlank()) {
                continue;
            }

            String[] words = text.split("\\s+");

            int start = 0;

            while (start < words.length) {

                int end = Math.min(
                        start + CHUNK_SIZE,
                        words.length
                );

                StringBuilder chunkText = new StringBuilder();

                for (int i = start; i < end; i++) {
                    chunkText.append(words[i]).append(" ");
                }

                String chunkId = "paper-chunk-" + chunkCounter++;

                chunks.add(
                        new PaperChunk(
                                chunkId,
                                page.getPageNumber(),
                                chunkText.toString().trim()
                        )
                );

                if (end == words.length) {
                    break;
                }

                start = end - OVERLAP;
            }
        }

        return chunks;
    }
}
