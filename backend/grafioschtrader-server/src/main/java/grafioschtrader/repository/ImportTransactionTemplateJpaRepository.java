package grafioschtrader.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import grafioschtrader.entities.ImportTransactionTemplate;
import grafioschtrader.rest.UpdateCreateJpaRepository;

public interface ImportTransactionTemplateJpaRepository extends JpaRepository<ImportTransactionTemplate, Integer>,
    ImportTransactionTemplateJpaRepositoryCustom, UpdateCreateJpaRepository<ImportTransactionTemplate> {

  List<ImportTransactionTemplate> findByIdTransactionImportPlatformOrderByTemplatePurpose(
      Integer idTransactionImportPlatform);

  List<ImportTransactionTemplate> findByIdTransactionImportPlatformAndTemplateFormatTypeOrderByTemplatePurpose(
      Integer idTransactionImportPlatform, byte templateFormatType);

  @Query(value = "SELECT DISTINCT t.* FROM imp_trans_template t, imp_trans_pos p "
      + "WHERE t.id_trans_imp_template = p.id_trans_imp_template AND p.id_trans_head = ?1 AND p.id_tenant = ?2", nativeQuery = true)
  List<ImportTransactionTemplate> getImportTemplateByImportTransPos(Integer idTransactionHead, Integer idTenant);
}
