package ltd.kumo.plutomc.bot.shared.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class FileUtility {

    public static void write(File file, String content) {
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
            outputStream.write(bytes, 0, bytes.length);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private FileUtility() {
    }

}
