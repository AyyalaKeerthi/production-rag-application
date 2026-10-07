package com.reg.rag_application.service;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContextService {

    public String buildContext(List<Document> documents) {

        StringBuilder context = new StringBuilder();

        int sourceNumber = 1;

        for (Document document : documents) {

            context.append("SOURCE ")
                    .append(sourceNumber++)
                    .append("\n");

            context.append("Paper: ")
                    .append(document.getMetadata().get("paperName"))
                    .append("\n");

            context.append("Page: ")
                    .append(document.getMetadata().get("pageNumber"))
                    .append("\n");

            context.append("Chunk: ")
                    .append(document.getMetadata().get("chunkId"))
                    .append("\n\n");

            context.append(document.getText())
                    .append("\n\n");

            context.append("--------------------------------\n\n");
        }

        return context.toString();
    }
}