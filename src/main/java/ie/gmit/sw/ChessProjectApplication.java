package ie.gmit.sw;

import org.apache.commons.cli.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class ChessProjectApplication {

    public static void main(String[] args) {

        Options options = new Options();

        Option portOpt = new Option("p", "port", true, "port for the tomcat server to run on");
        options.addOption(portOpt);
        Option pathOpt = new Option("b", "bot-path", true, "The absolute path of the python script.");
        options.addOption(pathOpt);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Chess Online", options);
            System.exit(1);
            return;
        }

        String portNum = cmd.hasOption("port") ? cmd.getOptionValue("port") : "8080";
        int port = Integer.parseInt(portNum);
        String pythonPath = cmd.getOptionValue("bot-path");

        final Map<String, Object> props = new HashMap<>();
        props.put("server.port", port);
        props.put("python-path", pythonPath);

        new SpringApplicationBuilder()
                .sources(ChessProjectApplication.class)
                .properties(props)
                .run(args);
    }
}
