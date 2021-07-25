package business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.ConnectException;
import java.sql.*;
import java.util.*;

@Service
@Transactional
public class UtecService {
    public static final String DIRECTIONS_QUERY_STR = "SELECT DISTINCT AREA_FUNCIONAL.CODAREAFUNCIONAL  AS \"id_direccion\", AREA_FUNCIONAL.DESCRIPLARGA  AS \"nombre_direccion\"\n" +
            "FROM  PROGRAMACION.PRO_CURSO_SECCION CURSO_SECCION INNER JOIN  GENERAL.GEN_MAESTRO_TABLAS_DETALLE CURSO_SECCION_DATOS\n" +
            "                                                               ON          CURSO_SECCION.CODSECCIONMTD = CURSO_SECCION_DATOS.CODMAESTROTABLASDETALLE\n" +
            "                                                                   AND         CURSO_SECCION_DATOS.ISDELETED = 'N'\n" +
            "\n" +
            "                                                   INNER JOIN  (\n" +
            "    SELECT  PCS.*\n" +
            "         ,   NVL(UTEC.FN_GET_CODDOCENTEREASIGNADO(PCS.CODCURSOSESION), PCS.CODDOCENTE) CODRDOCENTE\n" +
            "    FROM    PROGRAMACION.PRO_CURSO_SESION PCS\n" +
            ")   CURSO_SESION\n" +
            "                                                               ON          CURSO_SECCION.CODCURSOSECCION = CURSO_SESION.CODCURSOSECCION\n" +
            "                                                                   AND         CURSO_SESION.ISDELETED = 'N'\n" +
            "\n" +
            "                                                   INNER JOIN  GENERAL.GEN_MAESTRO_TABLAS_DETALLE CURSO_SESION_DATOS\n" +
            "                                                               ON          CURSO_SESION.CODTIPOSESIONMTD = CURSO_SESION_DATOS.CODMAESTROTABLASDETALLE\n" +
            "                                                                   AND         CURSO_SESION_DATOS.ISDELETED = 'N'\n" +
            "\n" +
            "                                                   INNER JOIN  PROGRAMACION.PRO_CURSO_PERIODO CURSO_PERIODO\n" +
            "                                                               ON          CURSO_SECCION.CODCURSOPERIODO = CURSO_PERIODO.CODCURSOPERIODO\n" +
            "                                                                   AND         CURSO_PERIODO.ISDELETED = 'N'\n" +
            "\n" +
            "                                                   INNER JOIN  CONFIGURACION.CON_CURSO CURSO\n" +
            "                                                               ON          CURSO_PERIODO.CODCURSO = CURSO.CODCURSO\n" +
            "                                                                   AND         CURSO.ISDELETED = 'N'\n" +
            "\n" +
            "                                                   INNER JOIN  CONFIGURACION.CON_ACTIVIDAD CURSO_ACT\n" +
            "                                                               ON          CURSO.CODCURSO = CURSO_ACT.CODACTIVIDAD\n" +
            "                                                                   AND         CURSO_ACT.ISDELETED = 'N'\n" +
            "\n" +
            "                                                   INNER JOIN  GENERAL.GEN_AREA_FUNCIONAL AREA_FUNCIONAL\n" +
            "                                                               ON          CURSO_ACT.CODAREAFUNCIONAL = AREA_FUNCIONAL.CODAREAFUNCIONAL\n" +
            "                                                                   AND         AREA_FUNCIONAL.ISDELETED = 'N'\n" +
            "\n" +
            "                                                   INNER JOIN  PROGRAMACION.PRO_PERIODORANGO RANGO_PERIODO\n" +
            "                                                               ON          CURSO_PERIODO.CODPERIODORANGO = RANGO_PERIODO.CODPERIODORANGO\n" +
            "                                                                   AND         RANGO_PERIODO.ISDELETED = 'N'\n" +
            "\n" +
            "                                                   INNER JOIN  PROGRAMACION.PRO_PERIODO PERIODO\n" +
            "                                                               ON          RANGO_PERIODO.CODPERIODO = PERIODO.CODPERIODO\n" +
            "                                                                   AND         PERIODO.ISDELETED = 'N'\n" +
            "\n" +
            "WHERE       CURSO_SECCION.ISDELETED = 'N'\n" +
            "  AND         RANGO_PERIODO.FECHAINICIO >= TO_TIMESTAMP('2018-12-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS')";



