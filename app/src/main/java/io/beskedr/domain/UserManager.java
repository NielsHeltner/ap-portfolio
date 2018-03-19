package io.beskedr.domain;

public class UserManager {

    private static UserManager instance = null;
    private User currentUser = null;

    private UserManager() {}

    public static UserManager getInstance() {
        if(instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

}
