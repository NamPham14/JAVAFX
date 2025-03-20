package com.hsf302.spring.javafx01.dao;

import com.hsf302.spring.javafx01.entity.Agent;

import java.util.List;

public interface AgentDAO {

    List<Agent> selectAll();

    Agent selectByEmail(String email);

    void delete(int agentId);

    void save(Agent agent);

    List<Agent> findAgents(String email, String agentName, String status);
}
