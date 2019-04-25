# Screenshot-Script-Generator
About the code:

This is the first time I use GitHub, so I'm not quite sure if I did everything correctly. If you plan to take a look at this program in eclipse you need to change the 'materials' and 'src' folders into source folders, elsewise it wont load any icons. This is the result of my lacking experience with GitHub.
The code itself isn't a masterpiece aswell, but it gets the job done.

About the program:

So I made a small program, which converts a .txt file containing 'setpos' and/or 'setang' commands into a vscript. Additionally the program creates a .cfg file, which allows the execution of the vscript file with one console command.

What does the vscript file do if I execute it?
It will automatically teleport you to the first setpos command of your .txt file, after this it is going to wait for a fraction of a second. When standing still the vscript will take a screnshot. This process repeats until there are no more 'setpos' commands in the .txt file.

Why should I use this?
This method of taking screenshots of your map doesn't require any entities, this means you don't need to constantly think about where to place the next point_dev_camera. Additionally allows this method an easy way to take comparison screenshots of official and custom maps between different versions.

How do I use this program?
You can find a full guide here: https://steamcommunity.com/sharedfiles/filedetails/?id=1721550921

This is my first full program, so there are probably some issues. Feel free to leave any issues or suggestions on the steam guide.
