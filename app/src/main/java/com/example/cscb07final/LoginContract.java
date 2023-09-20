package com.example.cscb07final;

public interface LoginContract {

    public interface LoginModel{
        public void loginAuth(String email, String password);
        public String loginSuccessful() throws InterruptedException;
    }

    public interface LoginView{
        public String getEmail();
        public String getPassword();
        public void displayMessage(String message);
        public void changeActivity();
    }

    public interface LoginPresenter{
        public void loginUser() throws InterruptedException;

    }
}
