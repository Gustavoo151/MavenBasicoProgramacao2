package br.upe.mavenBasico;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

/**
 * Classe que obtém os números do último sorteio da mega-sena.
 */
public class ResultadoMegasena {
    // URL da API que será utilizada

//    Link da API que será utilizada: https://loteriascaixa-api.herokuapp.com/swagger-ui/#/Loterias/getLatestResultUsingGET
    private final static String URL = "https://loteriascaixa-api.herokuapp.com/api/megasena/latest";

    /**
     * Método que obtem um resultado da API e exibe os dados desejados
     * @return void
     * @throws RuntimeException
     */
    public static void obtemUmResultadoDaApi() {
        // Cria um cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Cria uma requisição
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .build();

        // Envia a requisição e recebe a resposta
        HttpResponse<String> response = null;

        try {
            // Envia a requisição e recebe a resposta
            response = client // Cliente HTTP que será utilizado para enviar a requisição
                    .send(request, // HttpRequest que será enviado
                            HttpResponse. // BodyHandler ele faz o parse do corpo da resposta
                                    BodyHandlers.ofString()); // BodyHandler que converte o corpo da resposta para String

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Converte a resposta para JSON para facilitar a manipulação
        JSONObject json = new JSONObject(response.body());
        mostraResonse(json); // Chama o método que exibe os dados desejados
    }

    /**
     * Método que exibe os dados desejados
     * @param reponse JSONObject que contém a resposta da API
     * @return void
     */
    private static void mostraResonse(JSONObject reponse){
        System.out.println(
                "|" + "-".repeat(55) + "|".indent(0) +
                "|" + " ".repeat(15) + "Resultado da Mega-Sena" + "|".indent(18) +
                "|" + "-".repeat(55) + "|".indent(0) +
                "| Data do sorteio: " + reponse.get("data") + "|".indent(27) +
                "| Valor acumulado do concurso 0-5 : " + reponse.get("valorAcumuladoConcurso_0_5") +  "|".indent(10) +
                "| Loteria: " + reponse.get("loteria") + "|".indent(37) +
                "| Valor acumulado do concurso especial : R$ " + reponse.get("valorAcumuladoConcursoEspecial") +  "|".indent(1) +
                "| Dezenas sorteadas: " + reponse.get("dezenasOrdemSorteio").toString().replaceAll("[\\[\\]\",]", " ") + "|".indent(4) +
                "|" + "-".repeat(55) + "|".indent(0)
        );
    }
}
