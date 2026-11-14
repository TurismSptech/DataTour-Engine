package com.tourwise.notifications;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SlackService {
    private final String token;
    private final Slack slack;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");


    public SlackService(String token){
        this.token = token;
        this.slack = Slack.getInstance();
    }

    public void enviarMensagem(String canalId, String mensagem) throws SlackApiException, IOException {
        ChatPostMessageResponse response =
                slack.methods(token).chatPostMessage(req -> req
                        .channel(canalId)
                        .text(mensagem)
                );

        if (response.isOk()) {
            System.out.println(LocalDateTime.now().format(formatter) +
                    ANSI_GREEN + " - [INFO]" +
                    "[SYSTEM]" + ANSI_RESET +
                    " - Mensagem Enviada com Sucesso\n");
        } else {
            System.out.println("Erro: " + response.getError());
        }
    }



}
