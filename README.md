# RectangleBinaryTree
Key value, <String, Rectangle>, binary tree used to store and query a group of rectangles, that fully exist inside a predefined plane (1024x1024). Input is given as a text file with one command per line, and output is sent to the console. The commands for insert and simple remove are "insert [name] x y w h", where x y are the coordinates for the upper left corner of the rectangle and w h are its width and height, and "remove [name]". You can remove by dimension, since duplicates are allowed, with the command "remove x y w h".  
  
  other commands:  
  search by name - command "search [name]" - outputs the list of rectangles that have that name  
    
  region search - command "regionsearch x y w h"- outputs the list of rectangles that intersect the region described by x y w h  
    
dump - command "dump" - outputs a list of the tree's nodes with the depth of each node and the trees size  

intersections - command "intersections" - produces a list of all the unique pairs of intersecting rectangles
