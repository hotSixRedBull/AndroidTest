package com.db.alan.dbapp;

public class FileSplit0 {
    public static String questionNum[][] = new String[100][10];
    public static String questionNum2[][] = new String[1000][10];

    public FileSplit0(String str) {
        String tmp[] = str.split("\n");
        String s;
        char ch;
        for (int i = 0; i < tmp.length; i++) {
            s = tmp[i];
            String tmp2[] = s.split(":");
            for (int j = 0; j < 10; j++) {
                tmp2[j]=tmp2[j].trim();
                questionNum[i][j]=tmp2[j];
            }
        }
        makeHundred();
    }

    public void makeHundred() {
        int selectedQuestion;
        double randomNum;

        for(int i=0;i<1000;i++) {
            questionNum2[i][9] = "";
        }

        for(int i=0;i<100;i++) {
            do {
                randomNum = Math.random();
                selectedQuestion = (int) ((randomNum*(1000)));
            }while(questionNum2[selectedQuestion][9] == "yes");

            for(int j=0;j<10;j++) {
                FileSplit0.questionNum[i][j] = FileSplit0.questionNum2[selectedQuestion][j];
                FileSplit0.questionNum[i][0] = Integer.toString(i+1);
            }
            questionNum2[selectedQuestion][9] = "yes";
        }
    }
}
