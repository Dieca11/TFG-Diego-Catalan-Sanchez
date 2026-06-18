package servlets;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Clase encargada de centralizar el acceso a la base de datos de la aplicación.
 * 
 * Implementa un patrón Singleton para mantener una única instancia de conexión
 * y proporciona métodos para gestionar usuarios, municipios, reservas, partidas,
 * perfil de usuario y clasificación.
*/

public final class AccesoBD {

	private static AccesoBD instanciaUnica = null;
	private Connection conexionBD = null;

	public static AccesoBD getInstance(){
		if (instanciaUnica == null){
			instanciaUnica = new AccesoBD();
		}
		return instanciaUnica;
	}


	/**
	 * Constructor privado de la clase.
	 * 
	 * Evita que se puedan crear instancias de AccesoBD desde otras clases
	 * y abre la conexión con la base de datos al inicializar el objeto.
	*/

	private AccesoBD() {
		abrirConexionBD();
	}

	/**
	 * Abre la conexión con la base de datos MariaDB si todavía no existe.
	 * 
	 * Carga el driver JDBC, establece la conexión con la base de datos del proyecto
	 * y almacena dicha conexión para ser reutilizada por el resto de métodos.
	*/

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

	/**
	 * Comprueba si existe conexión activa con la base de datos.
	 * 
	 * Antes de realizar la comprobación intenta abrir la conexión si todavía
	 * no ha sido inicializada.
	 * 
	 * @return true si la conexión con la base de datos está disponible, false en caso contrario.
	*/

	public boolean comprobarAcceso() {
		abrirConexionBD();
		return (conexionBD != null);
	}

	/**
	 * Obtiene la lista de municipios registrados en la base de datos.
	 * 
	 * Recupera los datos principales de cada municipio, incluyendo su identificador,
	 * nombre, imagen, mapa, número de pistas y estado de activación.
	 * 
	 * @return lista de municipios almacenados en la base de datos.
	*/

	public List<MunicipioBD> obtenerMunicipiosBD(){
		abrirConexionBD();

		ArrayList<MunicipioBD> municipios = new ArrayList<>();

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


	/**
	 * Obtiene la información de un municipio concreto.
	 * 
	 * Busca en la base de datos el municipio cuyo identificador coincide con el
	 * valor recibido y devuelve sus datos principales.
	 * 
	 * @param idMunicipio identificador del municipio que se desea consultar.
	 * @return objeto MunicipioBD con los datos del municipio, o null si no existe.
	*/

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


	/**
	 * Comprueba las credenciales de inicio de sesión de un usuario.
	 * 
	 * Busca el usuario por su nombre de usuario y compara la contraseña introducida
	 * con el hash almacenado en la base de datos mediante BCrypt.
	 * 
	 * @param usuario nombre de usuario introducido en el formulario de inicio de sesión.
	 * @param clave contraseña en texto plano introducida por el usuario.
	 * @return identificador del usuario si las credenciales son correctas, o -1 si no lo son.
	*/
	
	public int comprobarUsuarioBD(String usuario, String clave) {
		abrirConexionBD();

		int id = -1;

		try{
			String con = "SELECT id,contrasena FROM usuarios WHERE nombre_usuario=?";
			PreparedStatement s = conexionBD.prepareStatement(con);
			s.setString(1,usuario);

			ResultSet resultado = s.executeQuery();

			// El usuario/clave se encuentra en la BD

			if ( resultado.next() ) {
				String hashBD= resultado.getString("contrasena");
			
				if(Seguridad.verificarPassword(clave, hashBD))
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

	/**
	 * Obtiene los datos completos de un usuario a partir de su identificador.
	 * 
	 * Recupera la información personal del usuario, incluyendo nombre, correo,
	 * contraseña cifrada, imagen de perfil, preferencias de privacidad e información
	 * de tarjeta.
	 * 
	 * @param codigo identificador del usuario que se desea consultar.
	 * @return objeto UsuarioBD con los datos del usuario, o null si no existe.
	*/

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

	/**
	 * Comprueba si ya existe otro usuario con el mismo nombre de usuario o correo electrónico.
	 * 
	 * Se utiliza tanto en el registro como en la actualización del perfil. El parámetro
	 * id permite excluir al propio usuario durante una modificación de datos.
	 * 
	 * @param id identificador del usuario actual, o 0 si se trata de un nuevo registro.
	 * @param usu nombre de usuario que se desea comprobar.
	 * @param email correo electrónico que se desea comprobar.
	 * @return true si existe otro usuario con ese nombre o correo, false en caso contrario.
	*/

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

	/**
	 * Actualiza los datos personales de un usuario.
	 * 
	 * Modifica el nombre de usuario, correo electrónico, preferencias de invitaciones,
	 * visibilidad de partidas y tarjeta. Si se recibe una contraseña o imagen nueva,
	 * también actualiza esos campos.
	 * 
	 * @param id identificador del usuario que se desea actualizar.
	 * @param nombre_usu nuevo nombre de usuario.
	 * @param email nuevo correo electrónico.
	 * @param imagen nueva imagen de perfil, si se desea modificar.
	 * @param invitacion indica si el usuario permite recibir invitaciones.
	 * @param partidas indica si el usuario permite mostrar sus partidas en la clasificación.
	 * @param clave1 nueva contraseña cifrada, si se desea modificar.
	 * @param tarjeta tarjeta asociada al usuario.
	*/

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

	/**
	 * Registra un nuevo usuario en la base de datos.
	 * 
	 * Inserta el nombre de usuario, correo electrónico y contraseña cifrada
	 * proporcionados durante el proceso de registro.
	 * 
	 * @param nombreUsuario nombre de usuario del nuevo usuario.
	 * @param email correo electrónico del nuevo usuario.
	 * @param clave1 contraseña cifrada del nuevo usuario.
	*/

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

	/**
	 * Obtiene los identificadores de los usuarios asociados a una reserva concreta.
	 * 
	 * Busca una reserva por municipio, pista, fecha y hora, siempre que no esté
	 * cancelada, y devuelve las cuatro posiciones posibles: creador e invitados.
	 * 
	 * @param municipioId identificador del municipio de la reserva.
	 * @param numeroPista número de pista reservada.
	 * @param fechaHora fecha y hora de la reserva.
	 * @return array con los identificadores del creador e invitados.
	 * @throws SQLException si ocurre un error al consultar la base de datos.
	*/

	public Integer[] obtenerReserva(int municipioId, int numeroPista, LocalDateTime fechaHora) throws SQLException {

		abrirConexionBD();
		Integer[] usuarios = new Integer[4];

		try { 
			String con = "SELECT creador_id, invitado1_id, invitado2_id, invitado3_id" +
			" FROM reservas WHERE municipio_id = ? AND numero_pista = ? AND fecha_hora = ? AND estado <> 'cancelada' " +
			"LIMIT 1";

			PreparedStatement ps = conexionBD.prepareStatement(con);

			ps.setInt(1, municipioId);
			ps.setInt(2, numeroPista);
			ps.setTimestamp(3, Timestamp.valueOf(fechaHora));
			ResultSet rs = ps.executeQuery();
            if (rs.next()){

            usuarios[0]= rs.getInt("creador_id");

            usuarios[1]= rs.getInt("invitado1_id");
            usuarios[2]= rs.getInt("invitado2_id");
            usuarios[3] = rs.getInt("invitado3_id");
			}
		}
		catch(Exception e) {
			System.err.println("Error ejecutando la consulta a la base de datos");
			System.err.println(e.getMessage());
		}
            return usuarios;
    }

	/**
	 * Clase auxiliar utilizada para representar los datos visibles de un usuario.
	 * 
	 * Contiene únicamente la información necesaria para mostrar usuarios en la
	 * interfaz: identificador, nombre de usuario e imagen de perfil.
	*/

	public static class UsuarioVista {
		private int id;
		private String nombreUsuario;
		private String imagenPerfil;

		/**
		 * Constructor vacío de UsuarioVista.
		 * 
		 * Permite crear el objeto y asignar sus valores posteriormente mediante métodos setter.
		*/

		public UsuarioVista() {}

		/**
		 * Constructor de UsuarioVista con todos sus campos principales.
		 * 
		 * @param id identificador del usuario.
		 * @param nombreUsuario nombre visible del usuario.
		 * @param imagenPerfil ruta de la imagen de perfil del usuario.
		*/

		public UsuarioVista(int id, String nombreUsuario, String imagenPerfil) {
			this.id = id;
			this.nombreUsuario = nombreUsuario;
			this.imagenPerfil = imagenPerfil;
		}

		public int getId() { return id; }
		public void setId(int id) { this.id = id; }

		public String getNombreUsuario() { return nombreUsuario; }
		public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

		public String getImagenPerfil() { return imagenPerfil; }
		public void setImagenPerfil(String imagenPerfil) { this.imagenPerfil = imagenPerfil; }
	}


	/**
	 * Obtiene los datos visibles de un usuario por su identificador.
	 * 
	 * Recupera únicamente el identificador, nombre de usuario e imagen de perfil,
	 * que son los datos necesarios para mostrar participantes en la interfaz.
	 * 
	 * @param userId identificador del usuario.
	 * @return objeto UsuarioVista con los datos visibles, o null si no existe.
	 * @throws SQLException si ocurre un error al consultar la base de datos.
	*/

	public UsuarioVista obtenerUsuarioVistaPorId(int userId) throws SQLException {

		if (userId <= 0) return null;

		abrirConexionBD();

		String sql = "SELECT id, nombre_usuario, imagen_perfil FROM usuarios WHERE id = ? LIMIT 1";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, userId);

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) return null;

				UsuarioVista u = new UsuarioVista();
				u.setId(rs.getInt("id"));
				u.setNombreUsuario(rs.getString("nombre_usuario"));
				u.setImagenPerfil(rs.getString("imagen_perfil"));
				return u;
			}
		}
	}


