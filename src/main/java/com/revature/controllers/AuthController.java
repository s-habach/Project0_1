package com.revature.controllers;

import com.revature.DAOs.AuthDAO;
import com.revature.models.LoginDTO;
import io.javalin.http.Handler;
import jakarta.servlet.http.HttpSession;

public class AuthController {

    // Instantiate AuthDAO so we can try to log in user
    AuthDAO aDAO = new AuthDAO();

    // HTTP Session object to store user information after successful login
    // This object will be initialized upon successful login, letting a user
    // access the app
    public static HttpSession ses;

    // "Initialized"? K=Just means it will be given a value

    // Login will be a POST request, since we're sending a username and password
    // (id and name for this)

    public Handler loginHandler = ctx -> {

        LoginDTO lDTO = ctx.bodyAsClass(LoginDTO.class);

        if (aDAO.login(lDTO.getUsername(), lDTO.getPassword())) {

            // Create a session object
            ses = ctx.req().getSession();

            // Store user information with setAttribute() method
            ses.setAttribute("username", lDTO.getUsername());
            // Note: we could have stored any kind of information in the Session
            // like this (id? role?)

            /*
            Foreshadowing for P!: each employee will have a role of employee or
                manager
            Managers will be able to do things that Employees can't
            Their identity as a manager or employee will be stored in their
                session after login
            Which will let us control what functionalities they can and can't access
             */

            // Send a success message
            ctx.result("Login successful! Welcome, " +
                    ses.getAttribute("username") + ".");
            ctx.status(200);
        }
        else {
            ctx.result("Login failed! Try again.");
            // If login fails, a good status code is 401 - Unauthorized
            ctx.status(401);
        }
    };
}
