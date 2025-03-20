package com.hsf302.spring.javafx01.service;

import com.hsf302.spring.javafx01.dto.AgentDTO;

import java.util.List;

public interface AgentService {

    void createAgent(String agentName, String email, String address, String password);




    List<AgentDTO> getAllAgents();

    AgentDTO authenticate(String email, String password);

    List<AgentDTO> findAgents(String email, String agentName, String status);
}
