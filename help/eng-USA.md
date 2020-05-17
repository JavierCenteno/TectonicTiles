<h1>Tectonic Tiles v0.3: User manual</h1>

<a href="https://github.com/JavierCenteno/TectonicTiles">Este proyecto está desarrollado en GitHub.</a>

<h2 id="Index">Index</h2>

<ol>
	<li>
		<a href="#RequirementsAndInstallation">Requirements and installation</a>
	</li>
	<li>
		<a href="#ConsoleMode">Console mode</a>
		<ol>
			<li>
				<a href="#ConsoleMode#Run">How to run the software</a>
			</li>
			<li>
				<a href="#ConsoleMode#Command">Commands</a>
			</li>
			<li>
				<a href="#ConsoleMode#Generate">How to generate a terrain</a>
			</li>
			<li>
				<a href="#ConsoleMode#Import">How to import a terrain</a>
			</li>
			<li>
				<a href="#ConsoleMode#Export">How to export a terrain</a>
			</li>
			<li>
				<a href="#ConsoleMode#Print">How to convert a terrain to image</a>
			</li>
			<li>
				<a href="#ConsoleMode#Configure">How to change the settings</a>
			</li>
		</ol>
	</li>
	<li>
		<a href="#GraphicalMode">Graphical mode</a>
		<ol>
			<li>
				<a href="#GraphicalMode#Run">How to run the software</a>
			</li>
			<li>
				<a href="#GraphicalMode#Generate">How to generate a terrain</a>
			</li>
			<li>
				<a href="#GraphicalMode#Import">How to import a terrain</a>
			</li>
			<li>
				<a href="#GraphicalMode#Export">How to export a terrain</a>
			</li>
			<li>
				<a href="#GraphicalMode#Print">How to convert a terrain to image</a>
			</li>
			<li>
				<a href="#GraphicalMode#Configure">How to change the settings</a>
			</li>
		</ol>
	</li>
	<li>
		<a href="#TerrainGeneration">Terrain generation</a>
		<ol>
			<li>
				<a href="#TerrainGeneration#TerrainTypes">Terrain types</a>
			</li>
			<li>
				<a href="#TerrainGeneration#CreaseTypes">Crease types</a>
			</li>
		</ol>
	</li>
	<li>
		<a href="#InstructionsForDevelopers">Instructions for developers</a>
		<ol>
			<li>
				<a href="#InstructionsForDevelopers#Generate">How to generate a terrain</a>
			</li>
			<li>
				<a href="#InstructionsForDevelopers#TerrainFile">Terrain file reference</a>
			</li>
		</ol>
	</li>
</ol>



<h2 id="RequirementsAndInstallation">Requirements and installation</h2>

<p>The software requires Java 8 or above.</p>

<p>In order to install, you must download and extract the file <code>TectonicTiles.zip</code>.</p>



<h2 id="ConsoleMode">Console mode</h2>

<p>The console mode assumes familiarity with the command line. For graphical mode, go to <a href="#GraphicalMode">Graphical mode</a>.</p>



<h3 id="ConsoleMode#Run">How to run the software</h3>

<p>In Windows, it is possible to run the console mode directly by double clicking the file <code>Tectonic Tiles Windows CL.bat</code>. If you do this, it is not necessary to follow the rest of the instructions to run the software.</p>

<p>In any system, in order to run the software it is necessary to run the file TectonicTiles.jar using java with a <code>cl</code> argument. This is done in the command line inside the <code>TectonicTiles</code> folder with the following command.</p>

<p><code>java -jar TectonicTiles.jar cl</code></p>

<p>The software has the following modes for users: Command line (with <code>cl</code> as an argument) and graphical (with <code>gui</code> as an argument).</p>



<h3 id="ConsoleMode#Command">Commands</h3>

<p>The list of commands depends on the software's language settings. If the software is in English, you can use the command <code>help</code> to see the list of commands.</p>



<h3 id="ConsoleMode#Generate">How to generate a terrain</h3>

<p>To generate a terrain, input the command <code>generate</code> and then follow the instructions.</p>

<p>Once a terrain is generated or imported, you can export it (see <a href="#ConsoleMode#Export">How to export a terrain</a>) o convert it to image (see <a href="#ConsoleMode#Print">How to convert a terrain to image</a>).</p>



<h3 id="ConsoleMode#Import">How to import a terrain</h3>

<p>To import a terrain, input the command <code>import</code> and then follow the instructions.</p>

<p>Once a terrain is imported, you can export it (see <a href="#ConsoleMode#Export">How to export a terrain</a>) o convert it to image (see <a href="#ConsoleMode#Print">How to convert a terrain to image</a>).</p>

<p>To learn more about the export file format for the creation of software to use these files, see <a href="#InstructionsForDevelopers#TerrainFile">Terrain file reference</a>.</p>



<h3 id="ConsoleMode#Export">How to export a terrain</h3>

<p>Once a terrain is generated or imported, input the command <code>export</code> and then follow the instructions.</p>

<p>To learn more about the export file format for the creation of software to use these files, see <a href="#InstructionsForDevelopers#TerrainFile">Terrain file reference</a>.</p>



<h3 id="ConsoleMode#Print">How to convert a terrain to image</h3>

<p>Once a terrain is generated or imported, input the command <code>print</code> and then follow the instructions.</p>



<h3 id="ConsoleMode#Configure">How to change the settings</h3>

<p>To configure the software, input the command <code>configure</code> and then follow the instructions.</p>



<h2 id="GraphicalMode">Graphical mode</h2>



<h3 id="GraphicalMode#Run">How to run the software</h3>

