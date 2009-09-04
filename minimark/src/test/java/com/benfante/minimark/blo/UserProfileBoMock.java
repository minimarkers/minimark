package com.benfante.minimark.blo;

/**
 * A mock class for the USerProfileBo.
 *
 * @author lucio
 */
public class UserProfileBoMock extends UserProfileBo {
    protected String authenticatedUsername;

    @Override
    public String getAuthenticatedUsername() {
        return this.authenticatedUsername;
    }

    public void setAuthenticatedUsername(String authenticatedUsername) {
        this.authenticatedUsername = authenticatedUsername;
    }

}
