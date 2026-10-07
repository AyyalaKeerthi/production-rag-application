package com.reg.rag_application.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AnswerGenerationService {

    private final ChatClient chatClient;

    public AnswerGenerationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generateAnswer(String question, String context) {

        String prompt = """
                You are a research paper assistant.

                Answer the user's question using ONLY the information
                provided in the research paper context below.

                If the context does not contain enough information to
                answer the question, say:
                "The provided research paper does not contain enough
                information to answer this question."

                Do not invent information.

                Research Paper Context:
                %s

                User Question:
                %s
                """.formatted(context, question);

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}