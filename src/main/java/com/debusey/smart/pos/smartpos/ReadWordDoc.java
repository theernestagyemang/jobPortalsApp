/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.debusey.smart.pos.smartpos;

/**
 * @author Admin
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.HeaderStories;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Admin
 */
public class ReadWordDoc {


    public static WordObject readMyDocument(String fileName) {
        POIFSFileSystem fs = null;
        try {
            fs = new POIFSFileSystem(new FileInputStream(fileName));
            HWPFDocument doc = new HWPFDocument(fs);

            /** Read the content **/
            readParagraphs(doc);

            int pageNumber = 1;

            /** We will try reading the header for page 1**/
            readHeader(doc, pageNumber);

            /** Let's try reading the footer for page 1**/
            readFooter(doc, pageNumber);

            /** Read the document summary**/
            return readDocumentSummary(doc);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void readParagraphs(HWPFDocument doc) throws Exception {
        WordExtractor we = new WordExtractor(doc);

        /**Get the total number of paragraphs**/
        String[] paragraphs = we.getParagraphText();

        for (int i = 0; i < paragraphs.length; i++) {

            System.out.println("Length of paragraph " + (i + 1) + ": " + paragraphs[i].length());
            System.out.println(paragraphs[i]);

        }

    }

    public static void readHeader(HWPFDocument doc, int pageNumber) {
        HeaderStories headerStore = new HeaderStories(doc);
        String header = headerStore.getHeader(pageNumber);
        System.out.println("Header Is: " + header);

    }

    public static void readFooter(HWPFDocument doc, int pageNumber) {
        HeaderStories headerStore = new HeaderStories(doc);
        String footer = headerStore.getFooter(pageNumber);
        System.out.println("Footer Is: " + footer);

    }

    public static WordObject readDocumentSummary(HWPFDocument doc) {
        System.out.println("heer...");
        DocumentSummaryInformation summaryInfo = doc.getDocumentSummaryInformation();

        String category = summaryInfo.getCategory();
        String company = summaryInfo.getCompany();

        int lineCount = summaryInfo.getLineCount();
        int sectionCount = summaryInfo.getSectionCount();
        int slideCount = summaryInfo.getSlideCount();

        System.out.println("---------------------------");
        System.out.println("Category: " + category);
        System.out.println("Company: " + company);
        System.out.println("Line Count: " + lineCount);
        System.out.println("Section Count: " + sectionCount);
        System.out.println("Slide Count: " + slideCount);

        return new WordObject(category, company, lineCount, sectionCount, slideCount);

    }

    public static List<String> readDocxFile(String fileName) {
        try {
            List<String> list = new ArrayList<>();
            File file = new File("uploads/" + fileName);
            if (!file.exists()) {
                return list;
            }
            //File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();


            paragraphs.forEach((para) -> {
                list.add(para.getText());
            });

            fis.close();
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
