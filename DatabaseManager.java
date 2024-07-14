package Literatura.lib.scr.main.java;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private Connection connect() {
        String url = "jdbc:sqlite:libros.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createTables() {
        String sqlLibros = "CREATE TABLE IF NOT EXISTS libros (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " title TEXT NOT NULL,\n"
                + " author TEXT,\n"
                + " language TEXT,\n"
                + " downloads INTEGER\n"
                + ");";
        
        String sqlAutores = "CREATE TABLE IF NOT EXISTS autores (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " name TEXT NOT NULL,\n"
                + " birthdate TEXT,\n"
                + " deathdate TEXT,\n"
                + " isAlive BOOLEAN\n"
                + ");";

        try (Connection conn = this.connect();
            PreparedStatement pstmt1 = conn.prepareStatement(sqlLibros);
            PreparedStatement pstmt2 = conn.prepareStatement(sqlAutores)) {
            pstmt1.execute();
            pstmt2.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertBook(String title, String author, String language, int downloads) {
        String sql = "INSERT INTO libros(title, author, language, downloads) VALUES(?, ?, ?, ?)";
        
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, language);
            pstmt.setInt(4, downloads);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertAuthor(String name, String birthdate, String deathdate, boolean isAlive) {
        String sql = "INSERT INTO autores(name, birthdate, deathdate, isAlive) VALUES(?, ?, ?, ?)";
        
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, birthdate);
            pstmt.setString(3, deathdate);
            pstmt.setBoolean(4, isAlive);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> getAllBooks() {
        String sql = "SELECT title, author, language, downloads FROM libros";
        List<String> books = new ArrayList<>();
        
        try (Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String book = String.format("Title: %s, Author: %s, Language: %s, Downloads: %d",
                        rs.getString("title"), rs.getString("author"), rs.getString("language"), rs.getInt("downloads"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return books;
    }

    public List<String> getAllAuthors() {
        String sql = "SELECT name, birthdate, deathdate, isAlive FROM autores";
        List<String> authors = new ArrayList<>();
        
        try (Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String author = String.format("Name: %s, Birthdate: %s, Deathdate: %s, IsAlive: %b",
                        rs.getString("name"), rs.getString("birthdate"), rs.getString("deathdate"), rs.getBoolean("isAlive"));
                authors.add(author);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return authors;
    }

    public List<String> getAuthorsByYear(int year) {
        String sql = "SELECT name FROM autores WHERE birthdate LIKE ?";
        List<String> authors = new ArrayList<>();
        
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + year + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                authors.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return authors;
    }

    public List<String> getBooksByLanguage(String language) {
        String sql = "SELECT title, author FROM libros WHERE language = ?";
        List<String> books = new ArrayList<>();
        
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, language);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String book = String.format("Title: %s, Author: %s", rs.getString("title"), rs.getString("author"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return books;
    }

    public List<String> getTopDownloadedBooks(int limit) {
        String sql = "SELECT title, downloads FROM libros ORDER BY downloads DESC LIMIT ?";
        List<String> books = new ArrayList<>();
        
        try (Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, limit);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String book = String.format("Title: %s, Downloads: %d", rs.getString("title"), rs.getInt("downloads"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return books;
    }

    public void getStatistics() {
        String sql = "SELECT COUNT(*) AS book_count, AVG(downloads) AS avg_downloads FROM libros";
        
        try (Connection conn = this.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                int bookCount = rs.getInt("book_count");
                double avgDownloads = rs.getDouble("avg_downloads");
                System.out.println("Total books: " + bookCount);
                System.out.println("Average downloads: " + avgDownloads);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

