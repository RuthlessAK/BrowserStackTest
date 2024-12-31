package utility;

import java.io.*;
import java.net.*;
import org.apache.commons.io.FileUtils;

public class DownloadUtils {

    public static void downloadImage(String imageURL,int articleNumber) {
        try {
            FileUtils.copyURLToFile(new URL(imageURL), new File("./Images/CoverImage_Article"+articleNumber+".jpg"));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    // Helper method to extract the file name from the URL
    public static String getFileNameFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            String path = url.getPath();
            return path.substring(path.lastIndexOf("/") + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return "default_image.jpg"; // Default file name if URL parsing fails
        }
    }
}
