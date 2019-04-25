# Screenshot-Script-Generator
So I made a small program, which converts a .txt file containing 'setpos' and/or 'setang' commands into a vscript. Additionally the program creates a .cfg file, which allows the execution of the vscript file with one console command.

What does the vscript file do if I execute it?
It will automatically teleport you to the first setpos command of your .txt file, after this it is going to wait for a fraction of a second. When standing still the vscript will take a screnshot. This process repeats until there are no more 'setpos' commands in the .txt file.

Why should I use this?
This method of taking screenshots of your map doesn't require any entities, this means you don't need to constantly think about where to place the next point_dev_camera. Additionally allows this method an easy way to take comparison screenshots of official and custom maps between different versions.
