package com.tourwise;

import com.tourwise.database.InsertData;
import com.tourwise.excel.LeitorPlanilha;
import com.tourwise.model.Hospedagem;
import com.tourwise.javalogs.Log;
import com.tourwise.notifications.NotificacaoService;
import com.tourwise.notifications.SlackService;
import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.File;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Dotenv dotenv = Dotenv.load();
        SlackService slackService = new SlackService(dotenv.get("SLACK_API_TOKEN"));
        NotificacaoService notificacaoService = new NotificacaoService(slackService);

        Log.log("Programa iniciado", "");

        //definir caminho da pasta de leitura
        File caminho = new File("/home/hugo/Desktop/S3");

        File[] arquivos = caminho.listFiles();
        InsertData insert = new InsertData();

        Log.log("Buscando planilhas já inseridas no BD", "");

        List<String> nomePlanilhasLidas = insert.buscarPlanilhasLidas();

        Log.log(nomePlanilhasLidas.size() + " planilha(s) encontrada(s) no BD", "");
        Log.log("Procurando novas planilhas em " + caminho, "");
        
        Boolean temPlanilhaNova = false;

        for (File arquivo : arquivos) {
            if (!nomePlanilhasLidas.contains(arquivo.getName())) {
                temPlanilhaNova = true;

                Log.log("Nova planilha encontrada: " + arquivo.getName(), "green");

                String path = arquivo.getPath();

                Log.log("Iniciando gravação da planilha " + arquivo.getName() + " no BD", "blue");
        
                List<Hospedagem> hospedagens = new LeitorPlanilha().ler(path);
                Integer qtdRegistroIncompleto = 0;

                for (int i = 0; i < 5; i++) {
                    if(!insert.temTodosAtributosPreenchidos(hospedagens.get(i))){ qtdRegistroIncompleto++; }
                    insert.insertHosp(hospedagens.get(i));
                }

                Log.log("Inserção da planilha " + arquivo.getName() + " finalizada", "green");

                if(qtdRegistroIncompleto != 0){
                Log.log(qtdRegistroIncompleto + " registros possuem dados incompletos", "yellow");
                }

                Log.log(hospedagens.size() + " registros encontrados", "blue");
                Log.log("Registrando no banco que a planilha já foi lida", "green");
                Log.log("Notificando via Slack sobre a nova planilha", "blue");

                insert.gravarNomeDePlanilhaLida(arquivo.getName());
                notificacaoService.notificarNovosDados(arquivo.getName(), hospedagens.size());
            }
        }

        Log.log("Nenhuma planilha nova encontrada", "yellow");
        Log.log("Programa encerrado", "blue");
    }
}
