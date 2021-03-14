# voter-database
Title: Interactive Voter Information Database or (IVID)

Authors: Rhys Z. Vulpe and Shay Iyer

Class: CSC212 Fall 2020 Smith College 

Abstract:

The purpose of this program is to allow a user to enter a voter database and build a profile, basing their affiliated political party (Republican or Democrat) on their opinions and not on their influences external to their own beliefs. From here, the user is given the opportunity to connect with the other voters in the database and store any potential resources or journal entries they have acquired in a resource file that is saved to the same folder as the program.
The program opens by asking the user to answer 11 agree/disagree questions about their social, political, and economic beliefs on partisan topics in the US. The user is assigned a political party based on their answers and are given prompts to create a user profile. The program then allows users to view their information, change any information if they can answer their security question (what is their favorite animal) within 10 guesses, retrieve contact information for users in the same assigned party as them (user has the option to write this contact information in their resource file), retrieve names of users in either party, create/fill out a resource file of their own, and ultimately save their user profile to the database file voterdata.csv when they choose to exit.
You must have Java software installed on your computer.


To run this program:
1. Download the folder “IVIDpkg” from Google Drive. You must download the entire folder.
   * Files included in this folder: 
      * ivid.java
      * voterdata.csv
         * columns from left to right are: party, name, age, race, favorite animal, email, phone number
      * README.txt
   * Files written in this folder upon running program
      * myresourcefile.txt 
2. Type the following paste in OS Command Line (Mac: Terminal, Windows/Linus, Command Line)
> cd ~/Downloads/IVIDpkg
> java ivid.java
	      * The program will then run in your command line. Type all responses on the same page and in the same line. All files are saved and updated in 
~/Downloads/IVIDpkg
