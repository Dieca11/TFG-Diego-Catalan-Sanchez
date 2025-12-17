package servlets;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class AccesoBD {
	private static AccesoBD instanciaUnica = null;
	private Connection conexionBD = null;

	public static AccesoBD getInstance(){
		if (instanciaUnica == null){
			instanciaUnica = new AccesoBD();
		}
		return instanciaUnica;
	}

	private AccesoBD() {
		abrirConexionBD();
	}

	public void abrirConexionBD() {
		if (conexionBD == null)
		{
			String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
			// daw es el nombre de la base de datos que hemos creado con anterioridad.
			String DB_URL = "jdbc:mariadb://localhost:3306/tfg";
			// El usuario root y su clave son los que se puso al instalar MariaDB.
			String USER = "root";
			String PASS = "root";
			try {
				Class.forName(JDBC_DRIVER);
				conexionBD = DriverManager.getConnection(DB_URL,USER,PASS);
			}
			catch(Exception e) {
				System.err.println("No se ha podido conectar a la base de datos");
				System.err.println(e.getMessage());
                e.printStackTrace();
			}
		}
	}

	public boolean comprobarAcceso() {
		abrirConexionBD();
		return (conexionBD != null);
	}

	/*Espacio Reservado para la muestra y obtencion de datos de los Municipios */
	public List<MunicipioBD> obtenerMunicipiosBD(){
		abrirConexionBD();

		ArrayList<MunicipioBD> municipios = new ArrayList();

		try{
			String con = "SELECT id, municipio, imagen, map_iframe, num_pistas, activo FROM municipios";
			Statement s = conexionBD.createStatement();
			ResultSet resultado = s.executeQuery(con);

			while(resultado.next()){
				MunicipioBD municipio = new MunicipioBD();
				municipio.setId(resultado.getInt("id"));
				municipio.setMunicipio(resultado.getString("municipio"));
				municipio.setImagen(resultado.getString("imagen"));
				municipio.setMap_iframe(resultado.getString("map_iframe"));
				municipio.setNum_pistas(resultado.getInt("num_pistas"));
				municipio.setActivo(resultado.getBoolean("activo"));
				municipios.add(municipio);
			}
		}
		catch(Exception e) {
			System.err.println("Error ejecutando la consulta a la base de datos");
			System.err.println(e.getMessage());
		}

	return municipios;
	}

	public MunicipioBD obtenerMunicipioBD( int idMunicipio){
		abrirConexionBD();
		MunicipioBD municipio = null;

		try{
			String con = "SELECT id, municipio, imagen, map_iframe, num_pistas, activo FROM municipios where id = ?";
			  PreparedStatement ps = conexionBD.prepareStatement(con);
       			ps.setInt(1, idMunicipio);

        	ResultSet resultado = ps.executeQuery();

			if(resultado.next()){
				municipio = new MunicipioBD();
				municipio.setId(resultado.getInt("id"));
				municipio.setMunicipio(resultado.getString("municipio"));
				municipio.setImagen(resultado.getString("imagen"));
				municipio.setMap_iframe(resultado.getString("map_iframe"));
				municipio.setNum_pistas(resultado.getInt("num_pistas"));
				municipio.setActivo(resultado.getBoolean("activo"));
			}
		}
		catch(Exception e) {
			System.err.println("Error ejecutando la consulta a la base de datos");
			System.err.println(e.getMessage());
		}

	return municipio;
	}

	/*Espacio reservado para la comprobacion, modificacion y obtencion de los usuarios */
	public int comprobarUsuarioBD(String usuario, String clave) {
		abrirConexionBD();

		int id = -1;

		try{
			String con = "SELECT id FROM usuarios WHERE nombre_usuario=? AND contrasena=?";
			PreparedStatement s = conexionBD.prepareStatement(con);
			s.setString(1,usuario);
			s.setString(2,clave);

			ResultSet resultado = s.executeQuery();

			// El usuario/clave se encuentra en la BD

			if ( resultado.next() ) {
				id =  resultado.getInt("id");
			}
		}
		catch(Exception e) {

			// Error en la conexión con la BD
			System.err.println("Error verificando usuario/contraseña");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		return id;
	}

	public UsuarioBD obtenerUsuarioPorCodigo(int codigo) {
		abrirConexionBD();
		UsuarioBD u = null;

		String con = "SELECT * FROM usuarios WHERE id = ?";
		try  
		{
			PreparedStatement s = conexionBD.prepareStatement(con);
			s.setInt(1, codigo);
			ResultSet resultado = s.executeQuery();

			if (resultado.next()) {
				u = new UsuarioBD();
				u.setId(resultado.getInt("id"));
				u.setUsuario(resultado.getString("nombre_usuario"));
				u.setEmail(resultado.getString("email"));
				u.setContraseña(resultado.getString("contrasena"));
				u.setFoto_perfil(resultado.getString("imagen_perfil"));
				u.setMostrar_partidas(resultado.getBoolean("mostrar_partidas"));
				u.setRecibir_invitacion(resultado.getBoolean("recibir_invitaciones"));
				u.setTarjeta(resultado.getString("tarjeta_credito"));
			}
		}	catch(Exception e) {

			// Error en la conexión con la BD
			System.err.println("Error verificando usuario/contraseña");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return u;
	}

	public boolean existeUsuOEmail(int id, String usu, String email){

		abrirConexionBD();
		    
		String con = "SELECT COUNT(*) AS total " +
                 "FROM usuarios " +
                 "WHERE (nombre_usuario = ? OR email = ?) " +
                 "AND id <> ?";
		try{
			PreparedStatement s = conexionBD.prepareStatement(con);
			
			s.setString(1, usu);
			s.setString(2, email);
			s.setInt(3, id);
			ResultSet result = s.executeQuery();

			if(result.next()){
				int total = result.getInt("total");
				return total > 0;
			}
		}catch(Exception e) {
			// Error en la conexión con la BD
			System.err.println("Existe ese usuario/email");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	public void ActualizarUsuario(Integer id,String nombre_usu,String email,
		String imagen,boolean invitacion,boolean partidas, String clave1,String tarjeta){
			abrirConexionBD();

			StringBuilder con = new StringBuilder( 
					"UPDATE usuarios SET " +
                 	"nombre_usuario = ?, " +
                 	"email = ?, " +
                 	"mostrar_partidas = ?, " +
                 	"recibir_invitaciones = ?, " +
                 	"tarjeta_credito = ? ");

			if (clave1 != null && !clave1.trim().isEmpty()) {
        		con.append(", contrasena = ? ");
    		}

			if(imagen != null && !imagen.trim().isEmpty()){
				con.append(", imagen_perfil = ? ");
			}

			con.append(" WHERE id = ?");

			try (PreparedStatement s = conexionBD.prepareStatement(con.toString())) {

				int i = 1;
				s.setString(i++, nombre_usu);
				s.setString(i++, email);
				s.setBoolean(i++, partidas);
				s.setBoolean(i++, invitacion);
				s.setString(i++, tarjeta);

				if(clave1 !=null && !clave1.trim().isEmpty()){
					s.setString(i++, clave1);
				}

				if(imagen!=null && !imagen.trim().isEmpty()){
					s.setString(i++, imagen);
				}

				s.setInt(i, id);

				s.executeUpdate();


			} catch (Exception e) {
			System.err.println("No ha sido posible modificar tus datos");
			System.err.println(e.getMessage());
			e.printStackTrace();
			}
		}

    public void RegistarUsuario(String nombreUsuario, String email, String clave1) {
		abrirConexionBD();
       String con = "INSERT INTO usuarios (nombre_usuario, email, contrasena) VALUES ( ?,?,?)";

	   try{
		PreparedStatement s = conexionBD.prepareStatement(con);
		s.setString(1, nombreUsuario);
		s.setString(2, email);
		s.setString(3, clave1);

		s.executeQuery();
	   }catch (Exception e) {
			System.err.println("No ha sido posible anaydir al usuario");
			System.err.println(e.getMessage());
			e.printStackTrace();
			}
    }
};
