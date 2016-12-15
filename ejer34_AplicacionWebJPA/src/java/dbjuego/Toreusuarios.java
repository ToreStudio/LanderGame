/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbjuego;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PEPE
 */
@Entity
@Table(name = "toreusuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Toreusuarios.findAll", query = "SELECT t FROM Toreusuarios t"),
    @NamedQuery(name = "Toreusuarios.findByCodigo", query = "SELECT t FROM Toreusuarios t WHERE t.codigo = :codigo"),
    @NamedQuery(name = "Toreusuarios.findByUsuario", query = "SELECT t FROM Toreusuarios t WHERE t.usuario = :usuario"),
    @NamedQuery(name = "Toreusuarios.findByPassword", query = "SELECT t FROM Toreusuarios t WHERE t.password = :password")})
public class Toreusuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "codigoUsuarios")
    private Collection<Toreresultados> toreresultadosCollection;

    public Toreusuarios() {
    }

    public Toreusuarios(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public Collection<Toreresultados> getToreresultadosCollection() {
        return toreresultadosCollection;
    }

    public void setToreresultadosCollection(Collection<Toreresultados> toreresultadosCollection) {
        this.toreresultadosCollection = toreresultadosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Toreusuarios)) {
            return false;
        }
        Toreusuarios other = (Toreusuarios) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dbjuego.Toreusuarios[ codigo=" + codigo + " ]";
    }
    
}
