package com.company;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;


public class UI extends JFrame {
   private int[] inputArray;
   private JTextArea jTextArea;

  private   JFileChooser fileChooser;

    public UI() {

        setName("Window");
        setVisible(true);
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(500, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button1 = new JButton("Load file");
        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton button2 = new JButton("Save in file");
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton button3 = new JButton("Randomise");
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel results = new JPanel();
        results.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel random = new JPanel();
        random.setLayout(new BoxLayout(random,BoxLayout.Y_AXIS));
        JPanel labels = new JPanel();
        JPanel fields = new JPanel();
        labels.setLayout(new FlowLayout());
        fields.setLayout(new FlowLayout());
        JLabel label1 = new JLabel("From");
        JLabel label2 = new JLabel("To");
        JLabel label3 = new JLabel("Size");

        labels.add(label1);
        labels.add(label2);
        labels.add(label3);

        JTextField field1 = new JTextField();
        JTextField field2 = new JTextField();
        JTextField field3 = new JTextField();
        fields.add(field1);
        fields.add(field2);
        fields.add(field3);

        field1.setPreferredSize(new Dimension(50,30));
        field2.setPreferredSize(new Dimension(50,30));
        field3.setPreferredSize(new Dimension(50,30));
        JTextField threadsArea = new JTextField("2");
        threadsArea.setPreferredSize(new Dimension(40,20));


        results.setLayout(new FlowLayout());
        results.setMaximumSize(new Dimension(600, 100));
        JLabel label = new JLabel("threads:  ");

        JButton button4 = new JButton("Sort");
        JButton button5 = new JButton("MultiThredSort");
        results.add(button4);
        results.add(button5);
        results.add(label);
        results.add(threadsArea);


        JLabel time = new JLabel("Calculation time: ");
        time.setPreferredSize(new Dimension(150,50));
        time.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel();
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
        buttons.setPreferredSize(new Dimension(200, 600));
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(Box.createVerticalStrut(10));
        JPanel k = new JPanel();
        k.add(labels);
        k.add(fields);
        buttons.add(k);




        jTextArea = new JTextArea();
        JScrollPane scroll = new JScrollPane(jTextArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(150, 200));
        scroll.setMaximumSize(new Dimension(1200, 800));


        JPanel mainScreen = new JPanel();
        mainScreen.setPreferredSize(new Dimension(700, 600));
        mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.Y_AXIS));
        // mainScreen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mainScreen.add(scroll);
        mainScreen.add(Box.createVerticalStrut(10));
        mainScreen.add(results);
        mainScreen.add(time);
        mainScreen.add(Box.createVerticalStrut(20));

        add(Box.createHorizontalStrut(20), BorderLayout.LINE_START);
        add(mainScreen, BorderLayout.CENTER);
        add(buttons, BorderLayout.LINE_END);


        pack();


        button1.addActionListener(e -> {
            jTextArea.setText("");
            fileChooser = new TxtChooser();
            BufferedReader br;
            int returnVal = fileChooser.showDialog(this,
                    "Open");
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                FileInputStream inputStream = null;
                Scanner sc=null;
                try {
                    LinkedList<Integer> b = new LinkedList<>();
                    inputStream = new FileInputStream(file);
                    sc = new Scanner(inputStream);
                    String line;
                    while (sc.hasNextLine()){
                       String[] a = sc.nextLine().split(" ");
                               for (String i:a){
                                   b.add(Integer.parseInt(i));
                               }
                       }
                    inputArray = b.stream().mapToInt(Integer::intValue).toArray();
                    printArray();

                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                    jTextArea.setText("Error");
                }
                finally {
                    if (inputStream!=null) {
                        try {
                            inputStream.close();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                    if (sc!=null)
                    sc.close();

                }
            }
           // printArray();
        });

        button2.addActionListener(e -> {
            fileChooser = new TxtChooser();
            int returnVal = fileChooser.showDialog(this,
                    "Save as");
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file;
                try {
                    file = fileChooser.getSelectedFile();
                    FileWriter fileWriter = new FileWriter(file);
                    for (int i = 0; i < inputArray.length; i++) {
                        fileWriter.write(inputArray[i] + " ");
                        if (i % 100 == 0 && i!=0) {
                            fileWriter.write("\n");
                        }
                    }
                    fileWriter.close();

                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
        button3.addActionListener(e -> {
            int from =Integer.parseInt(field1.getText());
            int to =Integer.parseInt(field2.getText());
            int size =Integer.parseInt(field3.getText());
            Random r = new Random();
            inputArray = r.ints(size, from, to).toArray();
            jTextArea.setText("");
            printArray();
        });

        button4.addActionListener(e -> {
            MergeSort mergeSort;
            try {
                mergeSort = new MergeSort(inputArray);
                time.setText("time: " + mergeSort.sort(0,inputArray.length-1));
            } catch (Exception exception) {
                jTextArea.setText("Array is empty");
            }
            printArray();


        });
        button5.addActionListener(e -> {
            jTextArea.setText("");
            MergeSort mergeSort;
            int count;
            try {
                mergeSort = new MergeSort(inputArray);
                count = Integer.parseInt(threadsArea.getText());
                if (count<2) throw new ArithmeticException();
                else time.setText("time: " + mergeSort.concurrencySort(count));
                printArray();
            } catch (NullPointerException nullPointerException) {
                jTextArea.append("Array is empty \n");
            }catch (NumberFormatException nfe){
                jTextArea.append("Amount of threads is empty");
            }catch (ArithmeticException ae){
                jTextArea.append("Amount of threads <2");
            }


        });

    }

    private void printArray() {
        jTextArea.setText("");
           if (inputArray!=null) {
               int i=0;
               while (i<500000 && i<inputArray.length){
                   jTextArea.append(inputArray[i] + " ");
                   if (i % 100 == 0 && i!=0) {
                       jTextArea.append("\n");
                   }
                   i++;
               }
           }
    }

}
