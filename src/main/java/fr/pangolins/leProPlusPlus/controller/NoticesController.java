package fr.pangolins.leProPlusPlus.controller;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import fr.pangolins.leProPlusPlus.domain.entities.CompanyType;
import fr.pangolins.leProPlusPlus.domain.entities.Notice;
import fr.pangolins.leProPlusPlus.repository.CompanyRepository;
import fr.pangolins.leProPlusPlus.repository.NoticeRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/notices")
public class NoticesController {


    private final NoticeRepository noticeRepository;

    public NoticesController(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<Notice>> getAll() {

        List<Notice> notices = noticeRepository.findAll();
        return new ResponseEntity<>(notices, HttpStatus.OK);
    }
    //


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        return new ResponseEntity<>(noticeRepository.findById(new ObjectId(id)), HttpStatus.OK);
    }
//
    @PostMapping
    public ResponseEntity<?> create(@RequestParam("title") String title){
        Notice newNotice = new Notice();
        newNotice.setTitle(title);
        return new ResponseEntity<>(noticeRepository.insert(newNotice), HttpStatus.CREATED);
    }
//
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable String id,
            @RequestParam("title") String title) {


        Optional<Notice> notice = noticeRepository.findById(new ObjectId(id));
        if (notice.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Notice updatedNotice = notice.get();

        updatedNotice.setTitle(title);

        noticeRepository.save(updatedNotice);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){

        Optional<Notice> notice = noticeRepository.findById(new ObjectId(id));
        if (notice.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        noticeRepository.delete(notice.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
