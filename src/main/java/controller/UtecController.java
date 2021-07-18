package controller;


import business.UsuarioService;
import config.JwtTokenUtil;
import data.entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/utecrequest")

public class UtecController {

    final static String clientUrl = "*";

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    @Qualifier("ds2")
    DataSource ds2;

    @GetMapping("/getdirecs/{direccion}")
    @CrossOrigin(origins = clientUrl)
    public List<Course> getDirecs( @RequestHeader("Authorization") String token, @PathVariable String direccion) throws SQLException {
        var username = jwtTokenUtil.getUsernameFromToken(token);
        var user = usuarioService.findUsuarioByEmailAndNombreNotNull(username);
        Connection con = ds2.getConnection();

        if (user.getRol().getId() == 4 || user.getRol().getId() == 3 ){
            ResultSet rs = con.prepareStatement("").executeQuery();



        }else{
            ResultSet rs = con.prepareStatement("SELECT\n" +
                    "            AREA_FUNCIONAL.CODAREAFUNCIONAL                             AS \"id_area_funcional\"\n" +
                    "    ,       PERIODO.CODPERIODO                                          AS \"id_periodo\"\n" +
                    "    ,       CURSO_SECCION.CODCURSOSECCION                               AS \"id_seccion\"\n" +
                    "    ,       CURSO_SESION.CODRDOCENTE                                    AS \"id_docente\"\n" +
                    "    ,       CURSO_SESION.CODTIPOSESIONMTD                               AS \"id_tipo_sesion\"\n" +
                    "    ,       AREA_FUNCIONAL.DESCRIPLARGA                                 AS \"nombre_area_funcional\"\n" +
                    "    ,       PERIODO.DESCRIPCIONLARGA                                    AS \"nombre_periodo\"\n" +
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
                    ";").executeQuery();
        }

    }


}

