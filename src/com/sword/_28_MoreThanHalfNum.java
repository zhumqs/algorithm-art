package com.sword;

import java.util.Arrays;

public class _28_MoreThanHalfNum {
    public int moreThanHalfNum(int [] array) {
        //遍历元素统计个数最多的元素然后跟长度比较
        if(array==null&&array.length==0) return 0;
        //超过半数肯定排序后中年元素就是该元素，然后左右遍历统计相同的长度然后比较
        Arrays.sort(array);
        int count = 1,mid = array.length/2;
        //从左往右统计
        for(int i = mid+1;i<array.length;i++){
            if(array[i] == array[mid])
                count++;
        }
        //从右往左统计
        for(int i = mid-1;i>=0;i--){
            if(array[i] == array[mid])
                count++;
        }
        return count>mid?array[mid]:0;
    }

    //O(N)的方法不用排序

    /**
     * 在遍历数组时保存两个值：一是数组中一个数字，一是次数。遍历下一个数字时，若它与之前保存的数字相同，
     * 则次数加1，否则次数减1；若次数为0，则保存下一个数字，并将次数置为1。遍历结束后，所保存的数字即为所求。
     * 然后再判断它是否符合条件即可。
     * @param arr
     * @return
     */
    public int moreThanHalfNum2(int arr[])
    {
        if(arr == null) return 0;

        // 遍历每个元素，并记录次数；若与前一个元素相同，则次数加1，否则次数减1
        int result = arr[0];
        int times = 1; // 次数

        for(int i=1;i<arr.length;++i)
        {
            if(times == 0)
            {
                // 更新result的值为当前元素，并置次数为1
                result = arr[i];
                times = 1;
            }
            else if(arr[i] == result)
            {
                ++times; // 相同则加1
            }
            else
            {
                --times; // 不同则减1
            }
        }

        // 判断result是否符合条件，即出现次数大于数组长度的一半
        times = 0;
        for(int i=0;i<arr.length;++i)
        {
            if(arr[i] == result) ++times;
        }

        return (times > arr.length/2) ? result : 0;
    }
}
