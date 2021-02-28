package com.terzo.ab.repository;

import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.terzo.ab.model.Employee;

public class AssesmentEntityListener {
    private static Log log = LogFactory.getLog(AssesmentEntityListener.class);
    
    @PrePersist
    @PreUpdate
    @PreRemove
    private void beforeAnyUpdate(Employee employee) {
        if (employee.getId() == null) {
            log.info("[employee AUDIT] About to add a employee");
        } else {
            log.info("[employee AUDIT] About to update/delete employee: " + employee.getId());
        }
    }
    
    @PostPersist
    @PostUpdate
    @PostRemove
    private void afterAnyUpdate(Employee employee) {
        log.info("[employee AUDIT] add/update/delete complete for employee: " + employee.getId());
    }
    
    @PostLoad
    private void afterLoad(Employee employee) {
        log.info("[employee AUDIT] employee loaded from database: " + employee.getId());
    }
}