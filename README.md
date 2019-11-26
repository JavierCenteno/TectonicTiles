# Tectonic tiles

v0.1

(c) 2019

[This software is licensed under a GNU general public license.](./LICENSE "LICENSE")

## About

Tectonic tiles is a program written in Java that randomly generates terrain using an algorithm inspired by plate tectonics.

This program is Javier Centeno Vega's end of degree project (TFG) for the Software Engineering degree at the University of Seville.

## The algorithm

The algorithm works by creating a lattice of tiles, which is tessellated into tectonic plates. Each tectonic plate is assigned a movement defined by a starting tile and an ending tile.

This movement is applied to the terrain through a crease function, which given a starting tile, an ending tile and an influenced tile returns the influence on the influenced tile from the movement of the crust from the starting tile to the ending tile.

For the movement of all tectonic plates, the influence is calculated and added to all tiles.

