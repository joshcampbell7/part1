package edu.leicester.co2103.controller;

import edu.leicester.co2103.domain.Convenor;
import edu.leicester.co2103.domain.ErrorInfo;
import edu.leicester.co2103.domain.Module;
import edu.leicester.co2103.domain.Session;
import edu.leicester.co2103.repo.ConvenorRepository;
import edu.leicester.co2103.repo.ModuleRepository;
import edu.leicester.co2103.repo.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ModuleRestController {

    @Autowired
    ConvenorRepository convenorRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    SessionRepository sessionRepository;

    @GetMapping("/modules")
    public ResponseEntity<List<Module>> endpoint7() {

        List<Module> modules = (List<Module>) moduleRepository.findAll();

        if (modules.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<Module>>(modules, HttpStatus.OK);

    }

    @PostMapping("/modules")
    public ResponseEntity<?> endpoint8(@RequestBody Module module, UriComponentsBuilder ucBuilder) {

        if (moduleRepository.existsById(module.getCode())) {
            return new ResponseEntity<ErrorInfo>(new ErrorInfo("A module with the code " + module.getCode() + " already exists."),
                    HttpStatus.CONFLICT);
        }
        moduleRepository.save(module);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/modules/{code}").buildAndExpand(module.getCode()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/modules/{code}")
    public ResponseEntity<?> endpoint9(@PathVariable("code") String code) {
        Module module = moduleRepository.findById(code).orElse(null);
        if (module == null) {
            return new ResponseEntity(new ErrorInfo("module with code " + code + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Module>(module, HttpStatus.OK);

    }

    @PatchMapping("/modules/{code}")
    public ResponseEntity<?> endpoint10(@PathVariable("code") String code, @RequestBody Module newModule) {

        if (moduleRepository.findById(code).isPresent()) {
            Module currentModule = moduleRepository.findById(code).get();
            currentModule.setTitle(newModule.getTitle());
            currentModule.setLevel(newModule.getLevel());
            //here should go the optional constructor
            currentModule.setSessions(newModule.getSessions());


            currentModule.getSessions().clear();
            currentModule.getSessions().addAll(newModule.getSessions());

            moduleRepository.save(currentModule);
            return new ResponseEntity<Module>(currentModule, HttpStatus.OK);
        } else
            return new ResponseEntity<ErrorInfo>(new ErrorInfo("module with code " + code + " not found."),
                    HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/modules/{code}")
    public ResponseEntity<?> endpoint11(@PathVariable("code") String code) {
        moduleRepository.deleteById(code);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/modules/{code}/sessions")
    public ResponseEntity<?> endpoint12(@PathVariable("code") String code) {
        Module module = (Module) moduleRepository.findById(code).orElse(null);
        if (module == null) {
            return new ResponseEntity(new ErrorInfo("module with code " + code + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(module.getSessions(), HttpStatus.OK);

    }

    @PostMapping("/modules/{code}/sessions")
    public ResponseEntity<?> endpoint13(@PathVariable("code") String code, @RequestBody Session session, UriComponentsBuilder ucBuilder) {

        Module module = (Module) moduleRepository.findById(code).orElse(null);

        if (module == null) {
            return new ResponseEntity(new ErrorInfo("module with code " + code + " not found"), HttpStatus.NOT_FOUND);
        }

        if (sessionRepository.existsById(session.getId())) {
            return new ResponseEntity<ErrorInfo>(new ErrorInfo("A session with the code " + session.getId() + " already exists."),
                    HttpStatus.CONFLICT);
        }
        sessionRepository.save(session);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/modules/{code}/sessions").buildAndExpand(session.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/modules/{code}/sessions/{id}")
    public ResponseEntity<?> endpoint14(@PathVariable("code") String code, @PathVariable("id") long id) {
        Module module = (Module) moduleRepository.findById(code).orElse(null);
        Session session = (Session) sessionRepository.findById(id).orElse(null);
        if (module == null) {
            return new ResponseEntity(new ErrorInfo("module with code " + code + " not found"), HttpStatus.NOT_FOUND);
        }
        if (session == null) {
            return new ResponseEntity(new ErrorInfo("session with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(session, HttpStatus.OK);
    }

    @PutMapping("/modules/{code}/sessions/{id}")
    public ResponseEntity<?> endpoint15Put(@PathVariable("id") int id, @RequestBody Convenor newConvenor) {

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

    @PatchMapping("/modules/{code}/session/{id}")
    public ResponseEntity<?> endpoint15Patch(@PathVariable("code") String code, @PathVariable("id") long id, @RequestBody Module newModule) {

        if (moduleRepository.findById(code).isPresent()) {
            Module currentModule = moduleRepository.findById(code).get();
            currentModule.setTitle(newModule.getTitle());
            currentModule.setLevel(newModule.getLevel());
            //here should go the optional constructor
            currentModule.setSessions(newModule.getSessions());


            currentModule.getSessions().clear();
            currentModule.getSessions().addAll(newModule.getSessions());

            moduleRepository.save(currentModule);
            return new ResponseEntity<Module>(currentModule, HttpStatus.OK);
        } else
            return new ResponseEntity<ErrorInfo>(new ErrorInfo("module with code " + code + " not found."),
                    HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/modules/{code}/sessions/{id}")
    public ResponseEntity<?> endpoint16(@PathVariable("code") String code, @PathVariable("id") long id) {
        sessionRepository.deleteById(id);
        return ResponseEntity.ok(null);
    }





}


