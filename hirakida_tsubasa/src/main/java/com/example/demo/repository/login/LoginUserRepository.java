package com.example.demo.repository.login;

import java.util.Optional;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.User;

@Repository
public class LoginUserRepository {

    private static final String SQL_FIND_BY_EMAIL = """
            SELECT
              u.id,
              u.email,
              u.name AS user_name,
              u.password
            FROM users u
            WHERE u.email = :email
            """;

    private static final ResultSetExtractor<User> LOGIN_USER_EXTRACTOR = (rs) -> {
        int id = 0;
        String email = null;
        String userName = null;
        String password = null;
        while (rs.next()) {
            if (email == null) {
                id = rs.getInt("id");
                email = rs.getString("email");
                userName = rs.getString("user_name");
                password = rs.getString("password");
            }
        }
        if (email == null) {
            return null;
        }
        return new User(id, email, userName, password);
    };

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public LoginUserRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<User> findByEmail(String email) {
        MapSqlParameterSource params = new MapSqlParameterSource("email", email);
        User loginUser = namedParameterJdbcTemplate.query(SQL_FIND_BY_EMAIL, params, LOGIN_USER_EXTRACTOR);
        return Optional.ofNullable(loginUser);
    }
}
