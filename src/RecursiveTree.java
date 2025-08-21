import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

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
        Path path = Paths.get(getPath());
        try {
            System.out.println(getDirectory(path, 0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Getters and Setters


    // Custom Methods
    public String getPath() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter path: ");
        String pathStr = sc.nextLine();
        try {
            Path path = Paths.get(pathStr);

            if(!Files.exists(path)) {
                System.out.println("  > E: Path does not exist");
                pathStr = getPath();
            }

        } catch (InvalidPathException e) {
            System.out.println("  > E: Path does not exist");
            pathStr = getPath();
        }
        return pathStr;
    }

    public String getDirectory(Path path, int depth) throws Exception {
        StringBuilder stringBuild = new StringBuilder();
        BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);

        // If directory, list files and drill down into subdirectories
        if(attr.isDirectory()) {
            DirectoryStream<Path> paths = Files.newDirectoryStream(path);
            long fileCount = Files.list(path).count();
            stringBuild.append(getDepthIndent(depth)).append(" >> ").append(path.getFileName());
            stringBuild.append(" (").append(attr.size()).append(" bytes, ");
            if(fileCount == 1) {
                stringBuild.append(fileCount).append(" file)").append("\n");
            } else {
                stringBuild.append(fileCount).append(" files)").append("\n");
            }


            for(Path subdirectory : paths) {
                stringBuild.append(getDirectory(subdirectory, depth + 1));
            }
        } else {
            stringBuild.append(getDepthIndent(depth)).append(" -- ").append(path.getFileName()).append(" (" + attr.size() + " bytes) ").append("\n");
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
