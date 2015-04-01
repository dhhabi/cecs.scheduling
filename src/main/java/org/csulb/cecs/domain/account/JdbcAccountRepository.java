package org.csulb.cecs.domain.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.csulb.cecs.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class JdbcAccountRepository implements AccountRepository {

	private final JdbcTemplate jdbcTemplate;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public JdbcAccountRepository(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
		this.jdbcTemplate = jdbcTemplate;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public boolean createAccount(Account user) throws UsernameAlreadyInUseException {
		int i = 0;
		try {
			i = jdbcTemplate.update(
					"insert into Account (firstName, lastName, username, password, role) values (?, ?, ?, ?, ?)",
					user.getFirstName(), user.getLastName(), user.getUsername(),
					passwordEncoder.encode(user.getPassword()), user.getRole());
			
		} catch (DuplicateKeyException e) {
			throw new UsernameAlreadyInUseException(user.getUsername());
		}
		if(i>0){
			return true;
		}else{
			return false;
		}
	}

	public Account findAccountByUsername(String username) {
		return jdbcTemplate.queryForObject("select username, password, firstName, lastName, role from Account where username = ?",
				new RowMapper<Account>() {
					public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
						return new Account(rs.getString("username"), rs.getString("password"), rs.getString("firstName"), rs
								.getString("lastName"), rs.getString("role"));
					}
				}, username);
	}

	
}
