package org.csulb.cecs.service.user;

import org.csulb.cecs.model.CurrentUser;

public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, Long userId);

}