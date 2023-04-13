import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;
  ArrayList<String> str = new ArrayList<String>(10);

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Henri's number: %d", num);
        } else if (url.getPath().equals("/increment")) {
            num += 1;
            return String.format("Number incremented!");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("str")) {
                    str.add(parameters[1]);
                    return String.format("Added %s to list of Strings!", parameters[1]);
                }
            }
            else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("name")) {
                    if(str.contains(parameters[1])) {
                        return String.formnaat("%s is in the list of strings!", parameters[1]);
                    }
                    else {
                        return String.format("%s is NOT in the list of strings!", parameters[1]);
                    }
                }
            }
            return "404 Not Found!";
        }
    }
}

class NumberServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
