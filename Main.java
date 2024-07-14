package Literatura.lib.scr.main.java;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ApiClient apiClient = new ApiClient();
        DatabaseManager dbManager = new DatabaseManager();
        dbManager.createTables();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Buscar libro en la API");
            System.out.println("2. Mostrar libros registrados");
            System.out.println("3. Mostrar autores registrados");
            System.out.println("4. Buscar y mostrar autores vivos en un año determinado");
            System.out.println("5. Buscar y mostrar libros por idioma");
            System.out.println("6. Mostrar el top 5 de libros más descargados");
            System.out.println("7. Mostrar estadísticas generales de la base de datos");
            System.out.println("8. Buscar autor por nombre");
            System.out.println("9. Mostrar el top 10 de libros más descargados");
            System.out.println("10. Listar autores con otras consultas");
            System.out.println("11. Salir");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (option) {
                case 1:
                    System.out.println("Ingrese el título del libro:");
                    String title = scanner.nextLine();
                    try {
                        String response = apiClient.searchBooks(title);
                        System.out.println("Respuesta de la API: " + response);
                        // Aquí puedes parsear la respuesta y almacenar los datos en la base de datos
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    List<String> books = dbManager.getAllBooks();
                    for (String book : books) {
                        System.out.println(book);
                    }
                    break;
                case 3:
                    List<String> authors = dbManager.getAllAuthors();
                    for (String author : authors) {
                        System.out.println(author);
                    }
                    break;
                case 4:
                    System.out.println("Ingrese el año:");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    List<String> authorsByYear = dbManager.getAuthorsByYear(year);
                    for (String author : authorsByYear) {
                        System.out.println(author);
                    }
                    break;
                case 5:
                    System.out.println("Ingrese el idioma:");
                    String language = scanner.nextLine();
                    List<String> booksByLanguage = dbManager.getBooksByLanguage(language);
                    for (String book : booksByLanguage) {
                        System.out.println(book);
                    }
                    break;
                case 6:
                    List<String> top5Books = dbManager.getTopDownloadedBooks(5);
                    for (String book : top5Books) {
                        System.out.println(book);
                    }
                    break;
                case 7:
                    dbManager.getStatistics();
                    break;
                case 8:
                    System.out.println("Ingrese el nombre del autor:");
                    String authorName = scanner.nextLine();
                    // Implementar la lógica para buscar autor por nombre
                    break;
                case 9:
                    List<String> top10Books = dbManager.getTopDownloadedBooks(10);
                    for (String book : top10Books) {
                        System.out.println(book);
                    }
                    break;
                case 10:
                    // Implementar lógica para listar autores con consultas adicionales
                    break;
                case 11:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}
