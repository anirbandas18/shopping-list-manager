package com.teenthofabud.codingchallenge.ecommerce.audit;

public interface Auditable {

    public Audit getAudit();

    public void setAudit(Audit audit);
}