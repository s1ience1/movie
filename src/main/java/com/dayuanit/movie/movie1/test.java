package com.dayuanit.movie.movie1;


public class test {
    // 冒泡排序
    public static void main(String[] args) {
        int[] nums = {5,2,3,6};
//        for (int i = 0; i<nums.length-1 ;i++) {
//            for (int j =0 ; j<nums.length-1-i;j++) {
//                if (nums[j] > nums[j+1]) {
//                    int temp = nums[j];
//                    nums[j] = nums[j+1];
//                    nums[j+1] = temp;
//                }
//            }
//        }


        //选择排序 2 5 9 3
        //外层循环  指到哪里就认为 是最小的
        for (int i = 0 ; i < nums.length-1;i++) {
            int min = i;
            for (int j = i+1;j < nums.length; j++) {
                if (nums[min] > nums[j]) {
                    min = j;
                }
            }
            if (min != i) {
                //最小值
                int temp = nums[min];
                nums[min] = nums[i];
                nums[i] = temp;
            }
        }




        // 插入排序 从已有的后面开始比较

        for (int i= 0; i < nums.length-1;i++) {
            for (int j = i + 1; j > 0; j--){
                if (nums[j-1]>nums[j]) {
                    //
                    int tmp = nums[j-1];
                    nums[j-1]=nums[j];
                    nums[j]=tmp;
                }
            }
        }

        for (int i : nums) {
            System.out.println(i);
        }


    }


}
