package br.upe.mavenBasico;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Classe que obtém os números do último sorteio da mega-sena.
 */
public class ResultadoMegaSenaOld {

    /** URL que possui as dezenas sorteadas. */
    private final static String URL = "http://www1.caixa.gov.br/_newincludes/home_2011/resultado_megasena.asp"; // Api sem funcionar

    /** Marcação inicial para extrair as dezenas do retorno HTML. */
    private final static String MARCA_INICIAL_RETORNO_NAO_UTIL = "<div id='concurso_resultado'>";

    /** Marcação final para extrair as dezenas do retorno HTML. */
    private final static String MARCA_FINAL_RETORNO_NAO_UTIL = "</div>";

    /**
     * Método que se conecta ao site da CEF para obter as dezenas
     do último sorteio.
     * @return array de Strings, onde cada elemento é uma dezena
    sorteada.
     */
    public static String[] obtemUltimoResultado() {

        //Criação do cliente HTTP que fará a conexão com o site
        HttpClient httpclient = new DefaultHttpClient();

        try {
            // Definição da URL a ser utilizada
            HttpGet httpget = new HttpGet(URL);

            // Manipulador da resposta da conexão com a URL
            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            // Resposta propriamente dita
            String html = httpclient.execute(httpget, responseHandler);

            //Retorno das dezenas, após tratamento
            return obterDezenas(html);

        } catch (Exception e) {
            // Caso haja erro, dispara exceção.
            throw new RuntimeException("Um erro inesperado ocorreu.", e);
        } finally {
            //Destruição do cliente para liberação dos recursos dosistema.
            httpclient.getConnectionManager().shutdown();
        }
    }

    /**
    * Tratamento da resposta HTML obtida pelo método
    obtemUltimoResultado().
     * @param html resposta HTML obtida
     * @return array de Strings, onde cada elemento é uma dezenasorteada.
     * */
    private static String[] obterDezenas(String html) {
        // Posição inicial de onde começam as dezenas
        Integer parteInicial = html.indexOf(MARCA_INICIAL_RETORNO_NAO_UTIL) + MARCA_INICIAL_RETORNO_NAO_UTIL.length();

        // Posição final de onde começam as dezenas
        Integer parteFinal = html.indexOf(MARCA_FINAL_RETORNO_NAO_UTIL);

        // Substring montada com base nas posições, com remoção deespaços.
        String extracao = html.substring(parteInicial, parteFinal).replaceAll(" ", "");

        // Criação de array, com base no método split(), separando porhifen.
                String[] numeros = extracao.split("-");
        return numeros;
    }
}
