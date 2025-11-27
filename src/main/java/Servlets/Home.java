package Servlets;  // ← Paquete OBLIGATORIO

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")  // ← Ruta de tu servlet
public class Home extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setAttribute("mensaje", "¡Hola desde Java!");
        request.getRequestDispatcher("/webapp/web/HOME.jsp").forward(request, response);
    }
}
