package fr.pangolins.leProPlusPlus.controller;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import fr.pangolins.leProPlusPlus.domain.entities.Notice;
import fr.pangolins.leProPlusPlus.domain.exception.entities.EntityNotFoundException;
import fr.pangolins.leProPlusPlus.domain.exception.entities.InvalidObjectIdException;
import fr.pangolins.leProPlusPlus.domain.schemaVersioning.NoticeSchemaVersioning;
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
    private final NoticeSchemaVersioning noticeSchemaVersioning;

    public NoticesController(
        NoticeRepository noticeRepository,
        CompanyRepository companyRepository,
        NoticeSchemaVersioning noticeSchemaVersioning) {
        this.noticeRepository = noticeRepository;
        this.companyRepository = companyRepository;
        this.noticeSchemaVersioning = noticeSchemaVersioning;
    }

    /**
     * GetAll permet de recupérer l'ensemble des notices via des NoticeResponse
     * @return une ResponseEntity avec la liste des NoticeResponse
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<NoticeResponse>> getAll() {
        List<Notice> notices = noticeRepository.findAll();
        notices.forEach(noticeSchemaVersioning::run);

        return new ResponseEntity<>(
                notices.stream().map(NoticeResponse::new).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    //

    /**
     * GetById permet de recupérer une NoticeResponse suivant un id donné
     * @return une ResponseEntity avec la NoticeResponse associée
     * @param id l'id de la comany qu'on souhaite récupérer
     */
    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse> getById(@PathVariable String id){
        Optional<Notice> notice;

        try {
            notice = noticeRepository.findById(new ObjectId(id));
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(id);
        }

        if (notice.isEmpty())
            throw new EntityNotFoundException(id);

        noticeSchemaVersioning.run(notice.get());

        return new ResponseEntity<>(
                new NoticeResponse(notice.get()),
                HttpStatus.OK
        );
    }
    /**
     * GetByTitle permet de recupérer une notice via une noticeResponse suivant un tittre donné
     * @return une ResponseEntity avec la NoticeResponse correspondante au titre fourni
     * @param title le titre de la notice qu'on souhaite récupérer
     */
    @GetMapping("title/{title}")
    public ResponseEntity<NoticeResponse> getByTitle(@PathVariable String title){
        Optional<Notice> notice;

        try {
            notice = noticeRepository.findById(new ObjectId(title));
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(title);
        }

        if (notice.isEmpty())
            throw new EntityNotFoundException(title);

        noticeSchemaVersioning.run(notice.get());

        return new ResponseEntity<>(
                new NoticeResponse(notice.get()),
                HttpStatus.OK
        );
    }

    /**
     * Create permet de créer une notice suivant une notice request donnée
     * @return une ResponseEntity avec la NoticeResponse créée
     * @param request la request comprenant les informations d'une notice
     */
    @PostMapping
    public ResponseEntity<NoticeResponse> create(@RequestBody CreateNoticeRequest request) {
        Notice newNotice = new Notice();
        newNotice.setTitle(request.getTitle());

        newNotice = noticeRepository.insert(newNotice);

        return new ResponseEntity<>(
                new NoticeResponse(newNotice),
                HttpStatus.CREATED
        );
    }

    /**
     * Update permet de mettre à jour une notice
     * @param id identifiant de la notice
     * @param request contient les informations de la notice à mettre à jour
     * @return une ResponseEntity avec le code de retour uniquement
     */
    @PutMapping("/{id}")
    public ResponseEntity<NoticeResponse> update(
        @PathVariable String id,
        @RequestBody EditNoticeRequest request
    ) {
        Optional<Notice> notice;

        try {
            notice = noticeRepository.findById(new ObjectId(id));
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(id);
        }

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

        noticeSchemaVersioning.run(updatedNotice);
        noticeRepository.save(updatedNotice);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete permet de supprimer une notice
     * @param id identifiant de la notice à supprimer
     * @return une ResponseEntity avec le code de retour uniquement
     */
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

    /**
     * Depuis un objectId on retourne une company
     * @param strObjId l'objectId de la company
     * @return une company
     */
    private Company findCompanyByStrObjectId(String strObjId) {
        ObjectId objectId = new ObjectId(strObjId);
        Optional<Company> company = companyRepository.findById(objectId);

        if (company.isEmpty()) {
            throw new EntityNotFoundException(strObjId);
        }

        return company.get();
    }
}
