package com.pichulacorp.integracion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProvider implements UserDetailsService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT rut,pwd FROM Cliente WHERE rut=?";
        return jdbcTemplate.queryForObject(sql,new UserMapper(),username);
    }

    static class UserMapper implements RowMapper<UserDetails> {

        @Override
        public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User(rs.getString("rut"), rs.getString("pwd"));
            return user;
        }
    }


}
