package controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.Properties;



@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/testc")
public class CourseController {
    public final String user;
    public final String password;
    public final String url;

    public static final String QUERY_STR =
            "SELECT\n" +
            "            AREA_FUNCIONAL.CODAREAFUNCIONAL                             AS \"id_direccion\"\n" +
            "    ,       PERIODO.CODPERIODO                                          AS \"id_ciclo\"\n" +
            "    ,       CURSO_SECCION.CODCURSOSECCION                               AS \"id_seccion\"\n" +
            "    ,       CURSO_SESION.CODRDOCENTE                                    AS \"id_docente\"\n" +
            "    ,       CURSO_SESION.CODTIPOSESIONMTD                               AS \"id_tipo_sesion\"\n" +
            "    ,       AREA_FUNCIONAL.DESCRIPLARGA                                 AS \"nombre_direccion\"\n" +
            "    ,       PERIODO.DESCRIPCIONLARGA                                    AS \"nombre_ciclo\"\n" +
            "    ,       CURSO_ACT.DESCRIPCIONLARGA                                  AS \"nombre_curso\"\n" +
            "    ,       CURSO_SECCION_DATOS.DESCRIPCIONLARGA                        AS \"nombre_seccion\"\n" +
            "    ,       CURSO_SESION_DATOS.DESCRIPCIONLARGA                         AS \"nombre_tipo_sesion\"\n" +
            "    ,       UTEC.GET_NOMBRES_PERSONA(CURSO_SESION.CODRDOCENTE)          AS \"nombre_docente\"\n" +
            "    ,       RANGO_PERIODO.FECHAINICIO                                   AS \"fecha_inicio\"\n" +
            "    ,       RANGO_PERIODO.FECHAFIN                                      AS \"fecha_fin\"\n" +
            "\n" +
            "FROM        PROGRAMACION.PRO_CURSO_SECCION CURSO_SECCION\n" +
            "\n" +
            "INNER JOIN  GENERAL.GEN_MAESTRO_TABLAS_DETALLE CURSO_SECCION_DATOS\n" +
            "ON          CURSO_SECCION.CODSECCIONMTD = CURSO_SECCION_DATOS.CODMAESTROTABLASDETALLE\n" +
            "AND         CURSO_SECCION_DATOS.ISDELETED = 'N'\n" +
            "\n" +
            "INNER JOIN  (\n" +
            "                SELECT  PCS.*\n" +
            "                    ,   NVL(UTEC.FN_GET_CODDOCENTEREASIGNADO(PCS.CODCURSOSESION), PCS.CODDOCENTE) CODRDOCENTE\n" +
            "                FROM    PROGRAMACION.PRO_CURSO_SESION PCS\n" +
            "            )   CURSO_SESION\n" +
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
            "AND         RANGO_PERIODO.FECHAINICIO >= TO_TIMESTAMP('2018-12-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS')\n";

    @Autowired
    CourseController(Environment env) {
        this.user = env.getProperty("UTEC_DB_USERNAME");
        this.password = env.getProperty("UTEC_DB_PASSWORD");
        this.url = env.getProperty("UTEC_DB_URL");
        assert this.user != null && this.password != null && this.url != null;
    }

    public Connection getConnection() throws SQLException {

        Properties connectionProps = new Properties();
        connectionProps.put("user", this.user);
        connectionProps.put("password", this.password);

        return DriverManager.getConnection(this.url, connectionProps);
    }

    // temporary function
    @GetMapping(value="/results")
    public JSONObject printResults() throws SQLException, JSONException {
        try (Connection connection = this.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(QUERY_STR);
                ResultSetMetaData rsmd = rs.getMetaData();
                JSONObject obj = new JSONObject();

                for (int rowNum = 0; rs.next(); ++rowNum) {
                    JSONObject item = new JSONObject();
                    int numColumns = rsmd.getColumnCount();
                    for (int i=1; i<=numColumns; i++) {
                        String columnName = rsmd.getColumnName(i);
                        item.put(columnName, rs.getObject(columnName));
                    }

                    obj.put(String.valueOf(rowNum), item);
                }
                rs.close();
                return obj;
            }
        }
    }


}
