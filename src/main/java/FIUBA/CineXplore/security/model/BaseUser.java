package FIUBA.CineXplore.security.model;

import java.util.Set;

public interface BaseUser {

    String getUserName();

    String getEmail();

    String getPasswordHash();

    Set<Role> getRoles();
}