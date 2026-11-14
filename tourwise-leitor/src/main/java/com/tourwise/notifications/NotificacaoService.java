package com.tourwise.notifications;

public class NotificacaoService {
    private final SlackService slackService;
    private final String CANAL_USUARIOS = "C0CH5VA96LV";
    private final String CANAL_ADM = "ID_CANAL_ADMINISTRADORES";

    public NotificacaoService(SlackService slackService) {
        this.slackService = slackService;
    }

    public void notificarNovosDados(String arquivo, int registros) throws Exception{
        String mensagem = "Olá! Novos dados estão disponíveis no Tourwise!\n\n" +
                "Arquivo: " + arquivo + "\n" +
                "Foram registrados " + registros + " novos dados de hotéis\n" +
                "Status: Concluído";

        slackService.enviarMensagem(CANAL_USUARIOS, mensagem);
    }
}
