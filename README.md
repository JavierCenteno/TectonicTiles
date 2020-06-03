<h1>Tectonic tiles v0.4</h1>

<p>(c) 2020</p>

<p><a href="./LICENSE" title="LICENSE">This software is licensed under a GNU general public license.</a></p>



<h2>About</h2>

<p>Tectonic tiles is a program written in Java that randomly generates terrain using an algorithm inspired by plate tectonics.</p>

<p>This program is Javier Centeno Vega's end of degree project (TFG) for the Software Engineering degree at the University of Seville.</p>



<h2>The algorithm</h2>

<p>The algorithm works by creating a lattice of tiles, which is tessellated into tectonic plates. Each tectonic plate is assigned a movement defined by a starting tile and an ending tile.</p>

<p>This movement is applied to the terrain through a crease function, which given a starting tile, an ending tile and an influenced tile returns the influence on the influenced tile from the movement of the crust from the starting tile to the ending tile.</p>

<p>For the movement of all tectonic plates, the influence is calculated and added to all tiles.</p>



<h2>Download</h2>

<a href="https://github.com/JavierCenteno/TectonicTiles/tree/master/runnable" title="Runnable">You can download the runnable here.</a>



<h2>Help</h2>

<ul>
	<li><a href="./help/eng-USA.md" title="English (USA)">English (USA)</a></li>
	<li><a href="./help/esp.md" title="Español neutro">Español neutro</a></li>
</ul>



<h2>Planned features</h2>

<ul>
	<li>Different ways to choose the start and end tiles of a movement vector. For example, choosing tiles outside of the tectonic plate or choosing tiles within a circle around the plate rather than in the plate.</li>
	<li>Add a material layer to the terrain which indicates which material the soil is made out of. Harder materials should be higher up (that'd enable plateaux to form). Could also be possible to add a terrain granularity/terrain hardness layer.</li>
	<li>Add ways of generating hidrology such as rivers and dams.</li>
</ul>

