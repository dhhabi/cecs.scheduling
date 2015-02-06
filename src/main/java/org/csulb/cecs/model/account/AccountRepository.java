package org.csulb.cecs.model.account;

public interface AccountRepository {
	
	boolean createAccount(Account account) throws UsernameAlreadyInUseException;

	Account findAccountByUsername(String username);
	
}
