1. run project
  a. Open terminal and go to the root of project where you can see a file named "pom.xml".
  b. key in "mvn compile && mvn exec:java -Dexec.mainClass="leo.App" and then press enter.

2. Sequence of program execution
  a. At first, Choose to load payment form a file name "payment.txt" where is in the project root directory
  b. Start a sub thread to print current payment list per min
  c. Input a currency and amount (e.g: USD 1000) as a payment in command line and press enter, 
     the payment will be updated to current payment list. There is a error msg if the payment is not valid.
  d. Input "quit" to quit the program, the payment list will be save to the file named "payment.txt".
