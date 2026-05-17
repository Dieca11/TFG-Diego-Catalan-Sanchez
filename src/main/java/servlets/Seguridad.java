package servlets;

import org.mindrot.jbcrypt.BCrypt;

public class Seguridad {
    
    public static String hashearPassword(String passwordPlano){
        return BCrypt.hashpw(passwordPlano, BCrypt.gensalt(12));
    }

    public static boolean verificarPassword(String passwordPlano, String passwordHash){
        if(passwordPlano == null || passwordHash == null){
            return false;
        } 
        return BCrypt.checkpw(passwordPlano, passwordHash);
    }
}
