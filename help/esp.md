<h1>Casillas Tectónicas v0.3: Manual de usuario</h1>

<a href="https://github.com/JavierCenteno/TectonicTiles">This project is developed on GitHub.</a>

<h2 id="Index">Índice</h2>

<ol>
	<li>
		<a href="#RequirementsAndInstallation">Requisitos e instalación</a>
	</li>
	<li>
		<a href="#ConsoleMode">Modo de consola</a>
		<ol>
			<li>
				<a href="#ConsoleMode#Run">Como ejecutar el programa</a>
			</li>
			<li>
				<a href="#ConsoleMode#Command">Comandos</a>
			</li>
			<li>
				<a href="#ConsoleMode#Generate">Cómo generar un terreno</a>
			</li>
			<li>
				<a href="#ConsoleMode#Import">Cómo importar un terreno</a>
			</li>
			<li>
				<a href="#ConsoleMode#Export">Cómo exportar un terreno</a>
			</li>
			<li>
				<a href="#ConsoleMode#Print">Cómo convertir un terreno a imagen</a>
			</li>
			<li>
				<a href="#ConsoleMode#Configure">Cómo cambiar la configuración</a>
			</li>
		</ol>
	</li>
	<li>
		<a href="#GraphicalMode">Modo gráfico</a>
		<ol>
			<li>
				<a href="#GraphicalMode#Run">Como ejecutar el programa</a>
			</li>
			<li>
				<a href="#GraphicalMode#Generate">Cómo generar un terreno</a>
			</li>
			<li>
				<a href="#GraphicalMode#Import">Cómo importar un terreno</a>
			</li>
			<li>
				<a href="#GraphicalMode#Export">Cómo exportar un terreno</a>
			</li>
			<li>
				<a href="#GraphicalMode#Print">Cómo convertir un terreno a imagen</a>
			</li>
			<li>
				<a href="#GraphicalMode#Configure">Cómo cambiar la configuración</a>
			</li>
		</ol>
	</li>
	<li>
		<a href="#TerrainGeneration">Generación de terreno</a>
		<ol>
			<li>
				<a href="#TerrainGeneration#TerrainTypes">Tipos de terreno</a>
			</li>
			<li>
				<a href="#TerrainGeneration#CreaseTypes">Tipos de pliegue</a>
			</li>
		</ol>
	</li>
	<li>
		<a href="#InstructionsForDevelopers">Instrucciones para desarrolladores</a>
		<ol>
			<li>
				<a href="#InstructionsForDevelopers#Generate">Cómo generar un terreno</a>
			</li>
			<li>
				<a href="#InstructionsForDevelopers#TerrainFile">Referencia de los archivos de terreno</a>
			</li>
		</ol>
	</li>
</ol>



<h2 id="RequirementsAndInstallation">Requisitos e instalación</h2>

<p>El programa requiere tener instalado Java 8 o superior.</p>

<p>Para instalar, se debe descargar y descomprimir el archivo <code>TectonicTiles.zip</code>.</p>



<h2 id="ConsoleMode">Modo de consola</h2>

<p>El modo de consola asume familiaridad con la consola de comandos. Para el modo gráfico, saltar directamente a la sección <a href="#GraphicalMode">Modo gráfico</a>.</p>



<h3 id="ConsoleMode#Run">Como ejecutar el programa</h3>

<p>En Windows, es posible ejecutar el modo de línea de comandos directamente haciendo doble click sobre el archivo <code>Tectonic Tiles Windows CL.bat</code>. Si se hace esto, no es necesario seguir el resto de instrucciones para ejecutar el programa.</p>

<p>En cualquier sistema, para ejecutar el programa se debe ejecutar el archivo TectonicTiles.jar usando java con el argumento <code>cl</code>. Esto se hace en la línea de comandos dentro de la carpeta <code>TectonicTiles</code> mediante el siguiente comando.</p>

<p><code>java -jar TectonicTiles.jar cl</code></p>

<p>El programa tiene los siguientes modos de cara a los usuarios: Línea de comandos (con el argumento <code>cl</code>) y gráfico (con el argumento <code>gui</code>).</p>



<h3 id="ConsoleMode#Command">Comandos</h3>

<p>La lista de comandos depende de la configuración de lenguaje del programa. Se puede usar el comando <code>ayuda</code> si el programa está en español (o <code>help</code> si está en inglés) para ver la lista de comandos.</p>



