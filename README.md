<h1>Tectonic tiles v0.3</h1>

<p>(c) 2020</p>

<p><a href="./LICENSE" title="LICENSE">This software is licensed under a GNU general public license.</a></p>



<h2>About</h2>

<p>Tectonic tiles is a program written in Java that randomly generates terrain using an algorithm inspired by plate tectonics.</p>

<p>This program is Javier Centeno Vega's end of degree project (TFG) for the Software Engineering degree at the University of Seville.</p>



<h2>The algorithm</h2>

<p>The algorithm works by creating a lattice of tiles, which is tessellated into tectonic plates. Each tectonic plate is assigned a movement defined by a starting tile and an ending tile.</p>

<p>This movement is applied to the terrain through a crease function, which given a starting tile, an ending tile and an influenced tile returns the influence on the influenced tile from the movement of the crust from the starting tile to the ending tile.</p>

<p>For the movement of all tectonic plates, the influence is calculated and added to all tiles.</p>



<h2>Help</h2>

<ul>
	<li><a href="./help/eng-USA.md" title="English (USA)">English (USA)</a></li>
	<li><a href="./help/esp.md" title="Español neutro">Español neutro</a></li>
</ul>
