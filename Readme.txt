=======KURUKURU==========
(i forgot the name)

SIT's [Advanced Programming Exercises in Java] OOP project. 

maybe we explain how the game works or something

==========STAGES=========

A stage requires at most 4 files:
- name (txt file, the stage settings)
- name-wall (image file, where black color is used for collision tests)
- name-ground (image file, draw below the bar)
- name-roof (image file, draw above the bar)

In the menu, the game checks for txt files inside the stage directory. If the same file name has a -wall image, this stage will be available. If there's no -ground and no -roof image, the -wall image will be used as a -ground one. If there is a -ground and/or roof image, it/they will be used accordingly.

=========================


