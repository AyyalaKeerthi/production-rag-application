package com.reg.rag_application.controller;

import com.reg.rag_application.model.AnswerResponse;
import com.reg.rag_application.model.QuestionRequest;
import com.reg.rag_application.service.AnswerGenerationService;
import com.reg.rag_application.service.ContextService;
import com.reg.rag_application.service.RetrievalService;
import org.springframework.ai.document.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final RetrievalService retrievalService;
    private final ContextService contextService;
    private final AnswerGenerationService answerGenerationService;

    public QuestionController(
            RetrievalService retrievalService,
            ContextService contextService,
            AnswerGenerationService answerGenerationService) {

        this.retrievalService = retrievalService;
        this.contextService = contextService;
        this.answerGenerationService = answerGenerationService;
    }

    @PostMapping("/search")
    public ResponseEntity<AnswerResponse> search(
            @RequestBody QuestionRequest request) {

        // 1. Retrieve relevant chunks
        List<Document> documents =
                retrievalService.retrieveRelevantChunks(
                        request.getQuestion()
                );

        // 2. Build context
        String context =
                contextService.buildContext(documents);

        // 3. Generate answer
        String answer =
                answerGenerationService.generateAnswer(
                        request.getQuestion(),
                        context
                );

        // 4. Build citations from retrieved document metadata
        List<AnswerResponse.Source> sources = new ArrayList<>();

        for (Document document : documents) {

            String paperName =
                    (String) document.getMetadata().get("paperName");

            Integer pageNumber =
                    (Integer) document.getMetadata().get("pageNumber");

            String chunkId =
                    (String) document.getMetadata().get("chunkId");

            sources.add(
                    new AnswerResponse.Source(
                            paperName,
                            pageNumber,
                            chunkId
                    )
            );
        }

        // 5. Return answer + sources
        AnswerResponse response =
                new AnswerResponse(answer, sources);

        return ResponseEntity.ok(response);
    }
}