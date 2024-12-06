package com.example.toolsleasing.services;

import com.example.toolsleasing.model.CFruit;
import com.example.toolsleasing.repositories.IRepositoryFruits;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class CServiceWorkbook {

    @Autowired
    private IRepositoryFruits repositoryFruits;

    public void uploadWorkbook(MultipartFile file) throws IOException {
        Workbook wb = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);

        int rows = sheet.getLastRowNum();
        Row row;
        long id;
        String name;
        String country;
        double price;
        CFruit fruit;
        for (int i=1; i<=rows; i++)
        {
            row = sheet.getRow(i);
            if (row==null)
                continue;
            id = (long)(row.getCell(0).getNumericCellValue());
            name = row.getCell(1).getStringCellValue();
            country = row.getCell(2).getStringCellValue();
            price = row.getCell(3).getNumericCellValue();
            fruit = new CFruit(id, name, country, price);
            repositoryFruits.save(fruit);
        }
        repositoryFruits.flush();
    }

}
