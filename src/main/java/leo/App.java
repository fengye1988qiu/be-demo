package leo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class App {
   

	private static final int ONE_MIN = 60 * 1000;
	private static final String FILE_PATH = "payment.txt";
	private static final String QUIT_FLAG = "quit";

	private static final Map<String, Long> paymentMap = new HashMap<String, Long>();
    private static Boolean isContinue = true;
    private static Thread subThread;
	
	public static void main(String[] args) {
        readFile();
	}

	private static void startPrintTaskPerMin() {
		subThread = new Thread(new Runnable(){
			@Override
			public void run() {
				while (isContinue) {
                    doPrint();
                    try {    
                        Thread.sleep(ONE_MIN);    
                    } catch (InterruptedException e) {    
                        System.out.println("Subthread has exited.");
                    }    
                }
                
			}
		});
        subThread.start();
	}

	private static void readCommandLine(Scanner sc) {
		while(isContinue) {
			String line = sc.nextLine();
            if ("".equals(line)) continue;
            if (QUIT_FLAG.equals(line.trim())) {
                isContinue = false;
                continue;
            }
			String[] params = line.split(" ");
			if(params.length == 2) {
				try {
					doPayment(params[0].trim(), Long.valueOf(params[1].trim()));
					doPrint();
				} catch (Exception e) {
					System.out.println("Invalid Payment! " + line + e.getMessage());
				}
			} else System.out.println("Invalid Payment! " + line);
        }
        sc.close();
        System.out.println("Exiting sub-threads...");
        subThread.interrupt();
        writeFile();
	}

	private static void doPayment(String currency, Long amount) {
		if (!paymentMap.containsKey(currency)) paymentMap.put(currency, amount);
		else {
			Long currentAmount = paymentMap.get(currency) + amount;
			if (currentAmount == 0) {
				paymentMap.remove(currency);
			} else paymentMap.replace(currency, currentAmount);
		}
	}


	private static void doPrint() {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println("Updated At:" + new Date().toString());
		System.out.println("------------Current Payment-------------\n");
		paymentMap.forEach((currency, amount) -> System.out.println(currency + " " + amount + "\n"));
		if (paymentMap.size() == 0) System.out.println("No payment yet\n");
		System.out.println("----------------End---------------------\n");
		System.out.println("please input a current and amount separated by a space, like \"USD 1000\"(enter 'quit' to exit):");
    }
    
    public static void readFile() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Do you want to load Payment from a file (put the file to the project root directory and name it as" + FILE_PATH + ")? Y/N");
        Scanner sc = new Scanner(System.in);
        String isLoadFile = sc.nextLine();
        if ("Y".equals(isLoadFile.trim().toUpperCase())) {
            try (FileReader reader = new FileReader(FILE_PATH);
                 BufferedReader br = new BufferedReader(reader)
            ) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] params = line.split(" ");
                    System.out.println(line);
                    if(params.length == 2) {
                        try {
                            doPayment(params[0].trim(), Long.valueOf(params[1].trim()));
                        } catch (Exception e) {
                            System.out.println("Invalid Payment! " + line + e.getMessage());
                        }
                    } else System.out.println("Invalid Payment! " + line);
                }
                doPrint();
                reader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        startPrintTaskPerMin();
        readCommandLine(sc);
    }

    public static void writeFile() {
        System.out.println("Save Payment...");
        try {
            File writeName = new File(FILE_PATH);
            writeName.createNewFile();
            try (
                FileWriter writer = new FileWriter(writeName);
                BufferedWriter out = new BufferedWriter(writer)
            ) {
                paymentMap.forEach((currency, amount) -> {
                    try {
                        out.write(currency + " " + amount + "\r\n");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                });
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Save Payment done.");
    }
}
