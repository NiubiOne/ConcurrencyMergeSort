package com.company;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class TextFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        String extension = "txt";
        String path = f.getAbsolutePath().toLowerCase();
        return path.endsWith(extension) && (path.charAt(path.length()
                - extension.length() - 1)) == '.';
    }

    @Override
    public String getDescription() {
        return "Txt Files";
    }
}
