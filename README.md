#Ejer34_AplicacionWebJPA
#Con esta práctica se pretende gestionar datos de: registro, inicio de sesión, puntuacones, rankings, etc.
#Las clases utilizadas son:
#•	Toreusuarios: tabla postgresql generada mediante Entity Classes from Database. Contiene: id , el nombre  y la contraseña encriptada  del usuario.
#•	ToreResultados: tabla postgresql generada mediante Entity Classes from Database. Contiene: id de resultado, la puntuación, fecha de inicio de partida, fecha de final de partida e id del usuario.
#•	DBListener: Web Application Listener que inicia la conexión con la base de datos.
#•	XMLServlet: maneja la información del result.jsp y la envía a la clase Xmlconverter, que contiene el método objectToXml y creando así el formato xml.
#•	Alumnos: clase generada mediante el jaxB.

#El funcionamiento del proyecto se basa en mostrar una lista de alumnos en el index.jsp y al seleccionar el alumno deseado, tratar la información en el doPost del NServlet y así ejecutar un jquery en el cual mostramos la información del alumno en el result.jsp. Por último , se puede acceder al XMLServlet mediante el botón y así crear el formato xml mediante jaxB.
