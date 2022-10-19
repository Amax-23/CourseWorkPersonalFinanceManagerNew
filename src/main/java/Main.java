import ru.netology.Category;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;


public class Main {
    private static final int PORT = 8989;
    private static final File dataLogFile = new File("data.bin");

    public static void main(String[] args) {
        Category category = new Category();
        if (Files.exists(dataLogFile.toPath())) {
            try {
                category = Category.loadFromBinFile(dataLogFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            category.readTsvFile();
        }
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started!");
            while (true) {
                try (Socket clientSocet = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocet.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocet
                             .getInputStream()))) {
                    System.out.println("New connection accepted!");
                    category.readJsonFile(in.readLine());
                    category.saveJsonFile();
                    out.println(category.getOutJsonFile());
                    category.saveLogServerBin(dataLogFile);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер!");
            e.printStackTrace();
        }
    }
}