<p>The software can be run directly in graphical mode by double clicking the file <code>TectonicTiles.jar</code>. If you're in Windows, it is also possible to double click the file <code>Tectonic Tiles Windows GUI.bat</code>. Plus, it is possible to run the software in graphical mode from a command line with a <code>gui</code> argument (See <a href="#ConsoleMode">Console mode</a>). This is done in the command line inside the <code>TectonicTiles</code> folder with the following command.</p>

<p><code>java -jar TectonicTiles.jar gui</code></p>



<h3 id="GraphicalMode#Generate">How to generate a terrain</h3>

<p>To generate a terrain, input the desired parameters and then press the <code>generate</code> button.</p>

<p>Once a terrain is generated, you can export it (see <a href="#GraphicalMode#Export">How to export a terrain</a>) o convert it to image (see <a href="#GraphicalMode#Print">How to convert a terrain to image</a>).</p>



<h3 id="GraphicalMode#Import">How to import a terrain</h3>

<p>To import a terrain, press the <code>import</code> button and select a file to import from.</p>

<p>Once a terrain is imported, you can export it (see <a href="#ConsoleMode#Export">How to export a terrain</a>) o convert it to image (see <a href="#ConsoleMode#Print">How to convert a terrain to image</a>).</p>

<p>To learn more about the export file format for the creation of software to use these files, see <a href="#InstructionsForDevelopers#TerrainFile">Terrain file reference</a>.</p>



<h3 id="GraphicalMode#Export">How to export a terrain</h3>

<p>Once a terrain is generated or imported, press the <code>export</code> button and select a file to export to.</p>

<p>To learn more about the export file format for the creation of software to use these files, see <a href="#InstructionsForDevelopers#TerrainFile">Terrain file reference</a>.</p>



<h3 id="GraphicalMode#Print">How to convert a terrain to image</h3>

<p>Once a terrain is generated or imported, press the <code>print</code> button and select a file to print to.</p>



<h3 id="GraphicalMode#Configure">How to change the settings</h3>

<p>To configure the software, press the <code>configure</code> button.</p>



<h2 id="TerrainGeneration">Terrain generation</h2>



<h3 id="TerrainGeneration#TerrainTypes">Terrain types</h3>

<p>There are the following terrain types:</p>

<ul>
	<li>Square</li>
</ul>

<h3 id="TerrainGeneration#CreaseTypes">Crease types</h3>

<p>There are the following crease types:</p>

<ul>
	<li>Cone</li>
	<li>Diamond</li>
	<li>Pyramid</li>
	<li>Hill 1</li>
	<li>Hill 2</li>
	<li>Hill 3</li>
</ul>

<p>Creases take the following parameters:</p>

<ul>
	<li>Height factor</li>
	<li>Height power</li>
	<li>Width factor</li>
	<li>Width power</li>
</ul>

<p>Height factor is the number by which the height of the creases is multiplied.</p>

<p>Height power is the number the height of the creases is elevated to.</p>

<p>Width factor is the number by which the width of the creases is multiplied.</p>

<p>Width power is the number the width of the creases is elevated to.</p>



<h2 id="InstructionsForDevelopers">Instructions for developers</h2>



<h3 id="InstructionsForDevelopers#Generate">How to generate a terrain</h3>

<p>In order to generate a terrain (or perform any other action), you just need to instance the configurers of the desired terrain and crease types and then call the methods of the terrain configurer that correspond to the action you want to perform.</p>

<code>
<br>01 InputStream is = null;
<br>02 OutputStream os = null;
<br>03 RandomGenerator rng = new Xorshift64StarGenerator();
<br>04 
<br>05 ConeConfigurer creaseConfigurer = new ConeConfigurer("");
<br>06 creaseConfigurer.getHeightFactor().setCurrentValue(1.0d);
<br>07 creaseConfigurer.getHeightPower().setCurrentValue(1.0d);
<br>08 creaseConfigurer.getWidthFactor().setCurrentValue(4.0d);
<br>09 creaseConfigurer.getWidthPower().setCurrentValue(1.0d);
<br>10 Crease crease = creaseConfigurer.generate();
<br>11 
<br>12 SquareTerrainConfigurer terrainConfigurer = new SquareTerrainConfigurer("");
<br>13 terrainConfigurer.getPlateSize().setCurrentValue(20);
<br>14 terrainConfigurer.getNumberOfPlatesX().setCurrentValue(40);
<br>15 terrainConfigurer.getNumberOfPlatesY().setCurrentValue(20);
<br>16 terrainConfigurer.getWaterParameters().getDeterminer().setCurrentValue(true);
<br>17 terrainConfigurer.getWaterParameters().getSeaLevel().setCurrentValue(16);
<br>18 terrainConfigurer.getMagmaParameters().getDeterminer().setCurrentValue(false);
<br>19 
<br>20 terrainConfigurer.generate(rng, crease);
<br>21 
<br>22 try {
<br>23 	terrainConfigurer.importTerrain("csv", is);
<br>24 	terrainConfigurer.exportTerrain("csv", os);
<br>25 } catch (IOException e) {
<br>26 	e.printStackTrace();
<br>27 }
<br>28 
<br>29 RenderedImage im = terrainConfigurer.toImage();
</code>



<h3 id="InstructionsForDevelopers#TerrainFile">Terrain file reference</h3>

<p>Terrain files consist of a CSV file that contains every layer of the terrain separated by two line breaks.</p>

<p>If a layer is not present in the terrain, it is represented by a dash (<code>-</code>).</p>

<p>If a layer is present, it is represented following the CSV format. Every row of each layer is separated by a line break and each tile is separated by a comma (<code>,</code>). The decimal separator is represented by a dot (<code>.</code>).</p>

<p>The layers appear in the following order: height, water, magma.</p>

