*** SikuliX-1.0MacBeta ***
--------------------------

Be aware: 
- still work in progress - might contain bugs
- not everything is tested
- not everything is implemented / documented


*** Installation
unzip to any location you like


*** Usage ONLY from command line
- go to the folder containing the installation directory
- use either sikuli-ide (starts the IDE) or sikli-script (interactive or run scripts) this way:
. sikuli-ide  (cannot be used to run scripts from commandline)
. sikuli-script (supported options: -h (help), -i (interactive), -r (run a script))

to run the command files

- you might make them executable using chmod
- if you want to run the command files from any other directory, you have to use
export SIKULI_HOME=<absolute path to installation folder>
on the commandline before using the above commands


*** Java-Version:
- the stuff is compiled with Java 6 latest version
- it runs on Java 7 and OpenJDK 7
- it is run with the current default Java version on your machine
- use "/usr/libexec/java_home -V" to find out, what you have on your machine
- having more than one Java version on your machine, use option 
  -j 6 to run with your Java 1.6 (Apple)
  -j 7 to run with your Java 1.7 (Oracle)
  -j o to run with OpenJDK 7 (installed in standard place /Library/Java/JavaVirtualMachines)