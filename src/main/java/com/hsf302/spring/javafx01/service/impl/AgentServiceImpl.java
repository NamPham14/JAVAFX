package com.hsf302.spring.javafx01.service.impl;

import com.hsf302.spring.javafx01.dao.AgentDAO;
import com.hsf302.spring.javafx01.dao.impl.AgentDAOImpl;
import com.hsf302.spring.javafx01.dto.AgentDTO;
import com.hsf302.spring.javafx01.entity.Agent;
import com.hsf302.spring.javafx01.service.AgentService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AgentServiceImpl implements AgentService {
    private final AgentDAO agentDAO;

    public AgentServiceImpl() {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("JakartaPersistenceUnit");
        agentDAO = new AgentDAOImpl(entityManagerFactory);

          }

         @Override
         public void createAgent(String agentName, String email, String address, String password) {
        Agent agent = new Agent();
        agent.setAgentName(agentName);
        agent.setEmail(email);
        agent.setAddress(address);
        agent.setPassword(password);
        agent.setStatus("Mới");
        agent.setRegisterDate(new Date());
        agent.setAccountBalance(0.0);
             System.out.println("Đang lưu đại lý với email: " + email);
             agentDAO.save(agent);
             System.out.println("Đại lý đã được lưu thành công với email: " + email);

         }




    @Override
    public List<AgentDTO> getAllAgents() {
        List<AgentDTO> list = new ArrayList<>();
        List<Agent> agents = agentDAO.selectAll();
        for (Agent agent : agents) {
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setAgentId(agent.getAgentId());
            agentDTO.setAgentName(agent.getAgentName());
            agentDTO.setAccountBalance(agent.getAccountBalance());
            agentDTO.setEmail(agent.getEmail());
            agentDTO.setStatus(agent.getStatus());
            agentDTO.setAddress(agent.getAddress());
            agentDTO.setPassword(agent.getPassword());
            agentDTO.setRegisterDate(agent.getRegisterDate());
            list.add(agentDTO);
        }

        return list;

    }

    @Override
    public AgentDTO authenticate(String email, String password) {
        Agent agent = agentDAO.selectByEmail(email);
        if (agent != null && agent.getPassword().equals(password)) {
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setAgentId(agent.getAgentId());
            agentDTO.setAgentName(agent.getAgentName());
            agentDTO.setAccountBalance(agent.getAccountBalance());
            agentDTO.setEmail(agent.getEmail());
            agentDTO.setStatus(agent.getStatus());
            agentDTO.setAddress(agent.getAddress());
            agentDTO.setPassword(agent.getPassword());
            agentDTO.setRegisterDate(agent.getRegisterDate());
            return agentDTO;
        }
        return null;
    }
    @Override
    public List<AgentDTO> findAgents(String email, String agentName, String status){

        List<AgentDTO> list = new ArrayList<>();
        List<Agent> agents = agentDAO.findAgents(email, agentName, status);
        for (Agent agent : agents) {
            AgentDTO agentDTO = new AgentDTO();
            agentDTO.setAgentId(agent.getAgentId());
            agentDTO.setAgentName(agent.getAgentName());
            agentDTO.setAccountBalance(agent.getAccountBalance());
            agentDTO.setEmail(agent.getEmail());
            agentDTO.setStatus(agent.getStatus());
            agentDTO.setAddress(agent.getAddress());
            agentDTO.setPassword(agent.getPassword());
            agentDTO.setRegisterDate(agent.getRegisterDate());
            list.add(agentDTO);
        }
        return list;

    }








}
