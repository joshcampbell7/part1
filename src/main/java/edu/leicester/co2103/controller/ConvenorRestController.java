package edu.leicester.co2103.controller;

import edu.leicester.co2103.domain.Convenor;
import edu.leicester.co2103.repo.ConvenorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ConvenorRestController {

    @Autowired
    ConvenorRepository convenorRepository;

    @GetMapping("/convenors")
    public ResponseEntity<List<Convenor>> endpoint1() {

        List<Convenor> convenors = (List<Convenor>) convenorRepository.findAll();

        if (convenors.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Convenor>>(convenors, HttpStatus.OK);

    }


//    @GetMapping("/convenors/{id}")
//    public ResponseEntity<?> endpoint3(@PathVariable("id")int id){
//        Convenor convenor =convenorRepository.findById(id).orElse(null);
//        if(convenor ==null){
//            return new ResponseEntity(new ErrorInfo("Hotel with id "+id +" not found"),HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<Convenor>(convenor,HttpStatus.OK);
//
//
//    }
//
//    @PostMapping("/api/convenors/{id}")
//    public String addConvenor(){
//
//    }
//
//    @PutMapping("/api/convenors/{id}")
//    public String updateConvenorByID(){
//
//    }
//
//    @DeleteMapping("/api/convenors/{id}")
//    public String deleteConvenorByID(){
//
//    }
//
//    @GetMapping("/api/convenors/{id}/modules")
//    public String retrieveModuleByConvenor(){
//
//    }



}
