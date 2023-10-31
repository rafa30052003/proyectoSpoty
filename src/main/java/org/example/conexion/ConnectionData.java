package org.example.conexion;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "conexion")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConnectionData implements Serializable{//HACEMOS LA CLASE SERIALIZABLE

    private static final long serialVersionUID = 1L;
    private String server;
    private String database;
    private String username;
    private String password;

    //CONSTRUCTOR VACIO
    public ConnectionData() {
        super();
        this.server = "";
        this.database = "";
        this.username = "";
        this.password = "";
    }

    /*
     * GETTER AND SETTER
     */
    public String getServer() {
        return server;
    }
    public void setServer(String server) {
        this.server = server;
    }
    public String getDatabase() {
        return database;
    }
    public void setDatabase(String database) {
        this.database = database;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "ConnectionData [server=" + server + ", database=" + database + ", username=" + username + ", password="
                + password + "]";
    }




}
