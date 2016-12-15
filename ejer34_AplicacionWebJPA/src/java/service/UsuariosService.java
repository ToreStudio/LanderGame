/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dbjuego.Toreusuarios;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author PEPE
 */
public class UsuariosService {

    protected EntityManager em;

    public UsuariosService(EntityManager em) {
        this.em = em;
    }

    public UsuariosService() {
    }

    //dado un usuario devuelvo su código
    public String coduser(String name) {

        String codigoString = "";
        Query qcode = em.createQuery("SELECT u.codigo FROM Toreusuarios u WHERE u.usuario=:name");
        qcode.setParameter("name", name);
        List<Integer> listCode = (List<Integer>) qcode.getResultList();
        for (int id : listCode) {
            codigoString = Integer.toString(id);
        }
        return codigoString;
    }

    //se introduce en la tabla usuarios de la base de datos los valores nombre y contraseña
    public void adduser(String nom, String pass) {

        Toreusuarios u = new Toreusuarios();
        EntityTransaction tx = em.getTransaction();
        u.setUsuario(nom);
        u.setPassword(pass);
        try {
            tx.begin();
            em.persist(u);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    //método que comprueba si el nombre y contraseña introducidos son correctos
    public boolean checkLogin(String name, String pass) {

        boolean flag = false;
        Query qname = em.createQuery("SELECT u.usuario FROM Toreusuarios u");
        Query qpass = em.createQuery("SELECT u.password FROM Toreusuarios u");
        List<String> listName = (List<String>) qname.getResultList();
        List<String> listPass = (List<String>) qpass.getResultList();
        for (Iterator<String> itn = listName.iterator(); itn.hasNext();) {
            if (itn.next().equals(name)) {
                for (String dbPass : listPass) {
                    if (BCrypt.checkpw(pass, dbPass)) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    //método que comprueba si el usuario introducido existe en la base de datos
    public boolean checkSignup(String name) {
        boolean flag = false;

        Query qname = em.createQuery("SELECT u.usuario FROM Toreusuarios u");
        List<String> usuarios = (List<String>) qname.getResultList();
        for (Iterator<String> it = usuarios.iterator(); it.hasNext();) {
            if (it.next().equals(name)) {
                flag = true;
            }
        }
        return flag;
    }
}
