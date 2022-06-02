package fr.pangolins.leProPlusPlus.controller;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import fr.pangolins.leProPlusPlus.domain.entities.CompanyType;
import fr.pangolins.leProPlusPlus.domain.entities.Notice;
import fr.pangolins.leProPlusPlus.domain.exception.entities.EntityNotFoundException;
import fr.pangolins.leProPlusPlus.domain.exception.entities.InvalidObjectIdException;
import fr.pangolins.leProPlusPlus.repository.CompanyRepository;
import fr.pangolins.leProPlusPlus.repository.NoticeRepository;
import fr.pangolins.leProPlusPlus.responses.CompanyResponse;
import fr.pangolins.leProPlusPlus.responses.NoticeResponse;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/notices")
public class NoticesController {


    private final NoticeRepository noticeRepository;

    public NoticesController(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<NoticeResponse>> getAll() {
        List<Notice> notices = noticeRepository.findAll();

        return new ResponseEntity<>(
                notices.stream().map(NoticeResponse::new).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }
    //


    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse> getById(@PathVariable String id) {
        Optional<Notice> notice;

        try {
            notice = noticeRepository.findById(new ObjectId(id));
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(id);
        }

        if (notice.isEmpty())
            throw new EntityNotFoundException(id);

        return new ResponseEntity<>(
                new NoticeResponse(notice.get()),
                HttpStatus.OK
        );
    }
//
    @PostMapping
    public ResponseEntity<NoticeResponse> create(@RequestParam("title") String title) {
        Notice newNotice = new Notice();
        newNotice.setTitle(title);

        newNotice = noticeRepository.insert(newNotice);

        return new ResponseEntity<>(
                new NoticeResponse(newNotice),
                HttpStatus.CREATED
        );
    }
//
    @PutMapping("/{id}")
    public ResponseEntity<NoticeResponse> update(
            @PathVariable String id,
            @RequestParam("title") String title
    ) {
        Optional<Notice> notice;

        try {
            notice = noticeRepository.findById(new ObjectId(id));
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(id);
        }

        if (notice.isEmpty()) {
            throw new EntityNotFoundException(id);
        }

        Notice updatedNotice = notice.get();

        updatedNotice.setTitle(title);

        noticeRepository.save(updatedNotice);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
//
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        Optional<Notice> notice;

        try {
            notice = noticeRepository.findById(new ObjectId(id));
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(id);
        }

        if (notice.isEmpty()) {
            throw new EntityNotFoundException(id);
        }

        noticeRepository.delete(notice.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
