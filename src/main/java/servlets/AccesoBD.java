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
};
