import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Jacob Whitney
 * Software Development II
 * August 20, 2025,
 * RecursiveTree.java
 * Description: contains attributes and methods for
 * creating and updating Recursive Tree objects.
 */

public class RecursiveTree {
    // Attributes

    // Constructor
    public RecursiveTree() throws Exception {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        try {
            System.out.println(getDirectory(currentPath, 0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Getters and Setters


    // Custom Methods
    public String getDirectory(Path path, int depth) throws Exception {
        StringBuilder stringBuild = new StringBuilder();
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);

        // If directory, list files and drill down into subdirectories
        if(attr.isDirectory()) {
            DirectoryStream<Path> paths = Files.newDirectoryStream(path);
            stringBuild.append(getDepthIndent(depth)).append(" -- ").append(path.getFileName()).append("\n");

            for(Path subdirectory : paths) {
                stringBuild.append(getDirectory(subdirectory, depth + 1));
            }
        } else {
            stringBuild.append(getDepthIndent(depth)).append(" -- ").append(path.getFileName()).append("\n");
        }
        return stringBuild.toString();
    }

    public String getDepthIndent(int depth) {
        StringBuilder stringBuild = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            stringBuild.append("    ");
        }
        return stringBuild.toString();
    }
}
