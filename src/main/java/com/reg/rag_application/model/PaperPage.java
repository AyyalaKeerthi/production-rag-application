package com.reg.rag_application.model;

public class PaperPage {

    private int pageNumber;
    private String text;

    public PaperPage(int pageNumber, String text) {
        this.pageNumber = pageNumber;
        this.text = text;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getText() {
        return text;
    }
}
