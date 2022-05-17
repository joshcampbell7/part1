package edu.leicester.co2103.controller;

import edu.leicester.co2103.domain.Convenor;
import edu.leicester.co2103.domain.ErrorInfo;
import edu.leicester.co2103.domain.Module;
import edu.leicester.co2103.repo.ConvenorRepository;
import edu.leicester.co2103.repo.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ConvenorRestController {

    @Autowired
    ConvenorRepository convenorRepository;

    @Autowired
    ModuleRepository moduleRepository;




    @GetMapping("/convenors")
    public ResponseEntity<List<Convenor>> endpoint1() {

        List<Convenor> convenors = (List<Convenor>) convenorRepository.findAll();

        if (convenors.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Convenor>>(convenors, HttpStatus.OK);

    }

    @PostMapping("/convenors")
    public ResponseEntity<?> endpoint2(@RequestBody Convenor convenor, UriComponentsBuilder ucBuilder) {

        if (convenorRepository.existsById(convenor.getId())) {
            return new ResponseEntity<ErrorInfo>(new ErrorInfo("A convenor named " + convenor.getName() + " already exists."),
                    HttpStatus.CONFLICT);
        }
        convenorRepository.save(convenor);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/convenors/{id}").buildAndExpand(convenor.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/convenors/{id}")
    public ResponseEntity<?> endpoint3(@PathVariable("id") int id) {
        Convenor convenor = convenorRepository.findById((long) id).orElse(null);
        if (convenor == null) {
            return new ResponseEntity(new ErrorInfo("convenor with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Convenor>(convenor, HttpStatus.OK);

    }

    @PutMapping("/convenors/{id}")
    public ResponseEntity<?> endpoint4(@PathVariable("id") int id, @RequestBody Convenor newConvenor) {

        if (convenorRepository.findById((long) id).isPresent()) {
            Convenor currentConvenor = convenorRepository.findById((long) id).get();
            currentConvenor.setName(newConvenor.getName());
            currentConvenor.setPosition(newConvenor.getPosition());
            currentConvenor.setModules(newConvenor.getModules());


            currentConvenor.getModules().clear();
            currentConvenor.getModules().addAll(newConvenor.getModules());

            convenorRepository.save(currentConvenor);
            return new ResponseEntity<Convenor>(currentConvenor, HttpStatus.OK);
        } else
            return new ResponseEntity<ErrorInfo>(new ErrorInfo("convenor with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/convenors/{id}")
    public ResponseEntity<?> endpoint5(@PathVariable("id") long id) {
        convenorRepository.deleteById(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/convenors/{id}/modules")
    public ResponseEntity<?> endpoint6(@PathVariable("id") long id) {
        Convenor convenor = (Convenor) convenorRepository.findById(id).orElse(null);
        if (convenor == null) {
            return new ResponseEntity(new ErrorInfo("convenor with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(convenor.getModules(), HttpStatus.OK);

    }
}
