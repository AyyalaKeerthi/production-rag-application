package com.reg.rag_application.model;

public class PaperChunk {

    private String chunkId;
    private int pageNumber;
    private String text;

    public PaperChunk(String chunkId, int pageNumber, String text) {
        this.chunkId = chunkId;
        this.pageNumber = pageNumber;
        this.text = text;
    }

    public String getChunkId() {
        return chunkId;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getText() {
        return text;
    }
}
