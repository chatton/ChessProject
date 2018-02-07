package ie.gmit.sw;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ChessProjectApplication {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(args));
        int port = 8080; // by default use port 8080
        if (args.length > 0) { // specific port provided by user.
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Must specify a valid number for port. Exiting...");
                return;
            }
        }
        System.out.println(port);
        final Map<String, Object> props = new HashMap<>();
        props.put("server.port", port);

        new SpringApplicationBuilder()
                .sources(ChessProjectApplication.class)
                .properties(props)
                .run(args);
    }
}
