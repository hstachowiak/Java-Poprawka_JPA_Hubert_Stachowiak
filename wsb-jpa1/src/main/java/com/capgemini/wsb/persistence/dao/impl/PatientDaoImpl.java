package com.capgemini.wsb.persistence.dao.impl;

import com.capgemini.wsb.persistence.dao.PatientDao;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.entity.VisitEntity;
import com.capgemini.wsb.persistence.enums.TreatmentType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @Override
    public List<PatientEntity> findByDoctor(String docFirstName, String docLastName) { //Done
        return entityManager.createQuery(
                        "SELECT DISTINCT p " +
                                "FROM VisitEntity v " +
                                "JOIN v.patient p " +
                                "JOIN v.doctor d " +
                                "WHERE d.firstName = :docFirstName " +
                                "AND d.lastName = :docLastName", PatientEntity.class)
                .setParameter("docFirstName", docFirstName)
                .setParameter("docLastName", docLastName)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsHavingTreatmentType(TreatmentType treatType) { //Done
        return entityManager.createQuery(
                        "SELECT DISTINCT p " +
                                "FROM VisitEntity v " +
                                "JOIN v.patient p " +
                                "JOIN v.medicalTreatments t " +
                                "WHERE t.type = :treatType", PatientEntity.class)
                .setParameter("treatType", treatType)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsSharingSameLocationWithDoc(String docFirstName, String docLastName) { //Done
        return entityManager.createQuery(
                        "SELECT DISTINCT p " +
                                "FROM PatientEntity p " +
                                "JOIN p.addresses patientAddr " +
                                "WHERE EXISTS (" +
                                "   SELECT 1 FROM DoctorEntity d " +
                                "   JOIN d.addresses docAddr " +
                                "   WHERE d.firstName = :docFirstName " +
                                "   AND d.lastName = :docLastName " +
                                "   AND docAddr = patientAddr" +
                                ")", PatientEntity.class)
                .setParameter("docFirstName", docFirstName)
                .setParameter("docLastName", docLastName)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithoutLocation() { //Done
        return entityManager.createQuery(
                        "SELECT DISTINCT p FROM PatientEntity p " +
                                "LEFT JOIN p.addresses addr " +
                                "WHERE addr IS NULL", PatientEntity.class)
                .getResultList();
    }
}
