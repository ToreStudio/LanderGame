/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbservlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.ResultadoService;

/**
 *
 * @author PEPE
 */
@WebServlet(name = "ServletResultado", urlPatterns = {"/ServletResultado"})
public class ServletResultado extends HttpServlet {

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

        EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();
        ResultadoService result = new ResultadoService(em);
        //se recupera el código del usuario y la puntuación obtenida
        String codi = (String) request.getParameter("codigo");
        String puntuacion = (String) request.getParameter("mostrarPunto");
        //se comprueba que la puntuación no sea numérica y se le asgna un valor 0
        if (puntuacion.contains("Maybe next time...")) {
            puntuacion = "0";
        }
        //se recuperan los valores de la fecha obtenidos por separados con 
        //javascript en el juego.jsp
        String iniciod = (String) request.getParameter("iniciod");
        int iniciodint = parseValue(iniciod);
        String iniciom = (String) request.getParameter("iniciom");
        int iniciomint = parseValue(iniciom);
        String inicioy = (String) request.getParameter("inicioy");
        int inicioyint = parseValue(inicioy);
        String inicioh = (String) request.getParameter("inicioh");
        int iniciohint = parseValue(inicioh);
        String iniciomi = (String) request.getParameter("iniciomi");
        int iniciomiint = parseValue(iniciomi);
        String inicios = (String) request.getParameter("inicios");
        int iniciosint = parseValue(inicios);
        String finald = (String) request.getParameter("finald");
        int finaldint = parseValue(finald);
        String finalm = (String) request.getParameter("finalm");
        int finalmint = parseValue(finalm);
        String finaly = (String) request.getParameter("finaly");
        int finalyint = parseValue(finaly);
        String finalh = (String) request.getParameter("finalh");
        int finalhint = parseValue(finalh);
        String finalmi = (String) request.getParameter("finalmi");
        int finalmiint = parseValue(finalmi);
        String finals = (String) request.getParameter("finals");
        int finalsint = parseValue(finals);
        
        
        
        Calendar inicio = fecha(iniciodint, iniciomint, inicioyint, iniciohint, iniciomiint, iniciosint);
        Calendar fin = fecha(finaldint, finalmint, finalyint, finalhint, finalmiint, finalsint);
        java.sql.Date dateinicio = new java.sql.Date(inicio.getTime().getTime());
        java.sql.Date datefin = new java.sql.Date(fin.getTime().getTime());
        //se parsea el resultado a int
        int resultadoFinal = result.getPuntuacion(puntuacion);
        //se parsea el código a int 
        int usecodi = Integer.parseInt(codi);
        //se almacenan los valores en la tabla resultados de la base de datos
        result.addresult(usecodi, resultadoFinal, dateinicio, datefin);
        //se devuelve el contenido de la consulta de la base de datos relacionado con el ranking 
        //de puntuación y con la última fecha de juego de los usuarios
        List<Object[]> rankingJuego = result.getRanking();
        List<Object[]> lastlogin = result.getOnline();
        List<Object[]> play = result.getTopPlayed();
        //se envian los valores a juego.jsp
        request.setAttribute("ranking", rankingJuego);
        request.setAttribute("onlinelast", lastlogin);
        request.setAttribute("played", play);
        RequestDispatcher a = request.getRequestDispatcher("juego.jsp");
        a.forward(request, response);
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

    //recibe los valores de la fecha por separado y los junta en un formato compatible con la base de datos
    public Calendar fecha(int d, int m, int y, int h, int mi, int s) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, y);
        calendar.set(Calendar.DAY_OF_MONTH, d);
        calendar.set(Calendar.MONTH, m); // Assuming you wanted May 1st
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, mi);
        calendar.set(Calendar.SECOND, s);
        return calendar;
    }

    public int parseValue(String cadena) {

        int number = Integer.parseInt(cadena);
        return number;
    }
}
