package fr.pangolins.leProPlusPlus.controller;

import fr.pangolins.leProPlusPlus.domain.entities.Company;
import fr.pangolins.leProPlusPlus.domain.entities.Notice;
import fr.pangolins.leProPlusPlus.domain.exception.entities.EntityNotFoundException;
import fr.pangolins.leProPlusPlus.domain.exception.entities.InvalidObjectIdException;
import fr.pangolins.leProPlusPlus.domain.schemaVersioning.NoticeSchemaVersioning;
import fr.pangolins.leProPlusPlus.repository.CompanyRepository;
import fr.pangolins.leProPlusPlus.requests.notices.CreateNoticeRequest;
import fr.pangolins.leProPlusPlus.requests.notices.EditNoticeRequest;
import fr.pangolins.leProPlusPlus.responses.NoticeResponse;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/companies/{companyId}/notices")
public class NoticesController {
    private final CompanyRepository companyRepository;
    private final NoticeSchemaVersioning noticeSchemaVersioning;

    public NoticesController(
        CompanyRepository companyRepository,
        NoticeSchemaVersioning noticeSchemaVersioning) {
        this.companyRepository = companyRepository;
        this.noticeSchemaVersioning = noticeSchemaVersioning;
    }

    /**
     * GetAll permet de recupérer l'ensemble des notices via des NoticeResponse
     * @return une ResponseEntity avec la liste des NoticeResponse
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<List<NoticeResponse>> getAll(
        @PathVariable(name = "companyId") String companyId
    ) {
        Company company = findCompanyByStrObjectId(companyId);
        List<Notice> notices = company.getNotices();
        notices.forEach(noticeSchemaVersioning::run);

        return new ResponseEntity<>(
                notices.stream().map(NoticeResponse::new).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    /**
     * GetById permet de recupérer une NoticeResponse suivant un id donné
     * @return une ResponseEntity avec la NoticeResponse associée
     * @param id l'id de la comany qu'on souhaite récupérer
     */
    @GetMapping("/{id}")
    public ResponseEntity<NoticeResponse> getById(
        @PathVariable(name = "companyId") String companyId,
        @PathVariable(name = "id") String id
    ){
        Notice notice = findNoticeByStrObjectId(companyId, id);

        return new ResponseEntity<>(
            new NoticeResponse(notice),
            HttpStatus.OK
        );
    }

    /**
     * GetByTitle permet de recupérer une notice via une noticeResponse suivant un tittre donné
     * @return une ResponseEntity avec la NoticeResponse correspondante au titre fourni
     * @param title le titre de la notice qu'on souhaite récupérer
     */
    @GetMapping("title/{title}")
    public ResponseEntity<NoticeResponse> getByTitle(
        @PathVariable(name = "companyId") String companyId,
        @PathVariable(name = "title") String title
    ){
        Notice notice;

        try {
            List<Notice> foundNotices = companyRepository.getNoticeByTitle(new ObjectId(companyId), title);

            if (foundNotices.size() == 0) {
                throw new EntityNotFoundException(title);
            }

            notice = foundNotices.get(0);
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(title);
        }

        return new ResponseEntity<>(
            new NoticeResponse(notice),
            HttpStatus.OK
        );
    }

    /**
     * Create permet de créer une notice suivant une notice request donnée
     * @return une ResponseEntity avec la NoticeResponse créée
     * @param request la request comprenant les informations d'une notice
     */
    @PostMapping
    public ResponseEntity<NoticeResponse> create(
        @PathVariable(name = "companyId") String companyId,
        @RequestBody CreateNoticeRequest request
    ) {
        Company company = findCompanyByStrObjectId(companyId);

        Notice newNotice = new Notice();
        newNotice.setId(new ObjectId());
        newNotice.setTitle(request.getTitle());
        newNotice.setContent(request.getContent());
        newNotice.setMark(request.getMark());
        newNotice.setAuthor(findCompanyByStrObjectId(request.getAuthorId()));

        List<Notice> notices = company.getNotices();
        if (notices == null)
            notices = new ArrayList<>();

        notices.add(newNotice);
        company.setNotices(notices);
        companyRepository.save(company);

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
        @PathVariable(name = "companyId") String companyId,
        @PathVariable(name = "id") String id,
        @RequestBody EditNoticeRequest request
    ) {
        Company company = findCompanyByStrObjectId(companyId);
        Notice notice = findNoticeByStrObjectId(companyId, id);

        if (request.getTitle() != null)
            notice.setTitle(request.getTitle());

        if (request.getAuthorId() != null)
        {
            notice.setAuthor(
                    findCompanyByStrObjectId(
                            request.getAuthorId()
                    )
            );
        }

        if (request.getContent() != null) {
            notice.setContent(request.getContent());
        }

        if (request.getMark() != null) {
            notice.setMark(request.getMark());
        }

        List<Notice> notices = company.getNotices()
                .stream()
                .filter(it -> !it.getId().toHexString().equals(id))
                .collect(Collectors.toList());

        notices.add(notice);
        company.setNotices(notices);
        companyRepository.save(company);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Delete permet de supprimer une notice
     * @param id identifiant de la notice à supprimer
     * @return une ResponseEntity avec le code de retour uniquement
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable(name = "companyId") String companyId,
        @PathVariable(name = "id") String id
    ) {
        Company company = findCompanyByStrObjectId(companyId);

        List<Notice> notices = company.getNotices()
            .stream()
            .filter(it -> !it.getId().toHexString().equals(id))
            .collect(Collectors.toList());

        if (company.getNotices().size() == notices.size()) {
            throw new EntityNotFoundException(id);
        }

        company.setNotices(notices);
        companyRepository.save(company);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Depuis un objectId on retourne une company
     * @param strObjId l'objectId de la company
     * @return une company
     */
    private Company findCompanyByStrObjectId(String strObjId) {
        try {
            ObjectId objectId = new ObjectId(strObjId);
            Optional<Company> company = companyRepository.findById(objectId);

            if (company.isEmpty()) {
                throw new EntityNotFoundException(strObjId);
            }

            return company.get();
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(strObjId);
        }
    }

    private Notice findNoticeByStrObjectId(String companyStrObjId, String noticeStrObjectId) {
        ObjectId companyId;
        ObjectId noticeId;

        try {
            companyId = new ObjectId(companyStrObjId);
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(companyStrObjId);
        }

        try {
            noticeId = new ObjectId(noticeStrObjectId);
        } catch (IllegalArgumentException e) {
            throw new InvalidObjectIdException(noticeStrObjectId);
        }

        List<Notice> foundNotices = companyRepository.getNoticeById(companyId, noticeId);

        if (foundNotices.size() == 0) {
            throw new EntityNotFoundException(noticeStrObjectId);
        }

        return foundNotices.get(0);
    }
}
