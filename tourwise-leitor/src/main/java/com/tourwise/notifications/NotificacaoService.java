package com.tourwise.notifications;

import com.tourwise.database.ConnectionFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class NotificacaoService {
    private final SlackService slackService;
    private final ConnectionFactory connectionFactory;
    private final String CANAL_USUARIOS = "C0CH5VA96LV";
    private final String CANAL_ADM = "C0CLUKB7N8Y";

    public NotificacaoService(SlackService slackService) {
        this.slackService = slackService;
        this.connectionFactory = new ConnectionFactory();
    }

    public void notificarNovosDados(String arquivo, int registros) throws Exception{
        String mensagem = "Olá! Novos dados estão disponíveis no Tourwise!\n\n" +
                "Arquivo: " + arquivo + "\n" +
                "Foram registrados " + registros + " novos dados de hotéis\n" +
                "Status: Concluído";

        slackService.enviarMensagem(CANAL_USUARIOS, mensagem);
    }

    public void notificarExecucao(List<String> arquivos, int registros) throws Exception{
        JdbcTemplate template = connectionFactory.getJdbcTemplate();

        String mensagem = "Olá! O DataTour-Engine foi executado e encerrado com sucesso!\n\n" +
                "Horario de execução:" + template.queryForObject("SELECT data_hora_log FROM java_logs WHERE id_logs = (SELECT MAX(id_logs) FROM java_logs WHERE mensagem = 'Programa iniciado')", LocalDateTime.class) + "\n" +
                "Horario de encerramento:" + template.queryForObject("SELECT data_hora_log FROM java_logs WHERE id_logs = (SELECT MAX(id_logs) FROM java_logs WHERE mensagem = 'Programa encerrado')", LocalDateTime.class) + "\n" +
                "Arquivos Gravados: " + arquivos.toString() + "\n" +
                "Foram registrados " + registros + " novos dados de hotéis\n" +
                "Status: Concluído";

        slackService.enviarMensagem(CANAL_ADM, mensagem);
    }
}
