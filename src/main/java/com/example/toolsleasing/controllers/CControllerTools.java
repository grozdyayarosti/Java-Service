package com.example.toolsleasing.controllers;

import com.example.toolsleasing.model.CTool;
import com.example.toolsleasing.repositories.IRepositoryTools;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/tools")
public class CControllerTools {

    @Autowired
    private IRepositoryTools repositoryTools;

    @GetMapping("/tools")
    public List<CTool> getAll() {
        return repositoryTools.findAll();
    }

    @GetMapping("/{id}") // /tools/100500
    //@GetMapping   /tools?id=100500,name=aodawdnaodn
    // @RequestParam
    public Optional<CTool> getById(@PathVariable Long id) {
        return repositoryTools.findById(id);
    }

    @PostMapping
    public CTool create(@RequestBody CTool tool) {
        return repositoryTools.save(tool);
    }

    @PutMapping("/{id}")
    public CTool update(
            @PathVariable Long id,
            @RequestBody CTool student)
    {
        return repositoryTools.save(student);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repositoryTools.deleteById(id);
    }
}