/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbservlet;

import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import service.ResultadoService;
import service.UsuariosService;

/**
 *
 * @author PEPE
 */
public class DBServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();
        UsuariosService us = new UsuariosService(em);
        ResultadoService result = new ResultadoService(em);
        //recuperamos los valores de usuario y contraseña para hacer las comprobaciones con la base de datos
        String ulog = request.getParameter("userlog");
        String plog = request.getParameter("passlog");
        //String encriptedPass=BCrypt.hashpw(plog, BCrypt.gensalt());
        //devuelve true cuando el usuario y contraseña introducidos en el login 
        //son iguales a los datos de la base de datos
        if (us.checkLogin(ulog, plog) == true) {
            //se crean las cookie con los valores de usuario y contraseña
            Cookie name = new Cookie("name", ulog);
            //Cookie pass = new Cookie("pass", plog);
            //see recupera el id del usuario introducido y se almacena el id en las cookie
            String codigoString = us.coduser(ulog);
            Cookie iduser = new Cookie("iduser", codigoString);
            name.setMaxAge(60 * 20);//expire 20minutos
            //pass.setMaxAge(60 * 20);
            iduser.setMaxAge(60 * 20);
            response.addCookie(name);
            //response.addCookie(pass);
            response.addCookie(iduser);
            //Cookie[] arrGalletas = request.getCookies();
            //arraylist de objetos ranking y online para mostrar en las tablas de puntuacion y last login
            List<Object[]> rankingJuego = result.getRanking();
            List<Object[]> lastlogin = result.getOnline();
            List<Object[]> play = result.getTopPlayed();
            //se envian los valores al juego.jsp 
            request.setAttribute("name", ulog);
            request.setAttribute("ranking", rankingJuego);
            request.setAttribute("onlinelast", lastlogin);
            request.setAttribute("played", play);
            RequestDispatcher a = request.getRequestDispatcher("juego.jsp");
            a.forward(request, response);
        } else {
            //de ser contraseña o usuario incorrectos se devuelve un mensaje de error
            String respuesta = "User name or password are wrong";
            request.setAttribute("respuesta", respuesta);
            RequestDispatcher a = request.getRequestDispatcher("error.jsp");
            a.forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();
        UsuariosService us = new UsuariosService(em);
        //se recuperan los valores de usuario y contraseña intoducidos en el registro 
        String ulog = request.getParameter("usersign");
        String plog = request.getParameter("passsign");
        String encryptedPass = BCrypt.hashpw(plog, BCrypt.gensalt());
        //se comprueba que el usuario introducido no exista en la base de datos
        if (us.checkSignup(ulog) == false) {
            //se crean los valores usuario y contraseña en la tabla usuarios de la base de datos
            us.adduser(ulog, encryptedPass);
            //se envía un mensaje para que el usuario sepa que se ha registrado
            String respuesta = "The user has been registered";
            request.setAttribute("respuesta", respuesta);
            RequestDispatcher a = request.getRequestDispatcher("error.jsp");
            a.forward(request, response);
        } else {
            //de existir el usuario introducido se envía un mensaje de error
            String respuesta = "The user is already registered";
            request.setAttribute("respuesta", respuesta);
            RequestDispatcher a = request.getRequestDispatcher("error.jsp");
            a.forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