    public static final String QUERYCURSOS = "" +
            "SELECT DISTINCT\n" +
            "            CURSO_ACT.DESCRIPCIONLARGA                                  AS \"nombre_curso\",\n" +
            "            CURSO_ACT.CODACTIVIDAD                                      AS \"cod_curso\"\n" +
            "\n" +
            "FROM        PROGRAMACION.PRO_CURSO_SECCION CURSO_SECCION\n" +
            "\n" +
            "INNER JOIN  GENERAL.GEN_MAESTRO_TABLAS_DETALLE CURSO_SECCION_DATOS\n" +
            "ON          CURSO_SECCION.CODSECCIONMTD = CURSO_SECCION_DATOS.CODMAESTROTABLASDETALLE\n" +
            "AND         CURSO_SECCION_DATOS.ISDELETED = 'N'\n" +
            "\n" +
            "INNER JOIN  PROGRAMACION.PRO_CURSO_SESION CURSO_SESION\n" +
            "ON          CURSO_SECCION.CODCURSOSECCION = CURSO_SESION.CODCURSOSECCION\n" +
            "AND         CURSO_SESION.ISDELETED = 'N'\n" +
            "\n" +
            "INNER JOIN  GENERAL.GEN_MAESTRO_TABLAS_DETALLE CURSO_SESION_DATOS\n" +
            "ON          CURSO_SESION.CODTIPOSESIONMTD = CURSO_SESION_DATOS.CODMAESTROTABLASDETALLE\n" +
            "AND         CURSO_SESION_DATOS.ISDELETED = 'N'\n" +
            "\n" +
            "INNER JOIN  PROGRAMACION.PRO_CURSO_PERIODO CURSO_PERIODO\n" +
            "ON          CURSO_SECCION.CODCURSOPERIODO = CURSO_PERIODO.CODCURSOPERIODO\n" +
            "AND         CURSO_PERIODO.ISDELETED = 'N'\n" +
            "\n" +
            "INNER JOIN  CONFIGURACION.CON_CURSO CURSO\n" +
            "ON          CURSO_PERIODO.CODCURSO = CURSO.CODCURSO\n" +
            "AND         CURSO.ISDELETED = 'N'\n" +
            "\n" +
            "INNER JOIN  CONFIGURACION.CON_ACTIVIDAD CURSO_ACT\n" +
            "ON          CURSO.CODCURSO = CURSO_ACT.CODACTIVIDAD\n" +
            "AND         CURSO_ACT.ISDELETED = 'N'\n" +
            "\n" +
            "INNER JOIN  GENERAL.GEN_AREA_FUNCIONAL AREA_FUNCIONAL\n" +
            "ON          CURSO_ACT.CODAREAFUNCIONAL = AREA_FUNCIONAL.CODAREAFUNCIONAL\n" +
            "AND         AREA_FUNCIONAL.ISDELETED = 'N'\n" +
            "\n" +
            "INNER JOIN  PROGRAMACION.PRO_PERIODORANGO RANGO_PERIODO\n" +
            "ON          CURSO_PERIODO.CODPERIODORANGO = RANGO_PERIODO.CODPERIODORANGO\n" +
            "AND         RANGO_PERIODO.ISDELETED = 'N'\n" +
            "\n" +
            "INNER JOIN  PROGRAMACION.PRO_PERIODO PERIODO\n" +
            "ON          RANGO_PERIODO.CODPERIODO = PERIODO.CODPERIODO\n" +
            "AND         PERIODO.ISDELETED = 'N'\n" +
            "\n" +
            "WHERE       CURSO_SECCION.ISDELETED = 'N'\n" +
            "AND         RANGO_PERIODO.FECHAINICIO >= TO_TIMESTAMP('2018-12-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS')\n" +
            "AND         AREA_FUNCIONAL.CODAREAFUNCIONAL= ? \n";

    public final String user;
    public final String password;
    public final String url;

    @Autowired
    UtecService(Environment env) throws ConnectException {
        this.user = env.getProperty("UTEC_DB_USERNAME");
        this.password = env.getProperty("UTEC_DB_PASSWORD");
        this.url = env.getProperty("UTEC_DB_URL");
        if (this.user == null || this.password == null || this.url == null) {
            throw new ConnectException("Unset environment properties for connection");
        }
    }


    public Connection getConnection() throws SQLException {
        var connectionProps = new Properties();
        connectionProps.put("user", this.user);
        connectionProps.put("password", this.password);

        return DriverManager.getConnection(this.url, connectionProps);
    }

    public List<Map<String, String>> getAllDirections() throws SQLException {
        try (var connection = this.getConnection()) {
            try (var statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(DIRECTIONS_QUERY_STR);
                ResultSetMetaData rsmd = rs.getMetaData();
                List<Map<String, String>> obj = new ArrayList<>();

                while (rs.next()) {
                    Map<String, String> item = new HashMap<>();
                    int numColumns = rsmd.getColumnCount();
                    for (int i=1; i<=numColumns; i++) {
                        String columnName = rsmd.getColumnName(i);
                        item.put(columnName, rs.getObject(columnName).toString());
                    }
                    obj.add(item);
                }
                rs.close();
                return obj;
            }
        }
    }

    public List<Map<String, String>> getCourseFromDireccion(String direccion) throws SQLException{
        return resultListFromQuery(direccion, QUERYCURSOS);
    }

    private List<Map<String, String>> resultListFromQuery(String direccion, String querycursos) throws SQLException {
        try(var connection = this.getConnection()){
            try ( PreparedStatement sentencia = connection.prepareStatement(querycursos)){
                sentencia.setString(1, direccion);
                ResultSet rs = sentencia.executeQuery();
                return makeListFromResults(rs);
            }
        }
    }

    private List<Map<String, String>> makeListFromResults(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        List<Map<String, String>> obj = new ArrayList<>();
        while (rs.next()) {
            Map<String, String> item = new HashMap<>();
            int numColumns = rsmd.getColumnCount();
            for (var i=1; i<=numColumns; i++) {
                String columnName = rsmd.getColumnName(i);
                item.put(columnName, rs.getObject(columnName).toString());
            }
            obj.add(item);
        }
        rs.close();
        return obj;
    }


}
