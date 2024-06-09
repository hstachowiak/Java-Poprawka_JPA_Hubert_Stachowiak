package com.capgemini.wsb.persistence.dao.impl;

import com.capgemini.wsb.persistence.dao.DoctorDao;
import com.capgemini.wsb.persistence.entity.DoctorEntity;
import com.capgemini.wsb.persistence.entity.PatientEntity;
import com.capgemini.wsb.persistence.enums.Specialization;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DoctorDaoImpl extends AbstractDao<DoctorEntity, Long> implements DoctorDao {
    @Override
    public List<DoctorEntity> findBySpecialization(Specialization spec) { // Done
        TypedQuery<DoctorEntity> query = entityManager.createQuery("SELECT d FROM DoctorEntity d where d.specialization = :spec", DoctorEntity.class);
        query.setParameter("spec", spec);
        return query.getResultList();
    }

    @Override
    public long countNumOfVisitsWithPatient(String docFirstName, String docLastName, String patientFirstName, String patientLastName) { // DONE
        long count = entityManager
                .createQuery("SELECT COUNT(*)" +
                        "FROM VisitEntity visit " +
                        "JOIN visit.doctor doctor " +
                        "JOIN visit.patient patient " +
                        " WHERE doctor.firstName = :docFirstNameParam" +
                        " AND doctor.lastName = :docLastNameParam" +
                        " AND patient.firstName = :patientFirstNameParam" +
                        " AND patient.lastName = :patientLastNameParam", Long.class)
                .setParameter("docFirstNameParam", docFirstName)
                .setParameter("docLastNameParam", docLastName)
                .setParameter("patientFirstNameParam", patientFirstName)
                .setParameter("patientLastNameParam", patientLastName)
                .getSingleResult();

        return count;
    }
}
