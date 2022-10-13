package ies.lab2.e2;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

public class App {
    private static final long serialVersionUID = -1915463532411657451L;

    public static void main(String[] args) throws Exception {

        Server server = new Server(8680);

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(HelloServlet.class, "/");

        server.start();
        server.join();

    }

    public static class HelloServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            String name = request.getParameter("name");
            // String caps = name.toUpperCase(); // NullPointerException when name is null

            try (PrintWriter out = response.getWriter()) {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Basic Test App</title>");
                out.println("</head>");
                out.println("<body>");
                if (name != null) {
                    out.println("<h2>Hello " + name + "! How are you?</h2>");
                } else {
                    out.println("<p>Include the name parameter in the url so I can greet you!</p>");
                    out.println("<p>...HelloParameter?name=...</p>");
                }
                out.println("</body>");
                out.println("</html>");
            }
        }
    }
}