package com.example.json;

import android.util.Log;
import java.io.*;
import java.net.*;


public class HttpHandler {



    String opener = "{\"contacts\":";
    String closer = "}";

    public HttpHandler() {
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {

            /*
            * Los pasos para usar HttpURLConnection son los siguientes:
            * Crea un objeto URL:URL url = new URL(aqui se pone la url de la api);
            * Llame a openConnection() del objeto URL para obtener una instancia del objeto HttpURLConnection:HttpURLConnection conn = (HttpURLConnection) url.openConnection()
            * Configure el método de solicitud HTTP: GET o POST, u otros métodos de solicitud conn.setRequestMethod("GET");
            * Llame al método getInputStream() para obtener el flujo de entrada de datos devuelto por el servidor, y luego se lee el flujo de entrada con
            * inputStream in = conn.getInputStream();
            */
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            /*
            * Para java, un InputStream es cualquier cosa de la que se leen bytes. Puede ser el teclado, un fichero, un socket, o cualquier otro dispositivo de entrada.
            * Esto, por un lado es una ventaja. Por otro lado, es una desventaja. Como un InputStream es para leer bytes, sólo tiene métodos para leer bytes.
            * */
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = streamToString(in);
        } catch (MalformedURLException e) {

        } catch (ProtocolException e) {

        } catch (IOException e) {

        } catch (Exception e) {

        }
        return response;
    }

    private String streamToString(InputStream is) {
        /*
        * Java proporciona varios mecanismos para leer desde el archivo. El paquete más útil que se proporciona para
        * esto es el java.io.Reader. Esta clase contiene el Class BufferedReader bajo el paquete java.io.BufferedReader
        * BufferedReader únicamente posee el método readLine() para leer la entrada y este siempre retorna String,
        * */
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        try {
            /*
            * El ciclo while en el código siguiente leerá el archivo hasta que haya llegado al final del archivo
            * line lee la línea actual y readLine() devuelve una cadena. Por lo tanto, el ciclo se repetirá hasta que no sea nulo.
            *
            *StringBuilder.append() es un método incorporado en Java que se usa para agregar la representación de string a una secuencia dada.
            * */
            stringBuilder.append(opener);
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            stringBuilder.append(closer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
