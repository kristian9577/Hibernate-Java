package kristian9577.springdataintroexercises.util;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileUtilImpl implements FileUtil {


    @Override
    public String[] fileContext(String path) throws IOException {

        File file = new File(path);
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line;

        List<String> fileInfo = new ArrayList<>();

        while ((line = bf.readLine()) != null) {
            fileInfo.add(line);
        }

        return fileInfo.stream()
                .filter(l -> !l.equals(""))
                .toArray(String[]::new);
    }
}
