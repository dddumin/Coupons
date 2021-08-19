package springtest.controller;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public abstract class ClientController {
    public abstract boolean login(HttpServletResponse response, String login, String password);
}
