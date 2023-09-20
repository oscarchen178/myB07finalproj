package com.example.cscb07final;

public class LoginPresenter implements LoginContract.LoginPresenter{

    private LoginContract.LoginView view;
    private LoginContract.LoginModel model;

    public LoginPresenter(LoginContract.LoginView view, LoginContract.LoginModel model){
        this.view = view;
        this.model = model;
    }

    @Override
    public void loginUser() throws InterruptedException {

        // get email and password
        String email = view.getEmail();
        String password = view.getPassword();

        // check if any are empty
        if(email.isEmpty() || password.isEmpty()){
            // display message that need to fill out fields
            view.displayMessage("Fields must not be empty");
        } else if (! email.matches(".*@.*")){
            // not a valid email
            view.displayMessage("Need to input a valid email");

        } else {
            // login to Firebase Auth
            model.loginAuth(email, password);

            // check for success
            String result = model.loginSuccessful();

            // check for success
            if(! result.isEmpty()){

                // login successful
                view.displayMessage(result);

                // go to next activity after login
                view.changeActivity();

            } else{

                // login failed
                view.displayMessage("Login Failed");

            }
        }
    }

}
