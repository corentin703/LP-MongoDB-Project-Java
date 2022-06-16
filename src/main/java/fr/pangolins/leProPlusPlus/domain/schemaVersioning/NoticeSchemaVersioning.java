package fr.pangolins.leProPlusPlus.domain.schemaVersioning;

import fr.pangolins.leProPlusPlus.domain.entities.Notice;
import fr.pangolins.leProPlusPlus.repository.CompanyRepository;
import fr.pangolins.leProPlusPlus.repository.NoticeRepository;
import org.springframework.stereotype.Service;

@Service
public class NoticeSchemaVersioning implements EntitySchemaVersioning<Notice> {
    private final NoticeRepository noticeRepository;
    private final int LAST_SCHEMA_VERSION = 1;

    public NoticeSchemaVersioning(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public void run(Notice notice) {
        if (notice.getSchemaVersion() >= LAST_SCHEMA_VERSION) {
            return;
        }

        noticeRepository.deleteById(notice.getId());
        notice.setSchemaVersion(LAST_SCHEMA_VERSION);
        noticeRepository.insert(notice);
    }
}