<h3 id="ConsoleMode#Generate">Cómo generar un terreno</h3>

<p>Para generar un terreno, escriba el comando <code>generar</code> y luego siga las instrucciones.</p>

<p>Una vez generado un terreno, puede exportarlo (ver <a href="#ConsoleMode#Export">Cómo exportar un terreno</a>) o convertirlo a imagen (ver <a href="#ConsoleMode#Print">Cómo convertir un terreno a imagen</a>).</p>



<h3 id="ConsoleMode#Import">Cómo importar un terreno</h3>

<p>Para importar un terreno, escriba el comando <code>importar</code> y luego siga las instrucciones.</p>

<p>Una vez importado un terreno, puede exportarlo (ver <a href="#ConsoleMode#Export">Cómo exportar un terreno</a>) o convertirlo a imagen (ver <a href="#ConsoleMode#Print">Cómo convertir un terreno a imagen</a>).</p>

<p>Para aprender más sobre el formato de archivo de exportación para la creación de programas que usen estos archivos, ver <a href="#InstructionsForDevelopers#TerrainFile">Referencia de los archivos de terreno</a>.</p>



<h3 id="ConsoleMode#Export">Cómo exportar un terreno</h3>

<p>Una vez un terreno ha sido generado o importado, escriba el comando <code>exportar</code> y luego siga las instrucciones.</p>

<p>Para aprender más sobre el formato de archivo de exportación para la creación de programas que usen estos archivos, ver <a href="#InstructionsForDevelopers#TerrainFile">Referencia de los archivos de terreno</a>.</p>



<h3 id="ConsoleMode#Print">Cómo convertir un terreno a imagen</h3>

<p>Una vez un terreno ha sido generado o importado, escriba el comando <code>imprimir</code> y luego siga las instrucciones.</p>



<h3 id="ConsoleMode#Configure">Cómo cambiar la configuración</h3>

<p>Para cambiar la configuración, escriba el comando <code>configurar</code> y luego siga las instrucciones.</p>



<h2 id="GraphicalMode">Modo gráfico</h2>



<h3 id="GraphicalMode#Run">Como ejecutar el programa</h3>

<p>El programa se puede ejecutar en modo gráfico directamente haciendo doble click sobre el archivo <code>TectonicTiles.jar</code>. Si se está en Windows, también se puede hacer doble click sobre el archivo <code>Tectonic Tiles Windows GUI.bat</code>. Además, se puede ejecutar el programa en modo gráfico desde una línea de comandos con el argumento <code>gui</code> (ver la sección <a href="#ConsoleMode">Modo de consola</a>). Esto se hace en la línea de comandos dentro de la carpeta <code>TectonicTiles</code> mediante el siguiente comando.</p>

<p><code>java -jar TectonicTiles.jar gui</code></p>



<h3 id="GraphicalMode#Generate">Cómo generar un terreno</h3>

<p>Para generar un terreno, inserte los parámetros deseados y luego pulse el botón <code>generar</code>.</p>

<p>Una vez generado un terreno, puede exportarlo (ver <a href="#GraphicalMode#Export">Cómo exportar un terreno</a>) o convertirlo a imagen (ver <a href="#GraphicalMode#Print">Cómo convertir un terreno a imagen</a>).</p>



<h3 id="GraphicalMode#Import">Cómo importar un terreno</h3>

<p>Para importar un terreno, pulse el botón <code>importar</code> y seleccione un archivo desde el cual importar.</p>

<p>Una vez importado un terreno, puede exportarlo (ver <a href="#GraphicalMode#Export">Cómo exportar un terreno</a>) o convertirlo a imagen (ver <a href="#GraphicalMode#Print">Cómo convertir un terreno a imagen</a>).</p>

<p>Para aprender más sobre el formato de archivo de exportación para la creación de programas que usen estos archivos, ver <a href="#InstructionsForDevelopers#TerrainFile">Referencia de los archivos de terreno</a>.</p>



<h3 id="GraphicalMode#Export">Cómo exportar un terreno</h3>

<p>Una vez un terreno ha sido generado o importado, pulse el botón <code>exportar</code> y seleccione un archivo hacia el cual exportar.</p>

