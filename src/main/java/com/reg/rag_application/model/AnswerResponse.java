package com.reg.rag_application.model;

import java.util.List;

public class AnswerResponse {

    private String answer;
    private List<Source> sources;

    public AnswerResponse(String answer, List<Source> sources) {
        this.answer = answer;
        this.sources = sources;
    }

    public String getAnswer() {
        return answer;
    }

    public List<Source> getSources() {
        return sources;
    }

    public static class Source {

        private String paperName;
        private int pageNumber;
        private String chunkId;

        public Source(String paperName, int pageNumber, String chunkId) {
            this.paperName = paperName;
            this.pageNumber = pageNumber;
            this.chunkId = chunkId;
        }

        public String getPaperName() {
            return paperName;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public String getChunkId() {
            return chunkId;
        }
    }
}