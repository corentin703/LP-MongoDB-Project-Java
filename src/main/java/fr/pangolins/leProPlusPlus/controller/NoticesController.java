package fr.pangolins.leProPlusPlus.controller;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import fr.pangolins.leProPlusPlus.domain.entities.Notice;
import fr.pangolins.leProPlusPlus.domain.exception.entities.EntityNotFoundException;
import fr.pangolins.leProPlusPlus.domain.exception.entities.InvalidObjectIdException;
import fr.pangolins.leProPlusPlus.repository.CompanyRepository;
import fr.pangolins.leProPlusPlus.repository.NoticeRepository;
import fr.pangolins.leProPlusPlus.requests.notices.CreateNoticeRequest;
import fr.pangolins.leProPlusPlus.requests.notices.EditNoticeRequest;
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
    private final CompanyRepository companyRepository;

    public NoticesController(
        NoticeRepository noticeRepository,
        CompanyRepository companyRepository
    ) {
        this.noticeRepository = noticeRepository;
        this.companyRepository = companyRepository;
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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateNoticeRequest request) {
        Notice newNotice = new Notice();
        newNotice.setTitle(request.getTitle());
        return new ResponseEntity<>(noticeRepository.insert(newNotice), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
        @PathVariable String id,
        @RequestBody EditNoticeRequest request
    ) {
        Optional<Notice> notice = noticeRepository.findById(new ObjectId(id));
        if (notice.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Notice updatedNotice = notice.get();

        if (request.getTitle() != null)
            updatedNotice.setTitle(request.getTitle());

        if (request.getAuthorId() != null)
        {
            updatedNotice.setAuthor(
                findCompanyByStrObjectId(
                    request.getAuthorId()
                )
            );
        }

        if (request.getContent() != null) {
            updatedNotice.setContent(request.getContent());
        }

        if (request.getMark() != null) {
            updatedNotice.setMark(request.getMark());
        }

        noticeRepository.save(updatedNotice);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

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

    private Company findCompanyByStrObjectId(String strObjId) {
        ObjectId objectId = new ObjectId(strObjId);
        Optional<Company> company = companyRepository.findById(objectId);

        if (company.isEmpty()) {
            throw new EntityNotFoundException(strObjId);
        }

        return company.get();
    }
}
