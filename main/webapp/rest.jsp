<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>REST de mi Aplicación</title>
<script type="text/javascript" src="js/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
//Esta función añade un elemento a la lista de categorias. 
function load(nombre){
	//Crea el nuevo elemento 'li' que contendrá los datos de la categoria.
	var entry = document.createElement('li');
	
	//Crea un elemento que será el enlace para borrar la categoria creada.
	var a = document.createElement('a');
	
	//Texto del enlace para borrar la categoria
	var linkText = document.createTextNode(" [Borrar]");
	
	//Se añade el texto a la etiqueta <a>
	a.appendChild(linkText);
	
		
	//Se añade el evento que se ejecutará al hacer clic sobre borrar.
	a.onclick = function () {
		$.ajax({
		    url: 'rest/categoria/' + nombre, //Url a ejecutar
		    type: 'DELETE', //Método a invocar
		    dataType: "json", //Tipo de dato enviado
		    success: function(result) {
		    	//Función que se ejecuta si todo ha ido bien. En este caso, quitar el <li> que se creó para mostrar
		    	//el usuario insertado.
		    	location.reload();
		    	document.getElementById(nombre).remove();
		    },
	    	error: function(jqXhr, textStatus, errorMessage){
	    		alert('Error: ' + jqXhr.responseJSON.resultado);	
		    }
		});
	};
	
	
	//Se identifica el <li> con el nombre de la categoria creada. Así se podrá recuperar en el futuro para eliminarlo.
	entry.nombre = nombre;		
	
	//Se añade texto al <li>
	entry.appendChild(document.createTextNode("Nombre Categoria: "+ nombre + " "));
	
	//Se pone como hijo de <li> el enlace <a> creado anteriormente  
	entry.appendChild(a);
	//Se añade el <li> al <ul> que hay en el cuerpo de la página.
	$('#categorias').append(entry);
	
	}
	
	
function load2(nombreCat,nombreVid){
	//Crea el nuevo elemento 'li' que contendrá los datos del videojuego
	var entry = document.createElement('li');
	
	//Crea un elemento que será el enlace para borrar el videojuego creado.
	var a = document.createElement('a');
	
	//Texto del enlace para borrar el videojuego
	var linkText = document.createTextNode(" [Borrar]");
	
	//Se añade el texto a la etiqueta <a>
	a.appendChild(linkText);
	
		
	//Se añade el evento que se ejecutará al hacer clic sobre borrar.
	a.onclick = function () {
		$.ajax({
		    url: 'rest/videojuego/' + nombreVid, //Url a ejecutar
		    type: 'DELETE', //Método a invocar
		    dataType: "json", //Tipo de dato enviado
		    success: function(result) {
		    	//Función que se ejecuta si todo ha ido bien. En este caso, quitar el <li> que se creó para mostrar
		    	//el usuario insertado.
		    	location.reload();
		    	document.getElementById(nombreVid).remove();
		    },
	    	error: function(jqXhr, textStatus, errorMessage){
	    		alert('Error: ' + jqXhr.responseJSON.resultado);	
		    }
		});
	};
	
	
	//Se identifica el <li> con el nombre del videojuego creado. Así se podrá recuperar en el futuro para eliminarlo.
	entry.nombreVid = nombreVid;		
	
	//Se añade texto al <li>
	entry.appendChild(document.createTextNode("Nombre Videojuego: "+ nombreVid + " " + "  Nombre Categoria: " + nombreCat));
	
	//Se pone como hijo de <li> el enlace <a> creado anteriormente  
	entry.appendChild(a);
	//Se añade el <li> al <ul> que hay en el cuerpo de la página.
	$('#videojuegos').append(entry);
	
	}
	

