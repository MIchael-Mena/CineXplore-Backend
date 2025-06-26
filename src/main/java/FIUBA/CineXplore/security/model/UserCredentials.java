package FIUBA.CineXplore.security.model;

import java.util.Set;

// Cualquier nuevo tipo de usuario debe implementar esta interfaz
public interface UserCredentials {

    String getUserName();

    String getEmail();

    String getPasswordHash();

    Set<Role> getRoles();
}