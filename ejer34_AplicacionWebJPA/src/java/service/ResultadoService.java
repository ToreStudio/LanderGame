/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import dbjuego.Toreresultados;
import dbjuego.Toreusuarios;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author PEPE
 */
public class ResultadoService {

    protected EntityManager em;

    public ResultadoService(EntityManager em) {
        this.em = em;
    }

    public ResultadoService() {
    }

    //regular expression \d representa caracteres que son dígitos
    //recibe un string puntuacion con caracteres alfanuméricos y dígitos
    //Pattern encuentra los caracteres dígitos
    //Matcher interpreta el patrón creado por la clase Pattern y mediante el método 
    //matcher() devuelve los valores que encajan con el patrón
    //el bucle while lee el Matcher y mediante el método find encuentra la siguiente secuencia que
    //encaje en con el patrón
    public int getPuntuacion(String puntuacion) {

        String cadena = "";
        int resultadoFinal = 0;
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(puntuacion);
        while (m.find()) {
            cadena = cadena + m.group(1);
        }
        try {
            resultadoFinal = Integer.parseInt(cadena);
        } catch (NumberFormatException e) {
        }
        return resultadoFinal;
    }

    //método que introduce en la tabla resultado los valores de código usuario, puntuación y fecha inicio y final
    public void addresult(int usecode, int point, java.sql.Date dateinicio, java.sql.Date datefin) {

        Toreusuarios u = new Toreusuarios();
        Toreresultados a = new Toreresultados();
        EntityTransaction tx = em.getTransaction();
        u.setCodigo(usecode);
        a.setCodigoUsuarios(u);
        a.setPuntuacion(point);
        a.setFechaInicio(dateinicio);
        a.setFechaFinal(datefin);
        System.out.println("fecha inicio "+dateinicio);
        try {
            tx.begin();
            em.persist(a);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    //método que mediante una consulta que devuelve de la base de datos 
    //un arraylist con con los valores de usuarios y sus últimos logins 
    public List<Object[]> getOnline() {
        
        Query q = em.createNativeQuery("select distinct  usuario as player,  min(age(now(),fecha_final)) as last_login \n"
                + "                from public.toreresultados, public.toreusuarios\n"
                + "                where codigo=codigo_usuarios\n"
                + "                group by usuario\n"
                + "                order by min(age(now(),fecha_final)) asc limit 5;");

        
        List<Object[]> results = q.getResultList();
//        for (Object[] elements : results) {
//            ResultadoQuery rq = new ResultadoQuery();
//            String name = String.valueOf(elements[0]);
//            String time = String.valueOf(elements[1]);
//            rq.setPlayer(name);
//            rq.setLastLogin(time);
//            lista.add(rq);
//        }
        return results;
    }

//método que mediante una consulta que devuelve de la base de datos 
//un arraylist con con los valores de usuarios ypuntuación 
    public List<Object[]> getRanking() {

        Query q = em.createNativeQuery("SELECT usuario as PLAYERS, puntuacion as POINTS\n"
                + "                   FROM public.toreusuarios, public.toreresultados  \n"
                + "                   WHERE puntuacion = (\n"
                + "                               SELECT MIN(puntuacion)\n"
                + "                               FROM public.toreresultados\n"
                + "                               WHERE codigo = codigo_usuarios and puntuacion>0\n"
                + "                               )\n"
                + "                    group by puntuacion, usuario\n"
                + "                  order by puntuacion asc limit 10;");

        List<Object[]> results = q.getResultList();

        return results;
    }

    //método que mediante una consulta que devuelve de la base de datos 
//un arraylist con con los valores de usuarios que más han jugado 
    public List<Object[]> getTopPlayed() {

        Query q = em.createNativeQuery("SELECT  usuario, count(puntuacion)\n"
                + "  FROM public.toreresultados, public.toreusuarios\n"
                + "  WHERE codigo=codigo_usuarios\n"
                + "  GROUP BY usuario\n"
                + "  ORDER BY count(puntuacion) desc limit 10;");
        List<Object[]> results = q.getResultList();
//        for (Object[] elements : results) {
//            ResultadoQuery rq = new ResultadoQuery();
//            String name = String.valueOf(elements[0]);
//            String scount = String.valueOf(elements[1]);
//            int icount = Integer.parseInt(scount);
//            rq.setPlayer(name);
//            rq.setCount(icount);
//            lista.add(rq);
//        }
        return results;
    }
}
