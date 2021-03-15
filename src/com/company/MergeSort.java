package com.company;


public class MergeSort {
   private int[] array;

    public MergeSort(int[] array) throws NullPointerException {
        if (array!=null)
            this.array = array;
        else throw new NullPointerException();
    }

    public double sort(int i,int j){

        long startTime = System.nanoTime();
        if(i<j){
            int mid = (j+i)/2;
            sort(i,mid);
            sort(mid+1,j);
            merge(i,mid,j);
        }
        long endTime = System.nanoTime();
        return (endTime-startTime)/1000000000.0;
    }

    public double concurrencySort(int count){
        Thread[] pool = new Thread[count];
        int d = array.length/count;
        int incr = d;
        long startTime = System.nanoTime();
        pool[0] = new Thread(() -> sort(0, d));
        pool[0].start();
        for (int i=1;i<count-1;i++) {
            int finalIncr = incr;
            Thread a = new Thread(() -> sort(finalIncr +1, finalIncr +d));
            incr+=d;
            a.start();
            pool[i] = a;
        }
        pool[count-1] = new Thread(() -> sort(d*(count-1)+1, array.length-1));
        pool[count-1].start();


        for (Thread i:pool) {
            try {
                i.join();
            } catch (Exception e) {
                System.out.println("Interrupt Exception in merge sort");
            }
        }
        for (int i=0;i<count-2;i++) {
            merge(0, d*(i+1), d*(i+2));
        }
        merge(0, d*(count-1), array.length-1);
        long endTime = System.nanoTime();
        return (endTime-startTime)/1000000000.0;

    }

    public void merge(int i,int mid,int j){
       // System.out.println(Arrays.toString(array));

        int n1 = mid - i + 1;
        int n2 = j - mid;
        int[] firstHalf = new int[n1];
        int[] secondHalf = new int[n2];

        System.arraycopy(array, i, firstHalf, 0, n1);
        System.arraycopy(array, mid+1, secondHalf, 0, n2);


        int l=0,k=0,p=i;
        while (l < n1 && k < n2) {
            if (firstHalf[l] <= secondHalf[k]) {
                array[p] = firstHalf[l];
                l++;
            }
            else {
                array[p] = secondHalf[k];
                k++;
            }
            p++;
        }

        while (l < n1) {
            array[p] = firstHalf[l];
            l++;
            p++;
        }


        while (k < n2) {
            array[p] = secondHalf[k];
            k++;
            p++;
        }
    }

}
