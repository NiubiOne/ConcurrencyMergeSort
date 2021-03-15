package com.company;


import javax.swing.*;
import java.io.File;

public class TxtChooser extends JFileChooser {
    public TxtChooser(){
        setCurrentDirectory(new File(System.getProperty("user.dir")));
        setFileFilter(new TextFilter());
    }

}
