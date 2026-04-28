package frc.robot.subsystems;

import java.io.*;
import java.net.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ObjectDetector extends SubsystemBase {
    private final String host;
    private final int port;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * @param host IP адрес VMX-pi (обычно 172.22.11.2 через USB или 10.TE.AM.2 через Wi-Fi)
     * @param port Порт (мы настроили 5800)
     */
    public ObjectDetector(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Запрашивает данные у Python-сервера
     * @return JsonNode (массив объектов) или пустой массив при ошибке
     */
    public JsonNode readFrameAsJson() {
        // Используем try-with-resources для автоматического закрытия сокета
        try (Socket socket = new Socket()) {
            // Подключаемся с таймаутом 100мс (чтобы не вешать робота)
            socket.connect(new InetSocketAddress(host, port), 100);
            socket.setSoTimeout(100);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Отправляем команду
            out.println("read_frame");
            
            // Читаем ответ
            String jsonResponse = in.readLine();
            
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                return mapper.readTree(jsonResponse);
            }
        } catch (Exception e) {
            System.out.println("[Vision Error] " + e.getMessage());
        }
        
        return mapper.createArrayNode();
    }
}