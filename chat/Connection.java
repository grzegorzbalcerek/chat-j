package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

class Connection implements Runnable {
    private final Socket socket;
    private final Chat chat;
    private final BufferedReader br;
    private final PrintWriter pw;

    Connection(Socket socket, Chat chat) throws Exception {
        this.socket = socket;
        this.chat = chat;
        this.br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        this.pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
    }

    public void run() {
        try {
            pw.print("Type your name and press ENTER: ");
            pw.flush();
            String nick = br.readLine();
            String input;
            do {
                input = br.readLine();
                pw.println(input);
            } while (!"".equals(input));
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}