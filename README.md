codegenerator
=============

An application to generate code based on the requirements of a system.

This is a Proof of Concept that has been built.  

<h2>How it works</h2>
<ul>
  <li>A textual input with the requirements of the application is fed.</li>
  <li>The input is tokenized. The tokenization is done using the apache-opennlp library. </li>
  <li>The tokens generated are used to obtain the parts of speech of each word given in the requirements.</li>
  <li>The words tagged as nouns are the classes. The pronouns tagged will be used to link the classes with the attributes they own.</li>
  <li>java files are created for each class with the attributes they own.</li>
</ul>
