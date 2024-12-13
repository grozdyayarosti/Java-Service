package com.example.toolsleasing.services;
import com.example.toolsleasing.model.CReport2Item;
import com.example.toolsleasing.model.CReportItem;
import com.example.toolsleasing.repositories.IRepositoryFruits;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class CServiceReport {
    @Autowired
    private IRepositoryFruits repositoryFruits;
    public byte[] createReport(int report_number)  {
        try(XWPFDocument document = new XWPFDocument())
        {
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            run.setFontSize(16);
            run.setFontFamily("Times New Roman");
            run.setBold(true);
            XWPFTable table = document.createTable(1, 2);
            table.setWidth("100%");

            if (report_number == 1) {
                run.setText("Самые дорогие фрукты");
                addColumn(table, 0, "Фрукт");
                addColumn(table, 1, "Цена, р");
                List<CReportItem> items = repositoryFruits.topExpensiveFruits();
                XWPFTableRow row;
                for (CReportItem item : items) {
                    row = table.createRow();
                    fillCell(row, 0, item.getName());
                    fillCell(row, 1, item.getPrice().toString());
                }
            }
            else if (report_number == 2) {
                run.setText("Популярность стран-поставщиков");
                addColumn(table, 0, "Страна");
                addColumn(table, 1, "Кол-во поставляемых фруктов");
                List<CReport2Item> items = repositoryFruits.supplyPopularCountries();
                XWPFTableRow row;
                for (CReport2Item item : items) {
                    row = table.createRow();
                    fillCell(row, 0, item.getCountry());
                    fillCell(row, 1, item.getFruitsCount().toString());
                }
            }

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            document.write(stream);
            return stream.toByteArray();
        }
        catch(IOException ignored)
        {
            return new byte[0];
        }
    }

    private void addColumn(XWPFTable table, int cellNumber, String columnName) {
        XWPFParagraph p = table.getRow(0).getCell(cellNumber).getParagraphs().get(0);
        p.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r = p.createRun();
        r.setBold(true);
        r.setText(columnName);
    }

    private void fillCell(XWPFTableRow row, int cellNumber, String cellText)
    {
        XWPFTableCell cell;
        cell = row.getCell(cellNumber);
        cell.setText(cellText);
    }
}