<p>Para aprender más sobre el formato de archivo de exportación para la creación de programas que usen estos archivos, ver <a href="#InstructionsForDevelopers#TerrainFile">Referencia de los archivos de terreno</a>.</p>



<h3 id="GraphicalMode#Print">Cómo convertir un terreno a imagen</h3>

<p>Una vez un terreno ha sido generado o importado, pulse el botón <code>exportar</code> y seleccione un archivo hacia el cual exportar.</p>



<h3 id="GraphicalMode#Configure">Cómo cambiar la configuración</h3>

<p>Para cambiar la configuración, pulse el botón <code>configurar</code>.</p>



<h2 id="TerrainGeneration">Generación de terreno</h2>



<h3 id="TerrainGeneration#TerrainTypes">Tipos de terreno</h3>

<p>Hay los siguientes tipos de terreno disponibles:</p>

<ul>
	<li>Cuadrado</li>
</ul>

<h3 id="TerrainGeneration#CreaseTypes">Tipos de pliegue</h3>

<p>Hay los siguientes tipos de pliegue disponibles:</p>

<ul>
	<li>Cono</li>
	<li>Diamante</li>
	<li>Pirámide</li>
	<li>Colina 1</li>
	<li>Colina 2</li>
	<li>Colina 3</li>
</ul>

<p>Los pliegues toman los siguientes parámetros:</p>

<ul>
	<li>Factor de altura</li>
	<li>Potencia de altura</li>
	<li>Factor de anchura</li>
	<li>Potencia de anchura</li>
</ul>

<p>El factor de altura es el número por el cual se multiplica la altura de los pliegues.</p>

<p>La potencia de altura es el número al cual se eleva la altura de los pliegues.</p>

<p>El factor de anchura es el número por el cual se multiplica la anchura de los pliegues.</p>

<p>La potencia de anchura es el número al cual se eleva la anchura de los pliegues.</p>



<h2 id="InstructionsForDevelopers">Instrucciones para desarrolladores</h2>



<h3 id="InstructionsForDevelopers#Generate">Cómo generar un terreno</h3>

<p>Para generar un terreno (o realizar cualquier otra acción), necesita instanciar los configuradores de los tipos de terreno y pliegue deseados y luego llamar a los métodos del configurador de terreno que se corresponden con la acción que se desea realizar.</p>

<code>
InputStream is = null;
OutputStream os = null;
RandomGenerator rng = new Xorshift64StarGenerator();

ConeConfigurer creaseConfigurer = new ConeConfigurer("");
creaseConfigurer.getHeightFactor().setCurrentValue(1.0d);
creaseConfigurer.getHeightPower().setCurrentValue(1.0d);
creaseConfigurer.getWidthFactor().setCurrentValue(4.0d);
creaseConfigurer.getWidthPower().setCurrentValue(1.0d);
Crease crease = creaseConfigurer.generate();

SquareTerrainConfigurer terrainConfigurer = new SquareTerrainConfigurer("");
terrainConfigurer.getPlateSize().setCurrentValue(20);
terrainConfigurer.getNumberOfPlatesX().setCurrentValue(40);
terrainConfigurer.getNumberOfPlatesY().setCurrentValue(20);
terrainConfigurer.getWaterParameters().getDeterminer().setCurrentValue(true);
terrainConfigurer.getWaterParameters().getSeaLevel().setCurrentValue(16);
terrainConfigurer.getMagmaParameters().getDeterminer().setCurrentValue(false);

terrainConfigurer.generate(rng, crease);

try {
	terrainConfigurer.importTerrain("csv", is);
	terrainConfigurer.exportTerrain("csv", os);
} catch (IOException e) {
	e.printStackTrace();
}

RenderedImage im = terrainConfigurer.toImage();
</code>



<h3 id="InstructionsForDevelopers#TerrainFile">Referencia de los archivos de terreno</h3>

<p>El archivo de terreno se trata de un archivo CSV que contiene cada capa del terreno, separada por dos saltos de línea.</p>

<p>Si una capa no está presente en el terreno, esta es representada por un guión (<code>-</code>).</p>

<p>Si una capa está presente, está representada siguiendo el formato CSV. Cada línea de cada capa está separada por un salto de línea, y cada casilla está separada por una coma (<code>,</code>). El separador decimal está representado por un punto (<code>.</code>).</p>

<p>Las capas aparecen en este orden: altitud, agua, magma.</p>

