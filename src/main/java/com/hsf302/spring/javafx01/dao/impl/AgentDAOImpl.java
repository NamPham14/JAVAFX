package com.hsf302.spring.javafx01.dao.impl;

import com.hsf302.spring.javafx01.dao.AgentDAO;
import com.hsf302.spring.javafx01.entity.Agent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AgentDAOImpl implements AgentDAO {
    private EntityManagerFactory entityManagerFactory;
    public AgentDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    @Override
    public List<Agent> selectAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("SELECT a FROM Agent a");
            List<Agent> list = query.getResultList();
            entityManager.getTransaction().commit();
            return list;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Agent selectByEmail(String email) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("SELECT a FROM Agent a WHERE a.email = :email", Agent.class);
            query.setParameter("email", email);
            List<Agent> list = query.getResultList();
            entityManager.getTransaction().commit();
            return list.isEmpty() ? null : list.get(0);
        }catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi kiểm tra email: " + email, e);
        }
        finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(int agentId) {}

    @Override
    public void save(Agent agent) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(agent);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi lưu đại lý", e);
        } finally {
            entityManager.close();
        }
    }
    @Override
    public List<Agent> findAgents(String email, String agentName, String status) {
        StringBuilder queryStr = new StringBuilder("SELECT a FROM Agent a WHERE 1=1");
        if (email != null && !email.trim().isEmpty()) {
            queryStr.append(" AND a.email LIKE :email");
        }
        if (agentName != null && !agentName.trim().isEmpty()) {
            queryStr.append(" AND a.agentName LIKE :agentName");
        }
        if (status != null && !status.trim().isEmpty()) {
            queryStr.append(" AND a.status LIKE :status"); // Sử dụng LIKE cho status
        }
        queryStr.append(" ORDER BY a.registerDate ASC, a.agentName ASC");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Agent> query = entityManager.createQuery(queryStr.toString(), Agent.class);
            if (email != null && !email.trim().isEmpty()) {
                query.setParameter("email", "%" + email + "%");
            }
            if (agentName != null && !agentName.trim().isEmpty()) {
                query.setParameter("agentName", "%" + agentName + "%");
            }
            if (status != null && !status.trim().isEmpty()) {
                query.setParameter("status", "%" + status + "%");
            }
            List<Agent> result = query.getResultList();
            entityManager.getTransaction().commit();
            return result;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new RuntimeException("Lỗi khi tìm kiếm đại lý", e);
        } finally {
            entityManager.close();
        }
    }



}
