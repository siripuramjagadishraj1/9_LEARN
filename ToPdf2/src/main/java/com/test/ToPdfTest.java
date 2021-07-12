package com.test;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ToPdfTest {

    public static final Rectangle myPage = PageSize.A4.rotate();

    public static void main(String[] args) throws  Exception{
        String inPutFileName = "C:/Users/Jagadish_Siripuram/Desktop/LEARN/DEMO.csv";
        String outPutFileName = "C:/itextExamples/sample2.pdf";
        String csvDescription = "FT203 AUTOPAY, AUTOPAY EXPRESS ";
        String csvHeader[] = null;
        String trailer = null;

        List<String> bodyLies = Files.readAllLines(Paths.get(inPutFileName));
        csvDescription = bodyLies.get(0);
        csvHeader = bodyLies.get(1).split(",");
        trailer = bodyLies.get(bodyLies.size()-1);
        bodyLies.remove(bodyLies.size()-1);
        bodyLies.remove(0);
        bodyLies.remove(0);



        Document document =
                new Document(myPage,
                20f,20f,10f,20f);
        PdfWriter.getInstance(document, new FileOutputStream(outPutFileName));
        document.open();
        PdfPTable table = new PdfPTable(csvHeader.length);
        table.setWidthPercentage(100);
        Stream.of(csvHeader)
                .forEach(columnTitle -> {
                    Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN,10,BaseColor.BLACK);
                    font.setStyle(Font.BOLD);
                    PdfPCell header = new PdfPCell();
                    header.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
        bodyLies.forEach(e->{
            Arrays.stream(e.split(",")).forEach(item->{
                insertToCell(table, item.trim());
            });
        });
        Font csvDescriptor = FontFactory.getFont(FontFactory.TIMES_ROMAN,15,BaseColor.BLACK);
        csvDescriptor.setStyle(Font.BOLD);
        document.add(new Paragraph(csvDescription, csvDescriptor));
        document.add(new Paragraph("\n"));
        document.add(table);
        document.add(new Paragraph(trailer, csvDescriptor));
        document.close();
    }

    private static void insertToCell(PdfPTable table, String text) {
        PdfPCell eachCell = new PdfPCell();
        eachCell.setBackgroundColor(BaseColor.WHITE);
        eachCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN,10,BaseColor.BLACK);
        Phrase phrase = new Phrase(text,font);
        eachCell.setPhrase(phrase);
        table.addCell(eachCell);
    }

}
/*
Heading THis is the first
sid, sname, class, marks
1,Jagadish,5,40
2,Jagadish,9,53
3,Jagadish,8,20
4,Jagadish,7,12
The tailer is here.
 */