	/**
	 * Obtiene la lista de usuarios que aceptan recibir invitaciones.
	 * 
	 * Recupera los usuarios que tienen activada la opción de recibir invitaciones
	 * y los ordena alfabéticamente por nombre de usuario.
	 * 
	 * @return lista de usuarios disponibles para ser invitados.
	 * @throws SQLException si ocurre un error al consultar la base de datos.
	*/

	public ArrayList<UsuarioVista> obtenerUsuariosInvitables() throws SQLException {

		abrirConexionBD();
		ArrayList<UsuarioVista> lista = new ArrayList<>();

		String sql =
			"SELECT id, nombre_usuario, imagen_perfil " +
			"FROM usuarios " +
			"WHERE recibir_invitaciones = TRUE " +
			"ORDER BY nombre_usuario ASC";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				UsuarioVista u = new UsuarioVista();
				u.setId(rs.getInt("id"));
				u.setNombreUsuario(rs.getString("nombre_usuario"));
				u.setImagenPerfil(rs.getString("imagen_perfil"));
				lista.add(u);
			}
		}

		return lista;
	}

	/**
	 * Obtiene el nombre de usuario asociado a un identificador.
	 * 
	 * Se utiliza para mostrar los nombres de los participantes en reservas,
	 * partidas e historial de usuario.
	 * 
	 * @param idUsuario identificador del usuario.
	 * @return nombre de usuario correspondiente, o null si no existe.
	 * @throws SQLException si ocurre un error al consultar la base de datos.
	*/

	public String obtenerNombreUsuarioPorId(int idUsuario) throws SQLException {
		abrirConexionBD();

		String nombre = null;
		String sql = "SELECT nombre_usuario FROM usuarios WHERE id = ?";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					nombre = rs.getString("nombre_usuario");
				}
			}
		}
		return nombre;
	}

	/**
	 * Enumeración que representa el resultado de intentar crear o unirse a una reserva.
	 * 
	 * CREADA indica que se ha creado una nueva reserva.
	 * UNIDO indica que el usuario se ha unido a una reserva existente.
	 * YA_ESTAS indica que el usuario ya forma parte de esa reserva.
	 * LLENA indica que la reserva no tiene huecos disponibles.
	*/

	public enum ResultadoReserva {
		CREADA, UNIDO, YA_ESTAS, LLENA
	}

	/**
	 * Crea una nueva reserva o une al usuario a una reserva existente.
	 * 
	 * Primero comprueba si existe una reserva pendiente para el municipio, pista,
	 * fecha y hora indicados. Si existe, intenta añadir al usuario como invitado
	 * siempre que no forme ya parte de la reserva y haya huecos disponibles.
	 * 
	 * Si no existe una reserva pendiente, comprueba si hay una reserva cancelada
	 * para la misma franja. En ese caso, la reactiva asignando el nuevo creador
	 * y las invitaciones iniciales. Si tampoco existe una cancelada, crea una
	 * nueva reserva desde cero.
	 * 
	 * La operación se realiza dentro de una transacción para evitar inconsistencias
	 * en caso de accesos concurrentes.
	 * 
	 * @param municipioId identificador del municipio donde se realiza la reserva.
	 * @param numeroPista número de pista seleccionada.
	 * @param fechaHora fecha y hora de inicio de la reserva.
	 * @param usuarioSesion identificador del usuario que realiza la operación.
	 * @param invitacionesIniciales lista de usuarios invitados inicialmente.
	 * @return resultado de la operación de reserva.
	 * @throws SQLException si ocurre un error durante la operación en base de datos.
	*/

	public ResultadoReserva crearOUnirseReserva(
        int municipioId, int numeroPista, LocalDateTime fechaHora,
        int usuarioSesion, java.util.List<Integer> invitacionesIniciales
) throws SQLException {

    abrirConexionBD();
    conexionBD.setAutoCommit(false);

    try {
        // 1) Intentar localizar reserva existente pendiente
        String sel = "SELECT id, creador_id, invitado1_id, invitado2_id, invitado3_id " +
                    "FROM reservas " +
                    "WHERE municipio_id=? AND numero_pista=? AND fecha_hora=? AND estado='pendiente' " +
                    "LIMIT 1 FOR UPDATE";

        Integer reservaId = null;
        Integer creadorId = null;
        Integer inv1 = null, inv2 = null, inv3 = null;

        try (PreparedStatement ps = conexionBD.prepareStatement(sel)) {
            ps.setInt(1, municipioId);
            ps.setInt(2, numeroPista);
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(fechaHora));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    reservaId = rs.getInt("id");
                    creadorId = rs.getInt("creador_id");
                    inv1 = (Integer) rs.getObject("invitado1_id");
                    inv2 = (Integer) rs.getObject("invitado2_id");
                    inv3 = (Integer) rs.getObject("invitado3_id");
                }
            }
        }

        // 2) Normalizar invitaciones iniciales
        java.util.ArrayList<Integer> invs = new java.util.ArrayList<>();

        if (invitacionesIniciales != null) {
            for (Integer x : invitacionesIniciales) {
                if (x == null) {
                    continue;
                }

                if (x == usuarioSesion) {
                    continue;
                }

                if (!invs.contains(x)) {
                    invs.add(x);
                }

                if (invs.size() == 3) {
                    break;
                }
            }
        }

        Integer i1 = invs.size() >= 1 ? invs.get(0) : null;
        Integer i2 = invs.size() >= 2 ? invs.get(1) : null;
        Integer i3 = invs.size() >= 3 ? invs.get(2) : null;

		// 3) Si NO existe reserva pendiente, comprobar si existe una cancelada
        if (reservaId == null) {

            String selCancelada = "SELECT id " +
                                "FROM reservas " +
                                "WHERE municipio_id=? AND numero_pista=? AND fecha_hora=? AND estado='cancelada' " +
                                "LIMIT 1 FOR UPDATE";

            Integer reservaCanceladaId = null;

            try (PreparedStatement ps = conexionBD.prepareStatement(selCancelada)) {
                ps.setInt(1, municipioId);
                ps.setInt(2, numeroPista);
                ps.setTimestamp(3, java.sql.Timestamp.valueOf(fechaHora));

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        reservaCanceladaId = rs.getInt("id");
                    }
                }
            }

            // 4) Si existe cancelada, se reactiva
            if (reservaCanceladaId != null) {

                String updCancelada = "UPDATE reservas SET " +
                                    "creador_id=?, " +
                                    "invitado1_id=?, " +
                                    "invitado2_id=?, " +
                                    "invitado3_id=?, " +
                                    "estado='pendiente' " +
                                    "WHERE id=?";

                try (PreparedStatement ps = conexionBD.prepareStatement(updCancelada)) {
                    ps.setInt(1, usuarioSesion);

                    if (i1 == null) {
                        ps.setNull(2, java.sql.Types.INTEGER);
                    } else {
                        ps.setInt(2, i1);
                    }

                    if (i2 == null) {
                        ps.setNull(3, java.sql.Types.INTEGER);
                    } else {
                        ps.setInt(3, i2);
                    }

                    if (i3 == null) {
                        ps.setNull(4, java.sql.Types.INTEGER);
                    } else {
                        ps.setInt(4, i3);
                    }

                    ps.setInt(5, reservaCanceladaId);
                    ps.executeUpdate();
                }

                conexionBD.commit();
                return ResultadoReserva.CREADA;
            }

            // 5) Si no existe ni pendiente ni cancelada, se crea una nueva
            String ins = "INSERT INTO reservas " +
                        "(municipio_id, numero_pista, creador_id, invitado1_id, invitado2_id, invitado3_id, fecha_hora, estado) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, 'pendiente')";

            try (PreparedStatement ps = conexionBD.prepareStatement(ins)) {
                ps.setInt(1, municipioId);
                ps.setInt(2, numeroPista);
                ps.setInt(3, usuarioSesion);

                if (i1 == null) {
                    ps.setNull(4, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(4, i1);
                }

                if (i2 == null) {
                    ps.setNull(5, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(5, i2);
                }

                if (i3 == null) {
                    ps.setNull(6, java.sql.Types.INTEGER);
                } else {
                    ps.setInt(6, i3);
                }

                ps.setTimestamp(7, java.sql.Timestamp.valueOf(fechaHora));
                ps.executeUpdate();
            }

            conexionBD.commit();
            return ResultadoReserva.CREADA;
        }

        // 6) Si existe reserva pendiente: comprobar si el usuario ya forma parte
        if (usuarioSesion == creadorId ||
            (inv1 != null && inv1 == usuarioSesion) ||
            (inv2 != null && inv2 == usuarioSesion) ||
            (inv3 != null && inv3 == usuarioSesion)) {

            conexionBD.commit();
            return ResultadoReserva.YA_ESTAS;
        }

        // 7) Si hay hueco, el usuario se une a la reserva pendiente
        if (inv1 == null) {
            String upd = "UPDATE reservas SET invitado1_id=? WHERE id=?";

            try (PreparedStatement ps = conexionBD.prepareStatement(upd)) {
                ps.setInt(1, usuarioSesion);
                ps.setInt(2, reservaId);
                ps.executeUpdate();
            }

            conexionBD.commit();
            return ResultadoReserva.UNIDO;
        }

        if (inv2 == null) {
            String upd = "UPDATE reservas SET invitado2_id=? WHERE id=?";

            try (PreparedStatement ps = conexionBD.prepareStatement(upd)) {
                ps.setInt(1, usuarioSesion);
                ps.setInt(2, reservaId);
                ps.executeUpdate();
            }

            conexionBD.commit();
            return ResultadoReserva.UNIDO;
        }

        if (inv3 == null) {
            String upd = "UPDATE reservas SET invitado3_id=? WHERE id=?";

            try (PreparedStatement ps = conexionBD.prepareStatement(upd)) {
                ps.setInt(1, usuarioSesion);
                ps.setInt(2, reservaId);
                ps.executeUpdate();
            }

            conexionBD.commit();
            return ResultadoReserva.UNIDO;
        }

        conexionBD.commit();
        return ResultadoReserva.LLENA;

		} catch (SQLException e) {
			try { conexionBD.rollback(); } catch (SQLException ignore) {}
			throw e;
		} finally {
			try { conexionBD.setAutoCommit(true); } catch (SQLException ignore) {}
		}
	}

	/**
	 * Obtiene los usuarios visibles que ocupan una franja concreta de reserva.
	 * 
	 * Devuelve un array de cuatro posiciones que representan al creador y a los tres
	 * posibles invitados. Si una posición está vacía, se devuelve null en esa posición.
	 * 
	 * @param municipioId identificador del municipio.
	 * @param numeroPista número de pista seleccionada.
	 * @param fechaHora fecha y hora de la reserva.
	 * @return array con los usuarios visibles de la reserva.
	 * @throws SQLException si ocurre un error al consultar la base de datos.
	*/

	public UsuarioVista[] obtenerUsuariosReservaSlot(int municipioId, int numeroPista, LocalDateTime fechaHora)
        throws SQLException {

		abrirConexionBD();

		// 4 posiciones: creador, invitado1, invitado2, invitado3
		UsuarioVista[] out = new UsuarioVista[] { null, null, null, null };

		String sql =
			"SELECT creador_id, invitado1_id, invitado2_id, invitado3_id " +
			"FROM reservas " +
			"WHERE municipio_id=? AND numero_pista=? AND fecha_hora=? AND estado <> 'cancelada' " +
			"LIMIT 1";

		Integer creadorId = null, inv1 = null, inv2 = null, inv3 = null;

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, municipioId);
			ps.setInt(2, numeroPista);
			ps.setTimestamp(3, Timestamp.valueOf(fechaHora));

			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) return out; // slot libre

				// CLAVE: getObject para preservar NULL
				creadorId = (Integer) rs.getObject("creador_id");
				inv1      = (Integer) rs.getObject("invitado1_id");
				inv2      = (Integer) rs.getObject("invitado2_id");
				inv3      = (Integer) rs.getObject("invitado3_id");
			}
		}

		// Mantener posiciones (sin desplazar)
		if (creadorId != null) out[0] = obtenerUsuarioVistaPorId(creadorId);
		if (inv1 != null)      out[1] = obtenerUsuarioVistaPorId(inv1);
		if (inv2 != null)      out[2] = obtenerUsuarioVistaPorId(inv2);
		if (inv3 != null)      out[3] = obtenerUsuarioVistaPorId(inv3);

		return out;
	}

	/**
	 * Aplica nuevas invitaciones a una reserva existente si el usuario es su creador.
	 * 
	 * Localiza la reserva indicada, comprueba que el usuario de sesión sea el creador
	 * y combina los invitados actuales con los nuevos, evitando duplicados y sin
	 * superar el máximo de tres invitados.
	 * 
	 * La operación se realiza dentro de una transacción para mantener la coherencia
	 * de los datos.
	 * 
	 * @param municipioId identificador del municipio.
	 * @param numeroPista número de pista.
	 * @param fechaHora fecha y hora de la reserva.
	 * @param creadorSesion identificador del usuario que intenta aplicar las invitaciones.
	 * @param invitaciones lista de nuevos usuarios invitados.
	 * @return true si las invitaciones se aplican correctamente, false en caso contrario.
	 * @throws SQLException si ocurre un error durante la actualización.
	*/

	public boolean aplicarInvitacionesComoCreador(int municipioId, int numeroPista, LocalDateTime fechaHora,
												int creadorSesion, ArrayList<Integer> invitaciones)
			throws SQLException {

		abrirConexionBD();
		conexionBD.setAutoCommit(false);

		try {
			// 1) localizar reserva y validar creador
					String sel = "SELECT id, creador_id, " +
								"       invitado1_id, " +
								"       invitado2_id, " +
								"       invitado3_id " +
								"FROM reservas " +
								"WHERE municipio_id=? AND numero_pista=? AND fecha_hora=? " +
								"LIMIT 1";


			Integer reservaId = null;
			Integer creadorId = null;
			Integer cur1 = null, cur2 = null, cur3 = null;

			try (PreparedStatement ps = conexionBD.prepareStatement(sel)) {
				ps.setInt(1, municipioId);
				ps.setInt(2, numeroPista);
				ps.setTimestamp(3, Timestamp.valueOf(fechaHora));
				try (ResultSet rs = ps.executeQuery()) {
					if (rs.next()) {
						reservaId = (Integer) rs.getObject("id");
						creadorId = (Integer) rs.getObject("creador_id");
						cur1 = (Integer) rs.getObject("invitado1_id");
						cur2 = (Integer) rs.getObject("invitado2_id");
						cur3 = (Integer) rs.getObject("invitado3_id");
					}
				}
			}


			if (reservaId == null || creadorId == null || creadorId != creadorSesion) {
				conexionBD.rollback();
				return false;
			}

			// 2) MERGE: primero los actuales, luego los nuevos (sin duplicados), max 3
			LinkedHashSet<Integer> set = new LinkedHashSet<>();

			// actuales (mantenerlos)
			if (cur1 != null) set.add(cur1);
			if (cur2 != null) set.add(cur2);
			if (cur3 != null) set.add(cur3);

			int sizeAntes = set.size();
			boolean ocupado = false;

			// añadir nuevos
			if (invitaciones != null) {
				for (Integer x : invitaciones) {
					if (x == null) continue;
					if (x == creadorSesion) continue;  // nunca invitar al creador
					if(set.contains(x)) continue;
					if (set.size() == 3){
						ocupado = true;
						break;
					}
					set.add(x);
				}
				int sizeDespues = set.size();
				if (ocupado || sizeDespues == sizeAntes){
					conexionBD.rollback();
					return false;
				}
			}

			Integer[] arr = set.toArray(new Integer[0]);
			Integer inv1 = (arr.length > 0) ? arr[0] : null;
			Integer inv2 = (arr.length > 1) ? arr[1] : null;
			Integer inv3 = (arr.length > 2) ? arr[2] : null;

			// 3) actualizar columnas invitado1..3 (y vaciar las que no uses)
			String upd = "UPDATE reservas SET invitado1_id=?, invitado2_id=?, invitado3_id=? WHERE id=?";

			try (PreparedStatement ps = conexionBD.prepareStatement(upd)) {
				if (inv1 == null) ps.setNull(1, Types.INTEGER); else ps.setInt(1, inv1);
				if (inv2 == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, inv2);
				if (inv3 == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, inv3);
				ps.setInt(4, reservaId);
				ps.executeUpdate();
			}

			conexionBD.commit();
			return true;

		} catch (SQLException ex) {
			conexionBD.rollback();
			throw ex;
		} finally {
			try { conexionBD.setAutoCommit(true); } catch (Exception ignore) {}
		}
	}

	/**
	 * Actualiza automáticamente las reservas pendientes que ya han finalizado.
	 * 
	 * Cambia a estado jugada aquellas reservas pendientes cuya duración estimada
	 * ya ha terminado y crea su correspondiente registro en la tabla partidas
	 * si todavía no existe.
	 * 
	 * @throws SQLException si ocurre un error durante la actualización.
	*/

	public void ActualizaPartidas() throws SQLException {

		int DURACION_PARTIDA = 90;

		abrirConexionBD();

		String sql = " UPDATE reservas set estado='jugada' where estado = 'pendiente' " +
						"AND DATE_ADD(fecha_hora, INTERVAL ? MINUTE) <= NOW()";

		String sql_2 = "INSERT IGNORE INTO partidas (reserva_id)" +
		"SELECT r.id FROM reservas r WHERE r.estado='jugada' ";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)){
			ps.setInt(1, DURACION_PARTIDA);
			ps.executeUpdate();
			try(PreparedStatement ps2 = conexionBD.prepareStatement(sql_2)){
				ps2.executeUpdate();
			}
		}
			conexionBD.commit();
	}

	/**
	 * Obtiene todas las partidas asociadas a un usuario.
	 * 
	 * Recupera tanto las reservas pendientes como las partidas jugadas en las que
	 * el usuario aparece como creador o invitado. También carga los datos del
	 * municipio, resultado, ganadores y participantes.
	 * 
	 * @param idUsuario identificador del usuario.
	 * @return lista de partidas asociadas al perfil del usuario.
	 * @throws SQLException si ocurre un error al consultar la base de datos.
	*/

	public ArrayList<PartidaPerfilBD> obtenerPartidasPerfil(int idUsuario) throws SQLException {
    abrirConexionBD();

    ArrayList<PartidaPerfilBD> lista = new ArrayList<>();

    String sql =
        "SELECT r.id, r.fecha_hora, r.numero_pista, r.creador_id, " +
        "       r.invitado1_id, r.invitado2_id, r.invitado3_id, " +
        "       r.estado, m.municipio, p.resultado, p.ganador1_id, p.ganador2_id " +
        "FROM reservas r " +
        "JOIN municipios m ON m.id = r.municipio_id " +
		"LEFT JOIN partidas p ON p.reserva_id = r.id " +
        "WHERE r.creador_id = ? " +
        "   OR r.invitado1_id = ? " +
        "   OR r.invitado2_id = ? " +
        "   OR r.invitado3_id = ? " +
        "ORDER BY r.fecha_hora DESC";

    try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        ps.setInt(2, idUsuario);
        ps.setInt(3, idUsuario);
        ps.setInt(4, idUsuario);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PartidaPerfilBD p = new PartidaPerfilBD();

                p.setId(rs.getInt("id"));
                p.setFechaHora(rs.getTimestamp("fecha_hora"));
                p.setMunicipio(rs.getString("municipio"));
                p.setNumeroPista(rs.getInt("numero_pista"));
                p.setEstado(rs.getString("estado"));
				p.setResultado(rs.getString("resultado"));
				p.setGanador1_id(rs.getInt("ganador1_id"));
				p.setGanador2_id(rs.getInt("ganador2_id"));
                p.setCreador(rs.getInt("creador_id") == idUsuario);

                ArrayList<ParticipantePerfilBD> participantes = new ArrayList<>();

                Integer creadorId = (Integer) rs.getObject("creador_id");
                Integer inv1 = (Integer) rs.getObject("invitado1_id");
                Integer inv2 = (Integer) rs.getObject("invitado2_id");
                Integer inv3 = (Integer) rs.getObject("invitado3_id");

                if (creadorId != null) {
                    String nombre = obtenerNombreUsuarioPorId(creadorId);
                    if (nombre != null) participantes.add(new ParticipantePerfilBD(creadorId, nombre));
                }
                if (inv1 != null) {
                    String nombre = obtenerNombreUsuarioPorId(inv1);
                    if (nombre != null) participantes.add(new ParticipantePerfilBD(inv1, nombre));
                }
                if (inv2 != null) {
                    String nombre = obtenerNombreUsuarioPorId(inv2);
                    if (nombre != null) participantes.add(new ParticipantePerfilBD(inv2, nombre));
                }
                if (inv3 != null) {
                    String nombre = obtenerNombreUsuarioPorId(inv3);
                    if (nombre != null) participantes.add(new ParticipantePerfilBD(inv3, nombre));
                }

                p.setParticipantes(participantes);

                lista.add(p);
            }
        }
    }

    return lista;
	}

	/**
	 * Obtiene las reservas pendientes asociadas a un usuario.
	 * 
	 * Filtra la lista completa de partidas del perfil y devuelve únicamente aquellas
	 * cuyo estado es pendiente.
	 * 
	 * @param idUsuario identificador del usuario.
	 * @return lista de reservas pendientes del usuario.
	 * @throws SQLException si ocurre un error al consultar la base de datos.
	*/

	public ArrayList<PartidaPerfilBD> obtenerPendientesPerfil(int idUsuario) throws SQLException {
		ArrayList<PartidaPerfilBD> todas = obtenerPartidasPerfil(idUsuario);
		ArrayList<PartidaPerfilBD> pendientes = new ArrayList<>();

		for (PartidaPerfilBD p : todas) {
			if ("pendiente".equalsIgnoreCase(p.getEstado())) {
				pendientes.add(p);
			}
		}
		return pendientes;
	}

	/**
	 * Obtiene el historial de partidas jugadas de un usuario.
	 * 
	 * Filtra la lista completa de partidas del perfil y devuelve únicamente aquellas
	 * cuyo estado es jugada.
	 * 
	 * @param idUsuario identificador del usuario.
	 * @return lista de partidas jugadas por el usuario.
	 * @throws SQLException si ocurre un error al consultar la base de datos.
	*/

	public ArrayList<PartidaPerfilBD> obtenerHistorialPerfil(int idUsuario) throws SQLException {
		ArrayList<PartidaPerfilBD> todas = obtenerPartidasPerfil(idUsuario);
		ArrayList<PartidaPerfilBD> historial = new ArrayList<>();

		for (PartidaPerfilBD p : todas) {
			if ("jugada".equalsIgnoreCase(p.getEstado())) {
				historial.add(p);
			}
		}
		return historial;
	}


	/**
	 * Cancela una reserva existente.
	 * 
	 * Cambia el estado de la reserva indicada a cancelada, permitiendo que esa
	 * franja pueda volver a estar disponible para una nueva reserva.
	 * 
	 * @param idReserva identificador de la reserva que se desea cancelar.
	 * @throws SQLException si ocurre un error durante la actualización.
	*/

	public void cancelarReserva(int idReserva) throws SQLException {
		abrirConexionBD();

		String sql = "UPDATE reservas SET estado = 'cancelada' WHERE id = ?";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, idReserva);
			ps.executeUpdate();
		}
	}

	/**
	 * Valida si un resultado introducido corresponde a un marcador válido de pádel.
	 * 
	 * Comprueba que el resultado no esté vacío, que tenga entre uno y tres sets
	 * y que alguno de los dos equipos haya ganado dos sets.
	 * 
	 * @param resultado resultado introducido en formato de sets.
	 * @return true si el resultado es válido, false en caso contrario.
	*/

	public boolean esResultadoPadelValido(String resultado) {
		if (resultado == null || resultado.isBlank()) return false;

		String[] sets = resultado.split("/");
		if (sets.length < 1 || sets.length > 3) return false;

		int setsGanadosA = 0;
		int setsGanadosB = 0;

		for (String set : sets) {
			String setLimpio = set.trim();

			if (!esSetValido(setLimpio)) {
				return false;
			}

			String[] partes = setLimpio.split("-");
			int a = Integer.parseInt(partes[0].trim());
			int b = Integer.parseInt(partes[1].trim());

			if (a > b) {
				setsGanadosA++;
			} else {
				setsGanadosB++;
			}
		}

		return setsGanadosA == 2 || setsGanadosB == 2;
	}

	/**
	 * Comprueba si un set individual tiene un formato válido.
	 * 
	 * Valida que el set tenga dos puntuaciones separadas por guion, que sean valores
	 * numéricos y que respeten las reglas habituales de puntuación de un set de pádel.
	 * 
	 * @param set set individual introducido por el usuario.
	 * @return true si el set es válido, false en caso contrario.
	*/

	private boolean esSetValido(String set) {
		String[] partes = set.split("-");
		if (partes.length != 2) return false;

		int a, b;
		try {
			a = Integer.parseInt(partes[0].trim());
			b = Integer.parseInt(partes[1].trim());
		} catch (NumberFormatException e) {
			return false;
		}

		if (a == b) return false;
		if (a < 0 || b < 0) return false;
		if (a > 7 || b > 7) return false;

		int max = Math.max(a, b);
		int min = Math.min(a, b);

		if (max == 6) {
			return min >= 0 && min <= 4;
		}

		if (max == 7) {
			return min == 5 || min == 6;
		}

		return false;
	}

	/**
	 * Guarda el resultado y los ganadores de una partida.
	 * 
	 * Actualiza la partida asociada a una reserva concreta, almacenando el resultado
	 * introducido y los dos usuarios seleccionados como ganadores.
	 * 
	 * @param idReserva identificador de la reserva asociada a la partida.
	 * @param resultado resultado de la partida.
	 * @param ganador1 identificador del primer ganador.
	 * @param ganador2 identificador del segundo ganador.
	 * @throws SQLException si ocurre un error durante la actualización.
	*/

	public void guardarResultadoPartida(int idReserva, String resultado, int ganador1, int ganador2) throws SQLException {
		abrirConexionBD();

		String sql = "UPDATE partidas SET resultado = ?, ganador1_id = ?, ganador2_id = ? WHERE reserva_id = ?";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setString(1, resultado);
			ps.setInt(2, ganador1);
			ps.setInt(3, ganador2);
			ps.setInt(4, idReserva);
			ps.executeUpdate();
		}
	}

	/**
	 * Obtiene la clasificación general de usuarios.
	 * 
	 * Calcula las partidas jugadas, partidas ganadas y porcentaje de victorias
	 * de todos los usuarios que permiten mostrar sus partidas. Solo incluye usuarios
	 * con un mínimo de cinco partidas jugadas y limita el resultado a los veinte
	 * mejores clasificados.
	 * 
	 * @return lista de usuarios ordenados por clasificación general.
	 * @throws SQLException si ocurre un error al consultar la base de datos.
	*/

	public ArrayList<UsuarioClasificacion> obtenerClasificacionGeneral() throws SQLException {
		abrirConexionBD();

		ArrayList<UsuarioClasificacion> lista = new ArrayList<>();

		String sql =
			"SELECT u.id AS id_usuario, u.nombre_usuario, " +
			"COUNT(*) AS partidas_jugadas, " +

			"SUM(CASE " +
			"WHEN u.id = p.ganador1_id OR u.id = p.ganador2_id " +
			"THEN 1 ELSE 0 END) AS partidas_ganadas, " +

			"ROUND( " +
			"SUM(CASE " +
			"WHEN u.id = p.ganador1_id OR u.id = p.ganador2_id " +
			"THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 1) AS porcentaje_victorias " +

			"FROM usuarios u " +

			"JOIN ( " +
			"SELECT creador_id AS id_usuario, id AS reserva_id FROM reservas " +
			"UNION ALL " +
			"SELECT invitado1_id, id FROM reservas WHERE invitado1_id IS NOT NULL " +
			"UNION ALL " +
			"SELECT invitado2_id, id FROM reservas WHERE invitado2_id IS NOT NULL " +
			"UNION ALL " +
			"SELECT invitado3_id, id FROM reservas WHERE invitado3_id IS NOT NULL " +
			") participantes ON participantes.id_usuario = u.id " +

			"JOIN reservas r ON r.id = participantes.reserva_id " +
			"JOIN partidas p ON p.reserva_id = r.id " +

			"WHERE r.estado = 'jugada' " +
			"AND p.resultado IS NOT NULL " +
			"AND u.mostrar_partidas = '1' " +

			"GROUP BY u.id, u.nombre_usuario " +
			"HAVING COUNT(*) >= 5 " +

			"ORDER BY porcentaje_victorias DESC, partidas_ganadas DESC, partidas_jugadas DESC, nombre_usuario ASC " +
			"LIMIT 20";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			int posicion = 1;

			while (rs.next()) {
				UsuarioClasificacion u = new UsuarioClasificacion();

				u.setPosicion(posicion++);
				u.setIdUsuario(rs.getInt("id_usuario"));
				u.setNombreUsuario(rs.getString("nombre_usuario"));
				u.setPartidasJugadas(rs.getInt("partidas_jugadas"));
				u.setPartidasGanadas(rs.getInt("partidas_ganadas"));
				u.setPorcentajeV(rs.getDouble("porcentaje_victorias"));
				u.setRacha(obtenerRachaUsuario(u.getIdUsuario()));

				lista.add(u);
			}
		}

		return lista;
	}

	/**
	 * Calcula la racha actual de un usuario.
	 * 
	 * Recorre las partidas jugadas del usuario desde la más reciente hasta la más
	 * antigua y calcula cuántas victorias o derrotas consecutivas acumula.
	 * 
	 * @param idUsuario identificador del usuario.
	 * @return cadena con la racha actual, formada por número y tipo de resultado.
	 * @throws SQLException si ocurre un error al consultar la base de datos.
	*/

	public String obtenerRachaUsuario(int idUsuario) throws SQLException {
		abrirConexionBD();

		ArrayList<String> resultados = new ArrayList<>();

		String sql =
			"SELECT r.fecha_hora, " +
			"CASE " +
			"   WHEN p.ganador1_id = ? OR p.ganador2_id = ? THEN 'V' " +
			"   ELSE 'D' " +
			"END AS resultado_usuario " +
			"FROM reservas r " +
			"JOIN partidas p ON p.reserva_id = r.id " +
			"WHERE r.estado = 'jugada' " +
			"AND p.resultado IS NOT NULL " +
			"AND (r.creador_id = ? OR r.invitado1_id = ? OR r.invitado2_id = ? OR r.invitado3_id = ?) " +
			"ORDER BY r.fecha_hora DESC";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			ps.setInt(2, idUsuario);
			ps.setInt(3, idUsuario);
			ps.setInt(4, idUsuario);
			ps.setInt(5, idUsuario);
			ps.setInt(6, idUsuario);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(rs.getString("resultado_usuario"));
				}
			}
		}

		if (resultados.isEmpty()) {
			return "0";
		}

		String primero = resultados.get(0);
		int contador = 0;

		for (String r : resultados) {
			if (r.equals(primero)) {
				contador++;
			} else {
				break;
			}
		}

		return contador + primero;
	}

	/**
	 * Obtiene la clasificación general o filtrada por municipio.
	 * 
	 * Calcula las estadísticas de clasificación de los usuarios teniendo en cuenta
	 * únicamente partidas jugadas con resultado registrado. Si se recibe un municipio,
	 * limita el cálculo a las partidas disputadas en ese municipio.
	 * 
	 * @param idMunicipio identificador del municipio usado como filtro, o null para clasificación general.
	 * @return lista de usuarios clasificados.
	 * @throws SQLException si ocurre un error al consultar la base de datos.
	*/

	public ArrayList<UsuarioClasificacion> obtenerClasificacionPorMunicipio(Integer idMunicipio) throws SQLException {
		abrirConexionBD();

		ArrayList<UsuarioClasificacion> lista = new ArrayList<>();

		String sql =
			"SELECT u.id AS id_usuario, u.nombre_usuario, " +
			"COUNT(*) AS partidas_jugadas, " +
			"SUM(CASE " +
			"WHEN u.id = p.ganador1_id OR u.id = p.ganador2_id " +
			"THEN 1 ELSE 0 END) AS partidas_ganadas, " +
			"ROUND( " +
			"SUM(CASE " +
			"WHEN u.id = p.ganador1_id OR u.id = p.ganador2_id " +
			"THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 1) AS porcentaje_victorias " +
			"FROM usuarios u " +
			"JOIN ( " +
			"SELECT creador_id AS id_usuario, id AS reserva_id FROM reservas " +
			"UNION ALL " +
			"SELECT invitado1_id, id FROM reservas WHERE invitado1_id IS NOT NULL " +
			"UNION ALL " +
			"SELECT invitado2_id, id FROM reservas WHERE invitado2_id IS NOT NULL " +
			"UNION ALL " +
			"SELECT invitado3_id, id FROM reservas WHERE invitado3_id IS NOT NULL " +
			") participantes ON participantes.id_usuario = u.id " +
			"JOIN reservas r ON r.id = participantes.reserva_id " +
			"JOIN partidas p ON p.reserva_id = r.id " +
			"WHERE r.estado = 'jugada' " +
			"AND p.resultado IS NOT NULL ";

		if (idMunicipio != null) {
			sql += "AND r.municipio_id = ? ";
		}

		sql +=
			"GROUP BY u.id, u.nombre_usuario " +
			"HAVING COUNT(*) >= 5 " +
			"ORDER BY porcentaje_victorias DESC, partidas_ganadas DESC, partidas_jugadas DESC, nombre_usuario ASC " +
			"LIMIT 20";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {

			if (idMunicipio != null) {
				ps.setInt(1, idMunicipio);
			}

			try (ResultSet rs = ps.executeQuery()) {
				int posicion = 1;

				while (rs.next()) {
					UsuarioClasificacion u = new UsuarioClasificacion();

					u.setPosicion(posicion++);
					u.setIdUsuario(rs.getInt("id_usuario"));
					u.setNombreUsuario(rs.getString("nombre_usuario"));
					u.setPartidasJugadas(rs.getInt("partidas_jugadas"));
					u.setPartidasGanadas(rs.getInt("partidas_ganadas"));
					u.setPorcentajeV(rs.getDouble("porcentaje_victorias"));
					u.setRacha(obtenerRachaUsuarioPorMunicipio(u.getIdUsuario(), idMunicipio));

					lista.add(u);
				}
			}
		}

		return lista;
	}

	/**
	 * Calcula la racha actual de un usuario en la clasificación general o municipal.
	 * 
	 * Analiza las partidas jugadas del usuario, opcionalmente filtradas por municipio,
	 * y devuelve la cantidad de victorias o derrotas consecutivas más recientes.
	 * 
	 * @param idUsuario identificador del usuario.
	 * @param idMunicipio identificador del municipio usado como filtro, o null para clasificación general.
	 * @return cadena con la racha actual del usuario.
	 * @throws SQLException si ocurre un error al consultar la base de datos.
	*/

	public String obtenerRachaUsuarioPorMunicipio(int idUsuario, Integer idMunicipio) throws SQLException {
		abrirConexionBD();

		ArrayList<String> resultados = new ArrayList<>();

		String sql =
			"SELECT r.fecha_hora, " +
			"CASE " +
			"WHEN p.ganador1_id = ? OR p.ganador2_id = ? THEN 'V' " +
			"ELSE 'D' " +
			"END AS resultado_usuario " +
			"FROM reservas r " +
			"JOIN partidas p ON p.reserva_id = r.id " +
			"WHERE r.estado = 'jugada' " +
			"AND p.resultado IS NOT NULL " +
			"AND (r.creador_id = ? OR r.invitado1_id = ? OR r.invitado2_id = ? OR r.invitado3_id = ?) ";

		if (idMunicipio != null) {
			sql += "AND r.municipio_id = ? ";
		}

		sql += "ORDER BY r.fecha_hora DESC";

		try (PreparedStatement ps = conexionBD.prepareStatement(sql)) {
			ps.setInt(1, idUsuario);
			ps.setInt(2, idUsuario);
			ps.setInt(3, idUsuario);
			ps.setInt(4, idUsuario);
			ps.setInt(5, idUsuario);
			ps.setInt(6, idUsuario);

			if (idMunicipio != null) {
				ps.setInt(7, idMunicipio);
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					resultados.add(rs.getString("resultado_usuario"));
				}
			}
		}

		if (resultados.isEmpty()) {
			return "0";
		}

		String primero = resultados.get(0);
		int contador = 0;

		for (String r : resultados) {
			if (r.equals(primero)) {
				contador++;
			} else {
				break;
			}
		}

		return contador + primero;
	}
};
