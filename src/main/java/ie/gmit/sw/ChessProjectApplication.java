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
        Option dbOpt = new Option("d" ,"db-ip", true, "The ip of the machine running the mysql database.");
        options.addOption(dbOpt);
        Option dbPassOpt = new Option("m", "db-pass", true, "The password used for the database.");
        options.addOption(dbPassOpt);

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

        String dbIp = cmd.hasOption("db-ip") ? cmd.getOptionValue("db-ip") : "localhost";
        String dataSource = "jdbc:mysql://" + dbIp + ":3306/chess_db?verifyServerCertificate=false&useSSL=false&requireSSL=false";

        String dbPass = cmd.hasOption("db-pass") ? cmd.getOptionValue("db-pass") : "";

        final Map<String, Object> props = new HashMap<>();
        props.put("server.port", port);
        props.put("python-path", pythonPath);
        props.put("spring.datasource.url", dataSource);
        props.put("spring.datasource.password", dbPass);

        new SpringApplicationBuilder()
                .sources(ChessProjectApplication.class)
                .properties(props)
                .run(args);
    }
}
