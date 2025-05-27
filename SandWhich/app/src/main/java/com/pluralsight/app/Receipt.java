package com.pluralsight.app;

import com.pluralsight.Logging;
import com.pluralsight.app.item.Item;
import com.pluralsight.app.ui.UserOutput;
import com.pluralsight.annotation.system.OnStartUp;

import javax.print.attribute.standard.Severity;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Receipt {
    public static Path receiptFolder;

    @OnStartUp
    public static void setup(){
        //Load folder path from env
        String path = System.getenv("RECEIPT_FOLDER");

        //Use loaded path or default to resources
        receiptFolder = Path.of(path != null ? path : "src/main/resources");
    }

    public static void process(ArrayList<Item> items){
        //Check if the receiptFolderExists
        if(Files.notExists(receiptFolder)) {
            try {
                //If Not attempt to create it
                Files.createDirectory(receiptFolder);
            } catch (IOException e) {
                //If folder doesn't exist and fails to create we need to shut down
                UserOutput.display("Folder Creation Failed! Shutting Down");
                UserOutput.display(e.getLocalizedMessage());
                Logging.log(Severity.ERROR, e);
                System.exit(1);
            }
        }

        //Attempt write
        try {
            //Create receipt file
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-hhmmss"));
            File recieptFile = receiptFolder.resolve(timeStamp + ".txt").toFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(recieptFile));

            //Append each item onto the file
            items.forEach(item -> {
                try {
                    writer.append(item.toString());
                } catch (IOException e) {
                    //If file writer fails we need to shut down
                    UserOutput.display("File Write Failed! Shutting Down");
                    UserOutput.display(e.getLocalizedMessage());
                    Logging.log(Severity.ERROR, e, recieptFile , item);
                    System.exit(1);
                }
            });

            //Flush and close writer
            writer.close();
        } catch (IOException e) {
            //If file writer fails we need to shut down
            UserOutput.display("File Creation Failed! Shutting Down");
            UserOutput.display(e.getLocalizedMessage());
            Logging.log(Severity.ERROR, e, receiptFolder);
            System.exit(1);
        }
    }
}
