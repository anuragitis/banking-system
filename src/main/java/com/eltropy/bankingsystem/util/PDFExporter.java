package com.eltropy.bankingsystem.util;

import com.lowagie.text.Font;
import com.eltropy.bankingsystem.entity.AccountTransaction;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PDFExporter {

    private List<AccountTransaction> accountTransactionList;

    public PDFExporter(List<AccountTransaction> accountTransactionList) {
        this.accountTransactionList = accountTransactionList;
    }
    
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);
        Paragraph p = new Paragraph("Statement for Account : " + accountTransactionList.get(0).getAccountId() , font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();

    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Id", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("AccountId", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("TransactionDate", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("OpenningBalance", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("ClosingBalance", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("TransactionType", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (AccountTransaction accountTransaction : accountTransactionList) {
            table.addCell(String.valueOf(accountTransaction.getId()));
            table.addCell(String.valueOf(accountTransaction.getAccountId()));
            table.addCell(String.valueOf(accountTransaction.getTransactionDate()));
            table.addCell(String.valueOf(accountTransaction.getAmount()));
            table.addCell(String.valueOf(accountTransaction.getOpenningBalance()));
            table.addCell(String.valueOf(accountTransaction.getClosingBalance()));
            table.addCell(String.valueOf(accountTransaction.getType()));
        }
    }


}
