package fastvagas.jpa;

import fastvagas.data.entity.CrowlerLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CrowlerLogRepository extends JpaRepository<CrowlerLog, Integer>, CrowlerLogCustomRepository {

}
