package com.example.toolsleasing.controllers;

import com.example.toolsleasing.model.CFruit;
import com.example.toolsleasing.repositories.IRepositoryFruits;
import com.example.toolsleasing.services.CServiceReport;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fruit_store")
public class CControllerFruits {

    @Autowired
    private IRepositoryFruits repositoryFruits;

    @Autowired
    private CServiceReport serviceReport;

    @GetMapping("/all_products")
    public List<CFruit> getAll() {
        return repositoryFruits.findAll();
    }

    @GetMapping("/{id}")
    //@GetMapping   /tools?id=100500,name=aodawdnaodn
    public Optional<CFruit> getById(@PathVariable Long id) {
        return repositoryFruits.findById(id);
    }

    @PostMapping("/add_products")
    public CFruit create(@RequestBody CFruit fruit) {
        return repositoryFruits.save(fruit);
    }

    @PutMapping("/update_product/{id}")
    public CFruit update(
            @PathVariable Long id,
            @RequestBody CFruit fruit)
    {
        fruit.setId(id);
        return repositoryFruits.save(fruit);
    }

    @DeleteMapping("/remove_product/{id}")
    public void delete(@PathVariable Long id) {
        repositoryFruits.deleteById(id);
    }

    @PostMapping(value = "/upload", consumes = {"*/*"})
    public String handleFileUpload(
            @RequestParam("file") MultipartFile file) {
        // TODO логика должна быть в отдельном классе
        try{
            Workbook wb = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            //HSSF - xls
            //XSSF - xlsx
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
                fruit = new CFruit(name, country, price);
                // TODO обернуть в транзакцию
                repositoryFruits.save(fruit);
            }
            repositoryFruits.flush();
        }
        catch (IOException e)
        {
            // TODO переопределить статус ошибки
            return "Ошибка!"+e.getMessage();
        }
        return "Загружено!";
    }

    @GetMapping(value = "/report/{report_number}")
    public ResponseEntity<ByteArrayResource> report(
            @PathVariable int report_number
    ) {
        byte[] report = serviceReport.createReport(report_number);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Report.docx");
        if (report.length>0)
            return new ResponseEntity<>(new ByteArrayResource(report),
                    header, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}