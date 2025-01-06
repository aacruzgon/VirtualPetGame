# Virtual Pet Game
Virtual Pet Game is an interactive desktop application created in Java using JavaFX. The game allows players to take care of a virtual pet by managing its hunger, health, sleep, and happiness levels. The application also includes features such as inventory management, parental controls to restrict gameplay hours, and a retro aesthetic for an engaging user experience.

# Required Libraries and Tools
To build and run the Virtual Pet Game, ensure you have the following tools installed:

- Java Development Kit (JDK): [Version 23.0.1](https://gluonhq.com/products/javafx/)
- Java: [Version 23.0.1](https://www.oracle.com/ca-en/java/technologies/downloads/#:~:text=JDK%2023%20is%20the%20latest%20release%20of%20the%20Java%20SE%20Platform.)
   - Make Sure JAVA_HOME environment variables are set: [Tutorial](https://www.baeldung.com/java-home-on-windows-mac-os-x-linux)
- Apache Maven: [Version 3.9.9](https://maven.apache.org/install.html)
- Launch4j: [Version 3.50](https://launch4j.sourceforge.net/) (for bundling into .exe format)

# Building the Software
### Step 1: Clone the Repository
1. Open a terminal/command prompt and clone the project in your desired location: `git clone <repo-url>`.

### Step 2: Build the Project
**YourPathTo** Is the path of where you are locating the required files, they are machine **INDEPENDENT**. **My YourPathTo** will be different from **Yours YourPathTo!!!**.
1. Navigate to the root of the directory `cd C:\YourPathTo\VirtualPetGame`.
2. Use Maven to clean package the project `mvn clean package`. This will generate a jar-with-dependencies JAR file in the target folder.
3. Ensure the target folder contains the VirtualPetGame-1.0-SNAPSHOT-jar-with-dependencies.jar:
   - `dir target` should show the two .jar files below:
   - `VirtualPetGame-1.0-SNAPSHOT-jar-with-dependencies.jar`
   - `VirtualPetGame-1.0-SNAPSHOT.jar`

Now, to make `VirtualPetGame-1.0-SNAPSHOT-jar-with-dependencies.jar` into a .exe file, we used Launch4j to bundle all required resources into one folder, and then made the .exe file, hence why the user does not need to have even Java to play the game, since we included a custom jre (java runtime environemnt),however, the user is ofcourse required to have Java if they wish to build and develop the game.

### Step 3: Gather Required Resources to Make the .exe File
1. Command to Create the JRE Folder `jlink --module-path "C:\Program Files\Java\jdk-23.0.1\jmods;C:\YourPathTo\javafx-jmods-23.0.1" --add-modules java.base,java.desktop,javafx.controls,javafx.fxml,javafx.media --output C:\Program Files\jre`. This will create a java runtime environment to be bundled inside the final game folder, which will allow users who do not have java installed to play the game.
2. Copy the JavaFX lib folder `C:\YourPathTo\javafx-jmods-23.0.1\lib`, copy the entire lib folder.
Put the `VirtualPetGame-1.0-SNAPSHOT-jar-with-dependencies.jar` from Step 2 and both folders from Step 3.1 and 3.2 into whichever directory you desire. For example: `C:\Program Files\GAME`
3. Prepare the Game Folder: Now, create a directory to place all the necessary files. For example, use C:\Program Files\GAME. Inside this folder, place:
   - VirtualPetGame-1.0-SNAPSHOT-jar-with-dependencies.jar (from the target folder).
   - The jre folder (from the jlink output).
   - The lib folder (from JavaFX SDK).

### Step 4: Bundling Files From Step 3.3 into an .exe File:
1. Download and install Launch4j if you have not already.
2. In the `Basic` tab:
   - Choose the output file name and its directory, preferebaly, keep it in the same directory you chose for Step 3, here, it is `C:\Program Files\GAME` do not forget to end the name with .exe
   - Choose the Jar file to be VirtualPetGame-1.0-SNAPSHOT-jar-with-dependencies.jar in the directory you chose, here, it is `C:\Program Files\GAME\VirtualPetGame-1.0-SNAPSHOT-jar-with-dependencies.jar`.
   - (Optional) Choose a .ico image for your .exe file.
   - Keep the `Change Dir` to the current directory, which is `.`.
3. In the `Classpath` tab: 
   - Put the Main class as org.example.VirtualPetGame
   - Add a New Classpath, type in .\lib and click Accept.
4. In the `JRE` tab:
   - Put the JRE paths as `.\jre`
   - Put the Min JRE version as 23.
   - Put in the JVM options the following: `--module-path ./lib --add-modules javafx.controls,javafx.fxml,javafx.media --add-opens=javafx.base/com.sun.javafx=ALL-UNNAMED`.
5. Click on the gear above in Launch4j to generate the .exe file, it will ask you to input a name for a .xml file, choose your desired name and directory for the generated .xml file, as it will not affect the process.

After generating the .exe file, you are able to run and play the game and distribute it to users who do not even have Java! 

# Using the Software
The software is very straight forward to use. Run the application by building the project, or by unzipping the GAME zip file and running the .exe file, and navigate your way in the game using the buttons provided.
## Parental Controls
### Password: 1234
In the Parental Controls you will be able to have a normal distribution of the sessions played in the game. You will be able to restrict time by setting a range in the format of ##:## where the range is from 00:00 to 23:59.



