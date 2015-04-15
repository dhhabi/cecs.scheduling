package org.csulb.cecs.dto;

import java.util.List;

import org.csulb.cecs.domain.Account;

public interface AccountDAO {
	public List<Account> getAllAccounts();
}