$(document).ready(function(){

	//Se añade la función que se ejecutará al hacer clic sobre el botón identificado por "agregarCat"
	$("#agregarCat").click(function(){
		
		
		//Se construye el JSON a enviar ¡
		//no se ponen las comillas porque la función JSON.stringify ya lo hace.
		var sendInfo = {nombre: $('#nombreCat').val()};
		
	    $.ajax({
			    url: 'rest/categoria', //URL a la que invocar
			    headers: { 
		               'Accept': 'application/json',
		               'Content-Type': 'application/json' 
		           },
			    type: 'POST', //Método del servicio rest a ejecutar
			    dataType: "json", 
			    success: function(result) {
			    	//Esta función se ejecuta si la petición ha ido bien. El cuerpo de la respuesta HTTP
			    	//se recibe en el parámetro 'result'
			    	//Ejemplo JSON respuesta --> {"categoria":{"nombre":"Acción"}}
			    	
			    	//Se llama a la función que añade el elemento a la lista.
			    	load(result.categoria.nombre);
			    	
			    },
		    	error: function(jqXhr, textStatus, errorMessage){
			    	alert('Error: ' + jqXhr.responseJSON.resultado);	
			    },
			    data:  JSON.stringify(sendInfo) //Datos a enviar al servidor
			    
			});
	    });
	
	//Se añade la función que se ejecutará al hacer clic sobre el botón identificado por "modificarCat"
	$("#modificarCat").click(function(){
		
		
		//Se construye el JSON a enviar ¡¡
		//no se ponen las comillas porque la función JSON.stringify ya lo hace.
		var sendInfo2 = {nombre: $('#nombreCat1').val(),nombre2: $('#nombreCat2').val()}
	    $.ajax({
			    url: 'rest/categoria', //URL a la que invocar
			    headers: { 
		               'Accept': 'application/json',
		               'Content-Type': 'application/json' 
		           },
			    type: 'PUT', //Método del servicio rest a ejecutar
			    dataType: "json", 
			    success: function(result) {
			    	//Esta función se ejecuta si la petición ha ido bien. El cuerpo de la respuesta HTTP
			    	//se recibe en el parámetro 'result'
			    	
			    	//Se llama a la función que añade el elemento a la lista.
			    	
			    	load(result.categoria.nombre);
			    	location.reload();
			    },
		    	error: function(jqXhr, textStatus, errorMessage){
			    	alert('Error: ' + jqXhr.responseJSON.resultado);	
			    },
			    data:  JSON.stringify(sendInfo2) //Datos a enviar al servidor
			    
			});
	    });
	
	//Se añade la función que se ejecutará al hacer clic sobre el botón identificado por "agregarVid"
	$("#agregarVid").click(function(){
		
		
		//Se construye el JSON a enviar ¡
		//no se ponen las comillas porque la función JSON.stringify ya lo hace.
		var sendInfo3 = {nombreCat: $('#nombreCat3').val(),nombreVid: $('#nombreVid4').val()}
	    $.ajax({
			    url: 'rest/videojuego', //URL a la que invocar
			    headers: { 
		               'Accept': 'application/json',
		               'Content-Type': 'application/json' 
		           },
			    type: 'POST', //Método del servicio rest a ejecutar
			    dataType: "json", 
			    success: function(result) {
			    	//Esta función se ejecuta si la petición ha ido bien. El cuerpo de la respuesta HTTP
			    	//se recibe en el parámetro 'result'
			    	
			    	//Se llama a la función que añade el elemento a la lista.
			    	load2(result.videojuego.nombreCat,result.videojuego.nombreVid);
	
			    },
		    	error: function(jqXhr, textStatus, errorMessage){
			    	alert('Error: ' + jqXhr.responseJSON.resultado);	
			    },
			    data:  JSON.stringify(sendInfo3) //Datos a enviar al servidor
			});
	    });
		
		//Se añade la función que se ejecutará al hacer clic sobre el botón identificado por "modificarVid"
		$("#modificarVid").click(function(){
		
		
		//Se construye el JSON a enviar 
		//no se ponen las comillas porque la función JSON.stringify ya lo hace.
		var sendInfo4 = {nombreCat: $('#nombreCat5').val(),nombreVid1: $('#nombreVid6').val(),nombreVid2: $('#nombreVid7').val()}
	    $.ajax({
			    url: 'rest/videojuego', //URL a la que invocar
			    headers: { 
		               'Accept': 'application/json',
		               'Content-Type': 'application/json' 
		           },
			    type: 'PUT', //Método del servicio rest a ejecutar
			    dataType: "json", 
			    success: function(result) {
			    	//Esta función se ejecuta si la petición ha ido bien. El cuerpo de la respuesta HTTP
			    	//se recibe en el parámetro 'result'
			    	
			    	//Se llama a la función que añade el elemento a la lista.
			    	
			    	load(result.videojuego.nombreCat,result.videojuego.nombreVid2);
			    	location.reload();
			    },
		    	error: function(jqXhr, textStatus, errorMessage){
			    	alert('Error: ' + jqXhr.responseJSON.resultado);	
			    },
			    data:  JSON.stringify(sendInfo4) //Datos a enviar al servidor
			    
			});
	    });
	
//Se invoca la petición REST que devuelve todas las categorias y se cargan dentro del <ul> de la página.
	$.ajax({
		  url: 'rest/categoria',
		  type: 'GET',
		  dataType: "json",
		  success: function(result) {
		    //Para cada elemento del array de result.users se ejecuta la función que se pasa como parámetro.
		    //Esa función tiene dos parámetros, i para la posición y val para el valor del elemento en curso.
		    jQuery.each(result.categorias, function(i, val) {
		    	load(val.nombre);
		    });
		  }
		});
		
	//Se invoca la petición REST que devuelve todas las categorias y se cargan dentro del <ul> de la página.
	$.ajax({
		  url: 'rest/videojuego',
		  type: 'GET',
		  dataType: "json",
		  success: function(result) {
		    //Para cada elemento del array de result.users se ejecuta la función que se pasa como parámetro.
		    //Esa función tiene dos parámetros, i para la posición y val para el valor del elemento en curso.
		    jQuery.each(result.videojuegos, function(i, val) {
		    	load2(val.nombreCat,val.nombreVid);
		    });
		    
		  }
		});
		
	});

</script>
</head>
<body>

<h2>Agregar Categoria</h2>
Nombre Categoria<input type=text id="nombreCat"><br>
<button id="agregarCat">Agregar Categoria</button>

<br>

<h2>Modificar Categoria</h2>
Nombre de Categoria<input type="text" id="nombreCat1" value=""><br>
Nuevo Nombre de Categoria<input type="text" id="nombreCat2" value=""><br>
<button id="modificarCat">Modificar Categoria</button>


<br><br><br>
Listado de categorias 
	<ul id="categorias"></ul>
	
	
<br><br>
<h2>Agregar Videojuego</h2>
Nombre de Categoria<input type=text id="nombreCat3" value=""><br>
Nombre de Videojuego <input type="text" id="nombreVid4" value=""><br>
<button id="agregarVid">Agregar Videojuego</button>
<br>

<h2> Modificar Videojuego</h2>
Nombre de Videojuego <input type="text" id="nombreVid6" value=""><br>
Nuevo Nombre de Categoria<input type="text" id="nombreCat5" value=""><br>
Nuevo Nombre de Videojuego <input type="text" id="nombreVid7" value=""><br>
<button id="modificarVid">Modificar Videojuego</button>
<br><br><br>

Listado de Videojuegos 
	<ul id="videojuegos"></ul>
	
<br><br>
<a href="panelusuario.jsp">Panel J2EE</a>
</body>
</html>