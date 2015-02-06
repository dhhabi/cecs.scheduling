package org.csulb.cecs.domain.account;

public interface AccountRepository {
	
	boolean createAccount(Account account) throws UsernameAlreadyInUseException;

	Account findAccountByUsername(String username);
	
}