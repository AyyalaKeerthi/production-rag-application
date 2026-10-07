package com.reg.rag_application.service;

import com.reg.rag_application.model.PaperPage;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaperService {

    public List<PaperPage> extractText(MultipartFile file) throws IOException {

        List<PaperPage> pages = new ArrayList<>();

        PDDocument document = Loader.loadPDF(file.getBytes());

        PDFTextStripper stripper = new PDFTextStripper();

        int totalPages = document.getNumberOfPages();

        for (int page = 1; page <= totalPages; page++) {

            stripper.setStartPage(page);
            stripper.setEndPage(page);

            String text = stripper.getText(document);

            pages.add(new PaperPage(page, text));
        }

        document.close();

        return pages;
    }
}