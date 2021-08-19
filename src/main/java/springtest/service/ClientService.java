package springtest.service;

import springtest.exceptions.IncorrectHashException;

public interface ClientService {
    String login(String login, String password);
}
