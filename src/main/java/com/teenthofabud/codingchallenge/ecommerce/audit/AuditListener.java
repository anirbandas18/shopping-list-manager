package com.teenthofabud.codingchallenge.ecommerce.audit;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component(value = "auditListener")
public class AuditListener {

    private Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("DEFAULT");
        }
        return Optional.of(authentication.getName());
    }

    @PrePersist
    public void setCreatedOn(Auditable auditable) {
        Audit audit = auditable.getAudit();

        if(audit == null) {
            audit = new Audit();
            auditable.setAudit(audit);
        }

        audit.setCreationTime(LocalDateTime.now());
        audit.setCreatedBy(getCurrentAuditor().get());
    }

    @PreUpdate
    public void setUpdatedOn(Auditable auditable) {
        Audit audit = auditable.getAudit();

        audit.setModificationTime(LocalDateTime.now());
        audit.setModifiedBy(getCurrentAuditor().get());
    }
}
