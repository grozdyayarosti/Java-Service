package com.example.toolsleasing.controllers;

import com.example.toolsleasing.model.CFruit;
import com.example.toolsleasing.repositories.IRepositoryFruits;
import com.example.toolsleasing.services.CServiceReport;
import com.example.toolsleasing.services.CServiceWorkbook;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    @Autowired
    private CServiceWorkbook serviceWorkbook;

    @GetMapping("/all_products")
    public List<CFruit> getAll()
    {
        return repositoryFruits.findAll();
    }

    @GetMapping("/{id}")
    public Optional<CFruit> getById(@PathVariable Long id)
    {
        return repositoryFruits.findById(id);
    }

    @PostMapping("/add_products")
    public CFruit create(@RequestBody CFruit fruit)
    {
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
    public void delete(@PathVariable Long id)
    {
        repositoryFruits.deleteById(id);
    }

    @PostMapping(value = "/upload", consumes = {"*/*"})
    public ResponseEntity<Integer> handleFileUpload(
            @RequestParam("file") MultipartFile file)
    {
        try {
            serviceWorkbook.uploadWorkbook(file);
        }
        catch (IOException e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
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

    @GetMapping("/get201")
    public ResponseEntity<Integer> get201() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/getJSON")
    public ResponseEntity<ObjectNode> getJSON() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode()
                .put("id", 1)
                .put("name", "Яблоко")
                .put("country", "Россия")
                .put("price", 150);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }
}