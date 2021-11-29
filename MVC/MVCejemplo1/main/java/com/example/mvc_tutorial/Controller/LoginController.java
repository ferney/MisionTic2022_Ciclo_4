package com.example.mvc_tutorial.Controller;

import com.example.mvc_tutorial.Model.User;
import com.example.mvc_tutorial.View.ILoginView;

public class LoginController implements ILoginController {

    ILoginView iLoginView;

    public LoginController(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    @Override
    public void onLogin(String email, String password) {

        User user = new User(email,password);
        int logincode = user.isValid();

        if(logincode == 0){
            iLoginView.OnLoginError("Please Enter Email");
        }
        else if(logincode == 1){
            iLoginView.OnLoginError("Please Enter valid Email");
        }
        else if(logincode == 2){
            iLoginView.OnLoginError("Please Enter a Password");
        }
        else if(logincode == 3){
            iLoginView.OnLoginError("Password should more than 8 characters");
        }
        else {
            iLoginView.OnLoginSuccess("Login Success");
        }
    }
}
