/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Web application lifecycle listener.
 *
 * @author PEPE
 */
@WebListener()
public class DBListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        System.out.println("Context Initialized");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ejer34_AplicacionWebJPAPU");
        arg0.getServletContext().setAttribute("emf", emf);
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

        System.out.println("Context Destroyed");
        EntityManagerFactory emf = (EntityManagerFactory) arg0.getServletContext().getAttribute("emf");
        emf.close();
    }
}
