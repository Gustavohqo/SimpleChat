package br.com.simplechat.app.controller;

import br.com.simplechat.app.application.annotation.Get;
import br.com.simplechat.app.application.annotation.Rest;

@Rest
public class HelloWorldController {

    @Get
    public String getGreetings() {
        return "Hello World!";
    }

    @Get(path = "room")
    public String getRoom() {
        return "Nenhuma sala foi criada ainda";
    }
}
