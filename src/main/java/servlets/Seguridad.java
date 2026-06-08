package servlets;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Clase auxiliar encargada de gestionar la seguridad de las contraseñas.
 * 
 * Proporciona métodos para cifrar contraseñas mediante BCrypt y para comprobar
 * si una contraseña en texto plano coincide con una contraseña previamente
 * cifrada.
*/

public class Seguridad {

    /**
     * Genera el hash de una contraseña en texto plano.
     * 
     * Utiliza el algoritmo BCrypt junto con una sal generada automáticamente.
     * El valor 12 indica el coste del algoritmo, aumentando la dificultad
     * computacional del proceso de cifrado.
     * 
     * @param passwordPlano contraseña introducida por el usuario en texto plano.
     * @return contraseña cifrada mediante BCrypt.
    */
    
    public static String hashearPassword(String passwordPlano){
        return BCrypt.hashpw(passwordPlano, BCrypt.gensalt(12));
    }

    /**
     * Verifica si una contraseña en texto plano coincide con su hash almacenado.
     * 
     * Comprueba que ninguno de los valores recibidos sea nulo y utiliza BCrypt
     * para comparar la contraseña introducida con la contraseña cifrada guardada
     * en la base de datos.
     * 
     * @param passwordPlano contraseña introducida por el usuario en texto plano.
     * @param passwordHash contraseña cifrada almacenada en la base de datos.
     * @return true si la contraseña coincide con el hash, false en caso contrario.
    */

    public static boolean verificarPassword(String passwordPlano, String passwordHash){
        if(passwordPlano == null || passwordHash == null){
            return false;
        } 
        return BCrypt.checkpw(passwordPlano, passwordHash);
    }
}
