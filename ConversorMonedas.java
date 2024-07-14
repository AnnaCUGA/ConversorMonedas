//Importación de bibliotecas para el correcto funcionamiento del programa

/**
1.- biblioteca Gson para la conversión entre java y Json
2.- solicitudes tipo htto
3.- manejar url
4.- Importamos la excepción IOexception, por si ocurre un problema de entrada o salida durante las solicitudes http, cerrando así
/el programa y evidanto crasheos
5.- el scanner para recibir entradas de usuario
6.- interfaz map
**/

import com.google.gson.Gson;
import java.net.http.*;
import java.net.URI;
import java.io.IOException;
import java.util.Scanner;
import java.util.Map;

//Clase principal que incluye el main, ConversorMonedas.java
public class ConversorMonedas {

    public static void main(String[] args) {
        
        //cargamos la api que necesitamos del servicio ExchangeRate, para tener las tasas de cambio más recientes 
        String apiKey = "94dbca1d77cfb1a6103e0277";
        String apiUrl = "https://api.exchangerate-api.com/v4/latest/USD?apikey=" + apiKey;

        //Creamos un cliente de HTTP que se usa para crear y enviar solicitudes de tipo http
        HttpClient client = HttpClient.newHttpClient();
        //creamos la instancia del cliente
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(apiUrl))
            .build();


        //enviamos la solicitud http y almacenamos la respuesta de esta solicitud como la variable response
        HttpResponse<String> response;
        try {
            //usando try y catch par manejar la exception por si ocurre algún error
            //vamos a obtener la respuesta "response" al enviar la solicitud, tratando las respuestas como una cadena de texto
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //en esta parte del catch se revisa si hay problemas en la entrada o salida, o si la operación fue interrumpida por alguna
        //otra razón
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return;
            //en caso de que sea así se termina el programa
        }


        //creamos un valor string llamado jsonResponse, que será obtenido como el valor de response tomándolo como cadena de texto
        String jsonResponse = response.body();
        //se crea la instancia Gson
        Gson gson = new Gson();
        //usamos el map
        Map<String, Object> responseMap = gson.fromJson(jsonResponse, Map.class);
        Map<String, Double> rates = (Map<String, Double>) responseMap.get("rates");

        //cremos el scanner para que el usuario escoja sus opciones
        Scanner scanner = new Scanner(System.in);

        //le pedimos al usuario que indique la cantidad de dolares que tenga, lo cargamos como double ya que al ser una moneda
        //puede haber centavos, o tomados también como decimales
        System.out.println("¿Quieres cambiar de dólares a otra moneda o de otra moneda a dólares? ");
        System.out.println("Coloca la opción en número que buscas");
        System.out.println("1.- Dólares a MXN, EUR, JPY, GBP");
        System.out.println("2.- MXN, EUR, JPY, GBP a Dólares");
        double usuario = scanner.nextDouble();

        if(usuario == 1){
            System.out.println("Coloca la cantidad de dólares: ");
            double cantidadUsd = scanner.nextDouble();
        
        if(cantidadUsd >=0){
    

            System.out.println("A qué moneda deseas hacer el cambio: MXN, EUR, JPY, GBP");
            String moneda = scanner.next().toUpperCase();

            if (rates.containsKey(moneda)) {
            //el valor de rates usa el map, lo que contiene las tasas de cambio para las diversas monedas, se verifica si este
            //dato ingresado por el usuario realmente coincide con alguna moneda que contiene la página de doned obtuvimos la API

            //obtenemos el valor de la moneda si es que si hay, y tomamos el valor de la moneda
                double tasa = rates.get(moneda);
                double resultado = cantidadUsd * tasa;
            

                //el resultado final será el valor de la cantidad de dólares del usuario por el valor de la tasa de cambio
                //mostramos al usuario cuanto dinero tiene de dicha moneda según la cantidad de dólares
                System.out.printf("%.2f USD son %.2f %s\n", cantidadUsd, resultado, moneda);
                } else {
                //en caso que la moneda no esté en la lista de las posibles, mostramos mensaje de que la moneda no existe
                System.out.println("Moneda no válida");
                }
            }else{
                System.out.println("Favor de colocar únicamente numeros positivos");

            }


        } else if(usuario == 2){
            System.out.println("Selecciona la moneda que tienes: MXN, EUR, JPY, GBP");
            String moneda = scanner.next().toUpperCase();

            System.out.println("Coloca la cantidad de " + moneda + " que tienes:");
            double pesos = scanner.nextDouble();

            if(pesos >=0){

            if (rates.containsKey(moneda)) {
            //el valor de rates usa el map, lo que contiene las tasas de cambio para las diversas monedas, se verifica si este
            //dato ingresado por el usuario realmente coincide con alguna moneda que contiene la página de doned obtuvimos la API

            //obtenemos el valor de la moneda si es que si hay, y tomamos el valor de la moneda
                String dolares = "USD";
                double tasa = rates.get(moneda);
                double resultado = pesos / tasa;
            //el resultado final será el valor de la cantidad de dólares del usuario por el valor de la tasa de cambio
            //mostramos al usuario cuanto dinero tiene de dicha moneda según la cantidad de dólares
                System.out.printf( pesos + moneda + " son " + resultado + " Dólares ");
            } else {
                //en caso que la moneda no esté en la lista de las posibles, mostramos mensaje de que la moneda no existe
            System.out.println("Moneda no válida");
            }
        }else{
            System.out.println("Favor de colocar únicamente numeros positivos");
        }
        
        }else{

            System.out.println("Opción no válida");
        }
        

        System.out.println("\n\tAdiós.");

        scanner.close();
    }
}
