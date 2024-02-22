package com.client.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.client.entity.Client;

import java.util.List;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private static final String INSERT_USER_QUERY = "INSERT INTO USER(id,fname,lname,email) values(?,?,?,?)";
    private static final String UPDATE_USER_BY_ID_QUERY = "UPDATE USER SET fname=? WHERE ID=?";
    private static final String GET_USER_BY_ID_QUERY = "SELECT * FROM USER WHERE ID=?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM USER WHERE ID=?";
    private static final String GET_USERS_QUERY = "SELECT * FROM USER";
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Client saveUser(Client user) {
        jdbcTemplate.update(INSERT_USER_QUERY, user.getId(), user.getFname(), user.getLname(), user.getEmail());
        return user;
    }

    @Override
    public Client updateUser(Client user) {
        jdbcTemplate.update(UPDATE_USER_BY_ID_QUERY, user.getFname(), user.getId());
        return user;
    }

    @Override
    public Client getById(int id) {
        return jdbcTemplate.queryForObject(GET_USER_BY_ID_QUERY, (rs, rowNum) -> {
            return new Client(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"));
        },id);
    }

    @Override
    public String deleteById(int id) {
        Integer i=jdbcTemplate.update(DELETE_USER_BY_ID, id);
        if(i!=1) {
        	return "user notfound : ";
        }
        return "User got deleted with id :" + id;
    }

    @Override
    public List<Client> allUsers() {
        return jdbcTemplate.query(GET_USERS_QUERY, (rs, rowNum) -> {
            return new Client(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("email"));
        });
    }
}
