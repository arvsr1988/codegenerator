codegenerator
=============

An application to generate code based on the requirements of a system.

This is a Proof of Concept that has been built.  

The application works as follows : 
• A textual input with the requirements of the application is fed to the application.
• The input is tokenized. The tokenization is done using the apache-opennlp library.
• The tokens generated are used to obtain the parts of speech of each word given in the requirements.
• The words tagged as nouns are the classes. The pronouns tagged will be used to link the classes with the attributes they own.
• .java files are created for each class with the attributes they own.
