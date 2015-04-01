package org.csulb.cecs.domain.account;

import org.csulb.cecs.domain.Account;

public interface AccountRepository {
	
	boolean createAccount(Account account) throws UsernameAlreadyInUseException;

	Account findAccountByUsername(String username);
	
}
