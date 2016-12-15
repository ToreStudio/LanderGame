#Ejer34_AplicacionWebJPA
#Con esta práctica se pretende gestionar datos de: registro, inicio de sesión, puntuacones, rankings, etc.

#Las clases utilizadas son:
#•	Toreusuarios: tabla postgresql generada mediante Entity Classes from Database. Contiene: id , el nombre  y la contraseña encriptada  del usuario.
#•	ToreResultados: tabla postgresql generada mediante Entity Classes from Database. Contiene: id de resultado, la puntuación, fecha de inicio de partida, fecha de final de partida e id del usuario.
#•	DBListener: Web Application Listener que inicia la conexión con la base de datos cuando se realiza una consulta y se cierra la conexión al acabar la misma.
#•	ServletCookies: servlet que comprueba las cookie y si son correctas lleva al juego, de lo contrario lleva a dbservlet.
#•	DbServlet: servlet que gestiona los registros e inicios de sesión de los usuarios.
#•	servletResultado: servlet que gestiona  resultados de las partidas  de los usuarios.
#•	UsuariosService: clase que realiza comprobaciones de usuarios con la base de datos y almacena los nuevos usuarios en la base de datos.
#•	ResultadosService: clase que almacena las puntuaciones y fechas de inicio y final de la partida en la base de datos.

#Las páginas utilizadas son:
#•	menu: menu del juego para registrar nuevos usuarios e inicios de sesión de usuarios ya registrados.
#•	juego: página html con el juego.

