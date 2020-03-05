## 时间复杂度

常数时间的操作： 一个操作如果和数据量没有关系， 每次都是固定时间内完成的操作， 叫做常数操作。
时间复杂度为一个算法流程中， 常数操作数量的指标。 常用O（读作big O） 来表示。 具体来说， 在常数操作数量的表达式中，只要高阶项， 不要低阶项， 也不要高阶项的系数， 剩下的部分如果记为f(N)， 那么时间复杂度为O(f(N))。评价一个算法流程的好坏， 先看时间复杂度的指标， 然后再分析不同数据样本下的实际运行时间， 也就是常数项时间。

> 一个有序数组A，另一个无序数组B，打印B中所有不在A中的数，A的数组长度为N，B数组长度为M
> 算法流程1： 对于数组B中的每一个数， 都在A中通过遍历的方式找一下；
> 算法流程2： 对于数组B中的每一个数， 都在A中通过二分的方式找一下；
> 算法流程3： 先把数组B排序， 然后用类似外排的方式打印所有在A中出现的数；

```java
public class _10_GetAllNotIncluded {

	public static List<Integer> GetAllNotIncluded(int[] A, int[] B) {
		List<Integer> res = new ArrayList<>();
		for (int i = 0; i < B.length; i++) {
			int l = 0;
			int r = A.length - 1;
			boolean contains = false;
			while (l <= r) {
				int mid = l + ((r - l) >> 1);
				if (A[mid] == B[i]) {
					contains = true;
					break;
				}
				if (A[mid] > B[i]) {
					r = mid - 1;
				} else {
					l = mid + 1;
				}
			}
			if (!contains) {
				res.add(B[i]);
			}
		}
		return res;
	}

	// for test
	public static List<Integer> comparator(int[] A, int[] B) {
		List<Integer> res = new ArrayList<>();
		for (int i = 0; i < B.length; i++) {
			boolean contains = false;
			for (int j = 0; j < A.length; j++) {
				if (A[j] == B[i]) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				res.add(B[i]);
			}
		}
		return res;
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static boolean isEqual(List<Integer> l1, List<Integer> l2) {
		if (l1.size() != l2.size()) {
			return false;
		}
		HashMap<Integer, Integer> map = new HashMap<>();
		for (Integer i : l1) {
			if (!map.containsKey(i)) {
				map.put(i, 0);
			}
			map.put(i, map.get(i) + 1);
		}
		for (Integer i : l2) {
			if (!map.containsKey(i)) {
				return false;
			}
			map.put(i, map.get(i) - 1);
			if (map.get(i) < 0) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void main(String[] args) {
		int testTime = 300000;
		int sortedArrayMaxSize = 300;
		int unsortedArrayMaxSize = 10;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] A = generateRandomArray(sortedArrayMaxSize, maxValue);
			int[] B = generateRandomArray(unsortedArrayMaxSize, maxValue);
			Arrays.sort(A);
			List<Integer> res1 = GetAllNotIncluded(A, B);
			List<Integer> res2 = comparator(A, B);
			if (!isEqual(res1, res2)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

	}

}
```



## 排序算法

### 冒泡排序
时间复杂度为O(N^2) 额外空间复杂度为O(1)

```java
	public static void bubbleSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		for (int e = arr.length - 1; e > 0; e--) {
			for (int i = 0; i < e; i++) {
				if (arr[i] > arr[i + 1]) {
					swap(arr, i, i + 1);
				}
			}
		}
	}

	public static void swap(int[] arr, int i, int j) {
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
	}
```

### 选择排序
时间复杂度为O(N^2) 额外空间复杂度为O(1)

```java
	public static void selectionSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		for (int i = 0; i < arr.length - 1; i++) {
			int minIndex = i;
			for (int j = i + 1; j < arr.length; j++) {
				minIndex = arr[j] < arr[minIndex] ? j : minIndex;
			}
			swap(arr, i, minIndex);
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
```

### 插入排序
时间复杂度为O(N^2) 额外空间复杂度为O(1),有序情况下只需遍历一边为O(N)

```java
	public static void insertionSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		for (int i = 1; i < arr.length; i++) {
			for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
				swap(arr, j, j + 1);
			}
		}
	}

	public static void swap(int[] arr, int i, int j) {
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
	}
```

### 归并排序
递归的时间复杂度计算
master公式的使用
T(N) = a*T(N/b) + O(N^d)
1) log(b,a) > d -> 复杂度为O(N^log(b,a))
2) log(b,a) = d -> 复杂度为O(N^d * logN)
3) log(b,a) < d -> 复杂度为O(N^d)
归并排序的时间复杂度为O(N*logN) 额外空间复杂度为O(N)(需要使用数组记录排序结果然后复制回去)

```
	public static void mergeSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		mergeSort(arr, 0, arr.length - 1);
	}

	public static void mergeSort(int[] arr, int l, int r) {
		if (l == r) {
			return;
		}
		int mid = l + ((r - l) >> 1);
		mergeSort(arr, l, mid);
		mergeSort(arr, mid + 1, r);
		merge(arr, l, mid, r);
	}

	public static void merge(int[] arr, int l, int m, int r) {
		int[] help = new int[r - l + 1];
		int i = 0;
		int p1 = l;
		int p2 = m + 1;
		while (p1 <= m && p2 <= r) {
			help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
		}
		while (p1 <= m) {
			help[i++] = arr[p1++];
		}
		while (p2 <= r) {
			help[i++] = arr[p2++];
		}
		for (i = 0; i < help.length; i++) {
			arr[l + i] = help[i];
		}
	}
```


  > 小和问题和逆序对问题
  >
  > 在一个数组中， 每一个数左边比当前数小的数累加起来， 叫做这个数组的小和。 求一个数组的小和。
  >
  > ```java
  > 	public static int smallSum(int[] arr) {
  > 		if (arr == null || arr.length < 2) {
  > 			return 0;
  > 		}
  > 		return mergeSort(arr, 0, arr.length - 1);
  > 	}
  > 
  > 	public static int mergeSort(int[] arr, int l, int r) {
  > 		if (l == r) {
  > 			return 0;
  > 		}
  > 		int mid = l + ((r - l) >> 1);
  > 		return mergeSort(arr, l, mid) + mergeSort(arr, mid + 1, r) + merge(arr, l, mid, r);
  > 	}
  > 
  > 	public static int merge(int[] arr, int l, int m, int r) {
  > 		int[] help = new int[r - l + 1];
  > 		int i = 0;
  > 		int p1 = l;
  > 		int p2 = m + 1;
  > 		int res = 0;
  > 		while (p1 <= m && p2 <= r) {
  > 			res += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;
  > 			help[i++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
  > 		}
  > 		while (p1 <= m) {
  > 			help[i++] = arr[p1++];
  > 		}
  > 		while (p2 <= r) {
  > 			help[i++] = arr[p2++];
  > 		}
  > 		for (i = 0; i < help.length; i++) {
  > 			arr[l + i] = help[i];
  > 		}
  > 		return res;
  > 	}
  > ```
  >
  >
  >
  > 在一个数组中， 左边的数如果比右边的数大， 则这两个数构成一个逆序对， 请打印所有逆序对。

### 快速排序
partition

  > 给定一个数组arr， 和一个数num， 请把小于等于num的数放在数组的左边， 大于num的数放在数组的右边。要求额外空间复杂度O(1)， 时间复杂度O(N)

  > (荷兰国旗问题)给定一个数组arr， 和一个数num， 请把小于num的数放在数组的左边， 等于num的数放在数组的中间， 大于num的数放在数组的右边。要求额外空间复杂度O(1)， 时间复杂度O(N)
  >
  > ```java
  > 	public static int[] partition(int[] arr, int l, int r, int p) {
  > 		int less = l - 1;
  > 		int more = r + 1;
  > 		while (l < more) {
  > 			if (arr[l] < p) {
  > 				swap(arr, ++less, l++);
  > 			} else if (arr[l] > p) {
  > 				swap(arr, --more, l);
  > 			} else {
  > 				l++;
  > 			}
  > 		}
  > 		return new int[] { less + 1, more - 1 };
  > 	}
  > 
  > 	// for test
  > 	public static void swap(int[] arr, int i, int j) {
  > 		int tmp = arr[i];
  > 		arr[i] = arr[j];
  > 		arr[j] = tmp;
  > 	}
  > ```

  经典排序和随机快排，用荷兰国旗问题来改进快排问题
  时间复杂度为O(N*logN) 额外空间复杂度为O(logN)

```java
	public static void quickSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		quickSort(arr, 0, arr.length - 1);
	}

	public static void quickSort(int[] arr, int l, int r) {
		if (l < r) {
			//让数组中随机一个元素跟最后一个元素交换
			swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
			int[] p = partition(arr, l, r);
			quickSort(arr, l, p[0] - 1);
			quickSort(arr, p[1] + 1, r);
		}
	}

	public static int[] partition(int[] arr, int l, int r) {
		int less = l - 1;
		//理解这里more,基准数是最后一个元素
		int more = r;
		while (l < more) {
			if (arr[l] < arr[r]) {
				swap(arr, ++less, l++);
			} else if (arr[l] > arr[r]) {
				swap(arr, --more, l);
			} else {
				l++;
			}
		}
		swap(arr, more, r);
		return new int[] { less + 1, more };
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
```

### 堆排序
时间复杂度O(N*logN) 额外空间复杂度为O(1)
堆结构的heapInsert与heapify,heapInset用来构建初始队，heapify用来每次头结点出堆后的复原操作
堆结构的增大和减少
如果只是建立堆的过程， 时间复杂度为O(N)
优先级队列结构就是堆结构

```java
public staic void heapInsert(int arr[], int index) {
	//如果当前节点位置比父节点大就和父节点交换并且当前节点向上移动
	//如果不比父节点大直接就跳过进行下一个位置了
	while(arr[index] > arr[(index-1)/2]){
		swap(arr,index,(index-1)/2);
		index = (index-1)/2;
	}
}

publid static void heapify(int arr[],int index ,int heapSize) {
	int left = index*2 + 1;
	while(left < heapSize) {
		//左右子节点的较大值
		int bigIndex = left + 1<heapSize && arr[left + 1] > arr[left] ? left+1:left;
		//当前的节点是否比最大的还大
		bigIndex = arr[index] > arr[bigIndex]? index : bigIndex;
		//如果当前节点比两个子节点都大则break
		if(index == bigIndex) {
			break;
		}
		swap(arr,index,bigIndex);
		//index还是必须要变成bigIndex因为下一轮要用
		index = bigIndex;
		left = index*2 + 1;
	}
}
```

### 排序算法的稳定性

原序列包含相同的值，稳定性指的是排序后相同值原始的相对位置不变

O(n^2)的算法
冒泡： 可以实现为稳定的
插入： 可以实现为稳定的
选择排序： 无法实现为稳定的

O(nlogn)算法
归并： 可以实现为稳定的
快排： 无法实现稳定性，因为partition过程无法实现稳定性(证明论文级别的难度)
堆排： 无法实现稳定性

> 无法直接用partition实现问题(保证原始次序的相对位置不变)：
> 奇数放在数组左边，偶数放在数组右边，还要求原始的相对次序不变

## 非基于排序的排序算法

### 计数排序

### 基数排序

```java
	// only for no-negative value
	public static void radixSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		radixSort(arr, 0, arr.length - 1, maxbits(arr));
	}

	public static int maxbits(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(max, arr[i]);
		}
		int res = 0;
		while (max != 0) {
			res++;
			max /= 10;
		}
		return res;
	}

	public static void radixSort(int[] arr, int begin, int end, int digit) {
		final int radix = 10;
		int i = 0, j = 0;
		int[] count = new int[radix];
		int[] bucket = new int[end - begin + 1];
		for (int d = 1; d <= digit; d++) {
			for (i = 0; i < radix; i++) {
				count[i] = 0;
			}
			for (i = begin; i <= end; i++) {
				j = getDigit(arr[i], d);
				count[j]++;
			}
			for (i = 1; i < radix; i++) {
				count[i] = count[i] + count[i - 1];
			}
			for (i = end; i >= begin; i--) {
				j = getDigit(arr[i], d);
				bucket[count[j] - 1] = arr[i];
				count[j]--;
			}
			for (i = begin, j = 0; i <= end; i++, j++) {
				arr[i] = bucket[j];
			}
		}
	}

	public static int getDigit(int x, int d) {
		return ((x / ((int) Math.pow(10, d - 1))) % 10);
	}
```



### 桶排序

1. 非基于比较的排序， 与被排序的样本的实际数据状况很有关系， 所以实际中并不经常使用

2. 时间复杂度O(N)， 额外空间复杂度O(N)

3. 稳定的排序

   ```java
   	// only for 0~200 value
   	public static void bucketSort(int[] arr) {
   		if (arr == null || arr.length < 2) {
   			return;
   		}
   		int max = Integer.MIN_VALUE;
   		for (int i = 0; i < arr.length; i++) {
   			max = Math.max(max, arr[i]);
   		}
   		int[] bucket = new int[max + 1];
   		for (int i = 0; i < arr.length; i++) {
   			bucket[arr[i]]++;
   		}
   		int i = 0;
   		for (int j = 0; j < bucket.length; j++) {
   			while (bucket[j]-- > 0) {
   				arr[i++] = j;
   			}
   		}
   	}
   ```


> ## 给定一个数组，求如果排序之后，相邻两数的最大差值，要求时间复杂度O(N)，且要求不能用非基于比较的排序。

```java
	public static int maxGap(int[] nums) {
		if (nums == null || nums.length < 2) {
			return 0;
		}
		int len = nums.length;
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < len; i++) {
			min = Math.min(min, nums[i]);
			max = Math.max(max, nums[i]);
		}
		if (min == max) {
			return 0;
		}
		boolean[] hasNum = new boolean[len + 1];
		int[] maxs = new int[len + 1];
		int[] mins = new int[len + 1];
		int bid = 0;
		for (int i = 0; i < len; i++) {
			//只记录最大值和最小值
			bid = bucket(nums[i], len, min, max);
			mins[bid] = hasNum[bid] ? Math.min(mins[bid], nums[i]) : nums[i];
			maxs[bid] = hasNum[bid] ? Math.max(maxs[bid], nums[i]) : nums[i];
			hasNum[bid] = true;
		}
		int res = 0;
		int lastMax = maxs[0];
		int i = 1;
		for (; i <= len; i++) {
			if (hasNum[i]) {
				res = Math.max(res, mins[i] - lastMax);
				lastMax = maxs[i];
			}
		}
		return res;
	}

	public static int bucket(long num, long len, long min, long max) {
		return (int) ((num - min) * len / (max - min));
	}
```



## 队列和栈

### 用数组结构实现大小固定的队列和栈

```java
	public static class ArrayStack {
		private Integer[] arr;
		private Integer size;

		public ArrayStack(int initSize) {
			if (initSize < 0) {
				throw new IllegalArgumentException("The init size is less than 0");
			}
			arr = new Integer[initSize];
			size = 0;
		}

		public Integer peek() {
			if (size == 0) {
				return null;
			}
			return arr[size - 1];
		}

		public void push(int obj) {
			if (size == arr.length) {
				throw new ArrayIndexOutOfBoundsException("The queue is full");
			}
			arr[size++] = obj;
		}

		public Integer pop() {
			if (size == 0) {
				throw new ArrayIndexOutOfBoundsException("The queue is empty");
			}
			return arr[--size];
		}
	}

	public static class ArrayQueue {
		private Integer[] arr;
		private Integer size;
		private Integer first;
		private Integer last;

		public ArrayQueue(int initSize) {
			if (initSize < 0) {
				throw new IllegalArgumentException("The init size is less than 0");
			}
			arr = new Integer[initSize];
			size = 0;
			first = 0;
			last = 0;
		}

		public Integer peek() {
			if (size == 0) {
				return null;
			}
			return arr[first];
		}

		public void push(int obj) {
			if (size == arr.length) {
				throw new ArrayIndexOutOfBoundsException("The queue is full");
			}
			size++;
			arr[last] = obj;
			last = last == arr.length - 1 ? 0 : last + 1;
		}

		public Integer poll() {
			if (size == 0) {
				throw new ArrayIndexOutOfBoundsException("The queue is empty");
			}
			size--;
			int tmp = first;
			first = first == arr.length - 1 ? 0 : first + 1;
			return arr[tmp];
		}
	}
```

### 栈的压入弹出队列

```java
public class Solution {
    public boolean IsPopOrder(int [] pushA,int [] popA) {
        //长度不一致直接返回false
        if(pushA.length != popA.length) return false;
        //借助辅助栈，将压入序列压入栈中
        Stack<Integer> stack = new Stack<>();
        //记录弹出序列的位置，从左往右
        int popIndex = 0;
        //将压入序列压入栈中的过程中判断压入元素是否跟弹出序列当前位置相等
        for(int i = 0;i<pushA.length;i++){
            //当前元素压入栈
            stack.push(pushA[i]);
            //栈不为空且辅助栈顶元素跟弹出序列当前位置相等
            while(!stack.isEmpty()&&stack.peek()==popA[popIndex]){
                //弹出当前元素
                stack.pop();
                //接着比较弹出序列下一个位置和新的栈顶元素是否相等
                popIndex++;
            }
        }
        //最后看辅助栈是否为空
        return stack.isEmpty();
    }
}
```



### 实现能返回最小元素的栈

```java
public class Solution {
    //正常栈用来记录压入和弹出的值
    Stack<Integer> stack1 = new Stack<>();
    //借助辅助栈每次栈顶都是保存当前栈最小元素，并且弹出时辅助栈也要弹出
    Stack<Integer> stack2 = new Stack<>();
    public void push(int node) {
        stack1.push(node);
        //如果当前节点比辅助栈的栈顶元素小则压入当前元素，否则接着压入辅助栈栈顶的元素
        if(stack2.isEmpty()){
             stack2.push(node); 
        }else{
             stack2.push(node<stack2.peek()?node:stack2.peek());
        }
    }
    
    public void pop() {
        stack1.pop();
        stack2.pop();
    }
    
    public int top() {
        return stack1.peek();
    }
    
    public int min() {
        return stack2.peek();
    }
}
```



### 用队列实现栈，如果是用队列实现图的深度优先遍历

(图的深度优先遍历是需要用栈实现的)，用两个队列实现一个栈然后用栈去实现深度优先遍历

```java
public static class TwoQueuesStack {
		private Queue<Integer> queue;
		private Queue<Integer> help;

		public TwoQueuesStack() {
			queue = new LinkedList<Integer>();
			help = new LinkedList<Integer>();
		}

		public void push(int pushInt) {
			queue.add(pushInt);
		}

		public int peek() {
			if (queue.isEmpty()) {
				throw new RuntimeException("Stack is empty!");
			}
			while (queue.size() != 1) {
				help.add(queue.poll());
			}
			int res = queue.poll();
			help.add(res);
			swap();
			return res;
		}

		public int pop() {
			if (queue.isEmpty()) {
				throw new RuntimeException("Stack is empty!");
			}
			while (queue.size() > 1) {
				help.add(queue.poll());
			}
			int res = queue.poll();
			swap();
			return res;
		}

		private void swap() {
			Queue<Integer> tmp = help;
			help = queue;
			queue = tmp;
		}
```





### 用栈实现队列

```java
public static class TwoStacksQueue {
		private Stack<Integer> stackPush;
		private Stack<Integer> stackPop;

		public TwoStacksQueue() {
			stackPush = new Stack<Integer>();
			stackPop = new Stack<Integer>();
		}

		public void push(int pushInt) {
			stackPush.push(pushInt);
		}

		public int poll() {
			if (stackPop.empty() && stackPush.empty()) {
				throw new RuntimeException("Queue is empty!");
			} else if (stackPop.empty()) {
				while (!stackPush.empty()) {
					stackPop.push(stackPush.pop());
				}
			}
			return stackPop.pop();
		}

		public int peek() {
			if (stackPop.empty() && stackPush.empty()) {
				throw new RuntimeException("Queue is empty!");
			} else if (stackPop.empty()) {
				while (!stackPush.empty()) {
					stackPop.push(stackPush.pop());
				}
			}
			return stackPop.peek();
		}
	}
```



### 狗猫队列的结构

要求如下： 用户可以调用add方法将cat类或dog类的实例放入队列中； 用户可以调用pollAll方法， 将队列中所有的实例按照进队列的先后顺序依次弹出； 用户可以调用pollDog方法， 将队列中dog类的实例按照进队列的先后顺序依次弹出； 用户可以调用pollCat方法， 将队列中cat类的实例按照进队列的先后顺序依次弹出； 用户可以调用isEmpty方法， 检查队列中是否还有dog或cat的实例； 用户可以调用isDogEmpty方法， 检查队列中是否有dog类的实例； 用户可以调用isCatEmpty方法， 检查队列中是否有cat类的实例

```java
	public static class Pet {
		private String type;

		public Pet(String type) {
			this.type = type;
		}

		public String getPetType() {
			return this.type;
		}
	}

	public static class Dog extends Pet {
		public Dog() {
			super("dog");
		}
	}

	public static class Cat extends Pet {
		public Cat() {
			super("cat");
		}
	}

	public static class PetEnterQueue {
		private Pet pet;
		private long count;

		public PetEnterQueue(Pet pet, long count) {
			this.pet = pet;
			this.count = count;
		}

		public Pet getPet() {
			return this.pet;
		}

		public long getCount() {
			return this.count;
		}

		public String getEnterPetType() {
			return this.pet.getPetType();
		}
	}

	public static class DogCatQueue {
		private Queue<PetEnterQueue> dogQ;
		private Queue<PetEnterQueue> catQ;
		private long count;

		public DogCatQueue() {
			this.dogQ = new LinkedList<PetEnterQueue>();
			this.catQ = new LinkedList<PetEnterQueue>();
			this.count = 0;
		}

		public void add(Pet pet) {
			if (pet.getPetType().equals("dog")) {
				this.dogQ.add(new PetEnterQueue(pet, this.count++));
			} else if (pet.getPetType().equals("cat")) {
				this.catQ.add(new PetEnterQueue(pet, this.count++));
			} else {
				throw new RuntimeException("err, not dog or cat");
			}
		}

		public Pet pollAll() {
			if (!this.dogQ.isEmpty() && !this.catQ.isEmpty()) {
				if (this.dogQ.peek().getCount() < this.catQ.peek().getCount()) {
					return this.dogQ.poll().getPet();
				} else {
					return this.catQ.poll().getPet();
				}
			} else if (!this.dogQ.isEmpty()) {
				return this.dogQ.poll().getPet();
			} else if (!this.catQ.isEmpty()) {
				return this.catQ.poll().getPet();
			} else {
				throw new RuntimeException("err, queue is empty!");
			}
		}

		public Dog pollDog() {
			if (!this.isDogQueueEmpty()) {
				return (Dog) this.dogQ.poll().getPet();
			} else {
				throw new RuntimeException("Dog queue is empty!");
			}
		}

		public Cat pollCat() {
			if (!this.isCatQueueEmpty()) {
				return (Cat) this.catQ.poll().getPet();
			} else
				throw new RuntimeException("Cat queue is empty!");
		}

		public boolean isEmpty() {
			return this.dogQ.isEmpty() && this.catQ.isEmpty();
		}

		public boolean isDogQueueEmpty() {
			return this.dogQ.isEmpty();
		}

		public boolean isCatQueueEmpty() {
			return this.catQ.isEmpty();
		}

	}
```



### 旋转打印矩阵

```java
	public static void spiralOrderPrint(int[][] matrix) {
		int tR = 0;
		int tC = 0;
		int dR = matrix.length - 1;
		int dC = matrix[0].length - 1;
		while (tR <= dR && tC <= dC) {
			printEdge(matrix, tR++, tC++, dR--, dC--);
		}
	}

	public static void printEdge(int[][] m, int tR, int tC, int dR, int dC) {
		if (tR == dR) {
			for (int i = tC; i <= dC; i++) {
				System.out.print(m[tR][i] + " ");
			}
		} else if (tC == dC) {
			for (int i = tR; i <= dR; i++) {
				System.out.print(m[i][tC] + " ");
			}
		} else {
			int curC = tC;
			int curR = tR;
			while (curC != dC) {
				System.out.print(m[tR][curC] + " ");
				curC++;
			}
			while (curR != dR) {
				System.out.print(m[curR][dC] + " ");
				curR++;
			}
			while (curC != tC) {
				System.out.print(m[dR][curC] + " ");
				curC--;
			}
			while (curR != tR) {
				System.out.print(m[curR][tC] + " ");
				curR--;
			}
		}
	}

```



### 旋转正方形矩阵

```java
	public static void rotate(int[][] matrix) {
		int tR = 0;
		int tC = 0;
		int dR = matrix.length - 1;
		int dC = matrix[0].length - 1;
		while (tR < dR) {
			rotateEdge(matrix, tR++, tC++, dR--, dC--);
		}
	}

	public static void rotateEdge(int[][] m, int tR, int tC, int dR, int dC) {
		int times = dC - tC; 
		int tmp = 0;
		for (int i = 0; i != times; i++) {
			tmp = m[tR][tC + i];
			m[tR][tC + i] = m[dR - i][tC];
			m[dR - i][tC] = m[dR][dC - i];
			m[dR][dC - i] = m[tR + i][dC];
			m[tR + i][dC] = tmp;
		}
	}

	public static void printMatrix(int[][] matrix) {
		for (int i = 0; i != matrix.length; i++) {
			for (int j = 0; j != matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

```



### 之字形打印矩阵

```java
	public static void printMatrixZigZag(int[][] matrix) {
		int tR = 0;
		int tC = 0;
		int dR = 0;
		int dC = 0;
		int endR = matrix.length - 1;
		int endC = matrix[0].length - 1;
		boolean fromUp = false;
		while (tR != endR + 1) {
			printLevel(matrix, tR, tC, dR, dC, fromUp);
			tR = tC == endC ? tR + 1 : tR;
			tC = tC == endC ? tC : tC + 1;
			dC = dR == endR ? dC + 1 : dC;
			dR = dR == endR ? dR : dR + 1;
			fromUp = !fromUp;
		}
		System.out.println();
	}

	public static void printLevel(int[][] m, int tR, int tC, int dR, int dC,
			boolean f) {
		if (f) {
			while (tR != dR + 1) {
				System.out.print(m[tR++][tC--] + " ");
			}
		} else {
			while (dR != tR - 1) {
				System.out.print(m[dR--][dC++] + " ");
			}
		}
	}
```



### 在行列都有序的矩阵中找数

```java
	public static boolean isContains(int[][] matrix, int K) {
		int row = 0;
		int col = matrix[0].length - 1;
		while (row < matrix.length && col > -1) {
			if (matrix[row][col] == K) {
				return true;
			} else if (matrix[row][col] > K) {
				col--;
			} else {
				row++;
			}
		}
		return false;
	}
```



## 链表

### 反转单链表和双链表

```java
	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}
	//就地逆置法
	public static Node reverseList(Node head) {
		Node pre = null;
		Node next = null;
		while (head != null) {
			next = head.next;
			head.next = pre;
			pre = head;
			head = next;
		}
		return pre;
	}
        //递归版reverse
    public ListNode reverse(ListNode head) {
        if(head == null || head.next == null) {
            //这时候返回的就是newHead
            return head;
        }
        //先递归处理后面的节点
        ListNode newHead = reverse(head.next);
        //处理当前节点
        head.next.next = head;
        head.next = null;
        return newHead;
    }

	public static class DoubleNode {
		public int value;
		public DoubleNode last;
		public DoubleNode next;

		public DoubleNode(int data) {
			this.value = data;
		}
	}

	public static DoubleNode reverseList(DoubleNode head) {
		DoubleNode pre = null;
		DoubleNode next = null;
		while (head != null) {
			next = head.next;
			head.next = pre;
			head.last = next;
			pre = head;
			head = next;
		}
		return pre;
	}
```



### 反转部分单链表

```java
	public ListNode reverseBetween(ListNode head, int m, int n) {
		if (head == null) return null;
		if (head.next == null) return head;
		int i = 1;
		ListNode reversedNewHead = null;// 反转部分链表反转后的头结点
		ListNode reversedTail = null;// 反转部分链表反转后的尾结点
		ListNode oldHead = head;// 原链表的头结点
		ListNode reversePreNode = null;// 反转部分链表反转前其头结点的前一个结点
		ListNode reverseNextNode = null;
		while (head != null) {
			if (i > n) {
				break;
			}
			if (i == m - 1) {
				reversePreNode = head;
			}
			if (i >= m && i <= n) {
				if (i == m) {
					reversedTail = head;
				}
				reverseNextNode = head.next;
				head.next = reversedNewHead;
				reversedNewHead = head;
				head = reverseNextNode;
			} else {
				head = head.next;
			}
			i++;
		}
		reversedTail.next = reverseNextNode;
		if (reversePreNode != null) {
			reversePreNode.next = reversedNewHead;
			return oldHead;
		} else {
			return reversedNewHead;
		}
	}
```



### 旋转部分单链表

方法一 双指针，快指针先走k步，然后两个指针一起走，当快指针走到末尾时，慢指针的下一个位置是新的顺序的头结点，这样就可以旋转链表了。
方法二 先遍历整个链表获得链表长度n，然后此时把链表头和尾链接起来，在往后走n – k % n个节点就到达新链表的头结点前一个点，这时断开链表即可。 

```java
	public class Solution { {
		public ListNode rotateRight(ListNode head, int k) {
			if (!head) return null;
			int n = 1;
			ListNode cur = head;
			while (cur.next) {
				++n;
				cur = cur.next;
			}
			cur.next = head;
			int m = n - k % n;
			for (int i = 0; i < m; ++i) {
				cur = cur.next;
			}
			ListNode newhead = cur.next;
			cur.next = NULL;
			return newhead;
		}
	};
```



### 打印两个有序链表的公共部分

```java
public static class Node {
		public int value;
		public Node next;
		public Node(int data) {
			this.value = data;
		}
	}
	public static void printCommonPart(Node head1, Node head2) {
		System.out.print("Common Part: ");
		while (head1 != null && head2 != null) {
			if (head1.value < head2.value) {
				head1 = head1.next;
			} else if (head1.value > head2.value) {
				head2 = head2.next;
			} else {
				System.out.print(head1.value + " ");
				head1 = head1.next;
				head2 = head2.next;
			}
		}
		System.out.println();
	}

	public static void printLinkedList(Node node) {
		System.out.print("Linked List: ");
		while (node != null) {
			System.out.print(node.value + " ");
			node = node.next;
		}
		System.out.println();
	}
```



### 判断一个链表是否为回文结构(利用栈结构或者用双指针)

```java
	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	// need n extra space
	public static boolean isPalindrome1(Node head) {
		Stack<Node> stack = new Stack<Node>();
		Node cur = head;
		while (cur != null) {
			stack.push(cur);
			cur = cur.next;
		}
		while (head != null) {
			if (head.value != stack.pop().value) {
				return false;
			}
			head = head.next;
		}
		return true;
	}

	// need n/2 extra space
	public static boolean isPalindrome2(Node head) {
		if (head == null || head.next == null) {
			return true;
		}
		Node right = head.next;
		Node cur = head;
		while (cur.next != null && cur.next.next != null) {
			right = right.next;
			cur = cur.next.next;
		}
		Stack<Node> stack = new Stack<Node>();
		while (right != null) {
			stack.push(right);
			right = right.next;
		}
		while (!stack.isEmpty()) {
			if (head.value != stack.pop().value) {
				return false;
			}
			head = head.next;
		}
		return true;
	}

	// need O(1) extra space
	public static boolean isPalindrome3(Node head) {
		if (head == null || head.next == null) {
			return true;
		}
		Node n1 = head;
		Node n2 = head;
		while (n2.next != null && n2.next.next != null) { // find mid node
			n1 = n1.next; // n1 -> mid
			n2 = n2.next.next; // n2 -> end
		}
		n2 = n1.next; // n2 -> right part first node
		n1.next = null; // mid.next -> null
		Node n3 = null;
		while (n2 != null) { // right part convert
			n3 = n2.next; // n3 -> save next node
			n2.next = n1; // next of right node convert
			n1 = n2; // n1 move
			n2 = n3; // n2 move
		}
		n3 = n1; // n3 -> save last node
		n2 = head;// n2 -> left first node
		boolean res = true;
		while (n1 != null && n2 != null) { // check palindrome
			if (n1.value != n2.value) {
				res = false;
				break;
			}
			n1 = n1.next; // left to mid
			n2 = n2.next; // right to mid
		}
		n1 = n3.next;
		n3.next = null;
		while (n1 != null) { // recover list
			n2 = n1.next;
			n1.next = n3;
			n3 = n1;
			n1 = n2;
		}
		return res;
	}
```



删除单链表倒数第n个节点

求单链表的中间节点

快慢指针，慢的走一步，快的走两步，当快指针到达尾节点时，慢指针移动到中间节点 

```java
	// 遍历一次，找出单链表的中间节点
	public ListNode findMiddleNode(ListNode head) {
		if (null == head) {
			return;
		}
		ListNode slow = head;
		ListNode fast = head;
		while (null != fast && null != fast.next) {
			fast = fast.next.next;
			slow = slow.next;
		}
		return slow;
	}
```



### 链表划分成两部分

给定一个单链表和数值x，划分链表使得所有小于x的节点排在大于等于x的节点之前 

```java
	public class Solution {
		/**
		 * @param head: The first node of linked list.
		 * @param x: an integer
		 * @return: a ListNode
		 */
		public ListNode partition(ListNode head, int x) {
			// write your code here
			if(head == null) return null;
			ListNode leftDummy = new ListNode(0);
			ListNode rightDummy = new ListNode(0);
			ListNode left = leftDummy, right = rightDummy;
			while (head != null) {
				if (head.val < x) {
					left.next = head;
					left = head;
				} else {
					right.next = head;
					right = head;
				}
				head = head.next;
			}
			right.next = null;
			left.next = rightDummy.next;
			return leftDummy.next;
		}
	}
```



### 将单向链表按某值划分成左边小、 中间相等、 右边大的形式

```java
	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node listPartition1(Node head, int pivot) {
		if (head == null) {
			return head;
		}
		Node cur = head;
		int i = 0;
		while (cur != null) {
			i++;
			cur = cur.next;
		}
		Node[] nodeArr = new Node[i];
		i = 0;
		cur = head;
		for (i = 0; i != nodeArr.length; i++) {
			nodeArr[i] = cur;
			cur = cur.next;
		}
		arrPartition(nodeArr, pivot);
		for (i = 1; i != nodeArr.length; i++) {
			nodeArr[i - 1].next = nodeArr[i];
		}
		nodeArr[i - 1].next = null;
		return nodeArr[0];
	}

	public static void arrPartition(Node[] nodeArr, int pivot) {
		int small = -1;
		int big = nodeArr.length;
		int index = 0;
		while (index != big) {
			if (nodeArr[index].value < pivot) {
				swap(nodeArr, ++small, index++);
			} else if (nodeArr[index].value == pivot) {
				index++;
			} else {
				swap(nodeArr, --big, index);
			}
		}
	}

	public static void swap(Node[] nodeArr, int a, int b) {
		Node tmp = nodeArr[a];
		nodeArr[a] = nodeArr[b];
		nodeArr[b] = tmp;
	}

	public static Node listPartition2(Node head, int pivot) {
		Node sH = null; // small head
		Node sT = null; // small tail
		Node eH = null; // equal head
		Node eT = null; // equal tail
		Node bH = null; // big head
		Node bT = null; // big tail
		Node next = null; // save next node
		// every node distributed to three lists
		while (head != null) {
			next = head.next;
			head.next = null;
			if (head.value < pivot) {
				if (sH == null) {
					sH = head;
					sT = head;
				} else {
					sT.next = head;
					sT = head;
				}
			} else if (head.value == pivot) {
				if (eH == null) {
					eH = head;
					eT = head;
				} else {
					eT.next = head;
					eT = head;
				}
			} else {
				if (bH == null) {
					bH = head;
					bT = head;
				} else {
					bT.next = head;
					bT = head;
				}
			}
			head = next;
		}
		// small and equal reconnect
		if (sT != null) {
			sT.next = eH;
			eT = eT == null ? sT : eT;
		}
		// all reconnect
		if (eT != null) {
			eT.next = bH;
		}
		return sH != null ? sH : eH != null ? eH : bH;
	}
```



### 复制含有随机指针界节点的链表

(利用栈key老节点value新节点或者每次创建的新节点都接在老节点后面)

```java
	public static class Node {
		public int value;
		public Node next;
		public Node rand;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node copyListWithRand1(Node head) {
		HashMap<Node, Node> map = new HashMap<Node, Node>();
		Node cur = head;
		while (cur != null) {
			map.put(cur, new Node(cur.value));
			cur = cur.next;
		}
		cur = head;
		while (cur != null) {
			map.get(cur).next = map.get(cur.next);
			map.get(cur).rand = map.get(cur.rand);
			cur = cur.next;
		}
		return map.get(head);
	}

	public static Node copyListWithRand2(Node head) {
		if (head == null) {
			return null;
		}
		Node cur = head;
		Node next = null;
		// copy node and link to every node
		while (cur != null) {
			next = cur.next;
			cur.next = new Node(cur.value);
			cur.next.next = next;
			cur = next;
		}
		cur = head;
		Node curCopy = null;
		// set copy node rand
		while (cur != null) {
			next = cur.next.next;
			curCopy = cur.next;
			curCopy.rand = cur.rand != null ? cur.rand.next : null;
			cur = next;
		}
		Node res = head.next;
		cur = head;
		// split
		while (cur != null) {
			next = cur.next.next;
			curCopy = cur.next;
			cur.next = next;
			curCopy.next = next != null ? next.next : null;
			cur = next;
		}
		return res;
	}
```



两个链表相交的一系列问题(包含链表有环的情况)

```java
	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node getIntersectNode(Node head1, Node head2) {
		if (head1 == null || head2 == null) {
			return null;
		}
		Node loop1 = getLoopNode(head1);
		Node loop2 = getLoopNode(head2);
		if (loop1 == null && loop2 == null) {
			return noLoop(head1, head2);
		}
		if (loop1 != null && loop2 != null) {
			return bothLoop(head1, loop1, head2, loop2);
		}
		return null;
	}

	public static Node getLoopNode(Node head) {
		if (head == null || head.next == null || head.next.next == null) {
			return null;
		}
		Node n1 = head.next; // n1 -> slow
		Node n2 = head.next.next; // n2 -> fast
		while (n1 != n2) {
			if (n2.next == null || n2.next.next == null) {
				return null;
			}
			n2 = n2.next.next;
			n1 = n1.next;
		}
		n2 = head; // n2 -> walk again from head
		while (n1 != n2) {
			n1 = n1.next;
			n2 = n2.next;
		}
		return n1;
	}

	public static Node noLoop(Node head1, Node head2) {
		if (head1 == null || head2 == null) {
			return null;
		}
		Node cur1 = head1;
		Node cur2 = head2;
		int n = 0;
		while (cur1.next != null) {
			n++;
			cur1 = cur1.next;
		}
		while (cur2.next != null) {
			n--;
			cur2 = cur2.next;
		}
		if (cur1 != cur2) {
			return null;
		}
		cur1 = n > 0 ? head1 : head2;
		cur2 = cur1 == head1 ? head2 : head1;
		n = Math.abs(n);
		while (n != 0) {
			n--;
			cur1 = cur1.next;
		}
		while (cur1 != cur2) {
			cur1 = cur1.next;
			cur2 = cur2.next;
		}
		return cur1;
	}

	public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
		Node cur1 = null;
		Node cur2 = null;
		if (loop1 == loop2) {
			cur1 = head1;
			cur2 = head2;
			int n = 0;
			while (cur1 != loop1) {
				n++;
				cur1 = cur1.next;
			}
			while (cur2 != loop2) {
				n--;
				cur2 = cur2.next;
			}
			cur1 = n > 0 ? head1 : head2;
			cur2 = cur1 == head1 ? head2 : head1;
			n = Math.abs(n);
			while (n != 0) {
				n--;
				cur1 = cur1.next;
			}
			while (cur1 != cur2) {
				cur1 = cur1.next;
				cur2 = cur2.next;
			}
			return cur1;
		} else {
			cur1 = loop1.next;
			while (cur1 != loop1) {
				if (cur1 == loop2) {
					return loop1;
				}
				cur1 = cur1.next;
			}
			return null;
		}
	}

```

### 二分的小扩展 

```java
	public static int getLessIndex(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1; // no exist
		}
		if (arr.length == 1 || arr[0] < arr[1]) {
			return 0;
		}
		if (arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr.length - 1;
		}
		int left = 1;
		int right = arr.length - 2;
		int mid = 0;
		while (left < right) {
			mid = (left + right) / 2;
			if (arr[mid] > arr[mid - 1]) {
				right = mid - 1;
			} else if (arr[mid] > arr[mid + 1]) {
				left = mid + 1;
			} else {
				return mid;
			}
		}
		return left;
	}
```



### 在O(1)时间删除链表节点

(前提是给了头指针和要删除节点的指针，常规是找到前一个节点然后让前一个节点指先待删除的后一个节点为O(N))，其实可以将后一个节点的值赋给待删除的节点然后将待删除的节点的next指向后一个的后一个节点(但是如果删除的是最后一个节点就必须要找到前驱节点了)

### 链表求和

(数字存贮就是按照原来位置相反的顺序，用链表的形式返回和) 
(A+B)/10进位值 
(A+B)%10当前位的值

```java
	public class Solution {
		public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
			ListNode c1 = l1;
			ListNode c2 = l2;
			ListNode sentinel = new ListNode(0);
			ListNode d = sentinel;
			int sum = 0;
			while (c1 != null || c2 != null) {
				sum /= 10;
				if (c1 != null) {
					sum += c1.val;
					c1 = c1.next;
				}
				if (c2 != null) {
					sum += c2.val;
					c2 = c2.next;
				}
				d.next = new ListNode(sum % 10);
				d = d.next;
			}
			if (sum / 10 == 1)
				d.next = new ListNode(1);
			return sentinel.next;
		}
	}
```



### 单链表O(NlogN)排序

(用快排实现,只是改每个链表中节点的值而不该节点的位置)(或者用归并实现)

```java
public ListNode sortlist(ListNode head) {
	//快排
	quicksort(head,null);
	return head;
}

public void quicksort(ListNode head , ListNode end) {
	if(head != null) {
		ListNode mid = partion(head,end);
		quicksort(head,mid);
		quicksort(mid.next,end);
	}
}

public ListNode partion(ListNode head,ListNode end) {
	//基准值为head的值
	//p1维护的是所有小于等于key值的边界，所以当p2一直往后遍历的时候只要遇到小于key的值就加到p1的后面就行
	//p2维护的是当前遍历到的节点
	ListNode p1 = head, p2 = head.next;
	//走到尾才停下
	while(p2 != end) {
		//大于key值p1向前走一步然后交换key和p1的值
		if(head.val > p2.val) {
			//将p2的值接到p1的后面即交换值
			p1 = p1.next;
    		int temp = p1.val;
			p1.val = p2.val;
			p2.val = temp;
		}
		p2 = p2.next;
	}
	//最后交换head即key和p1的值
	//当有序时不用交换p1和key的值
	if(p1 != head ){
		int temp = p1.val;
		p1.val = head.val;
		head.val = temp; 
	}
	return p1;
}
```

归并排序的做法

合并两个有序的链表

删除链表中值重复的节点



## **二叉树**

二叉树的先序、中序、后序遍历，递归和非递归
递归可以访问每个节点三次，因此不同访问时机顺序就是先、中、后遍历
非递归用栈实现只能访问每个节点两次，因此在后序遍历的时候用栈会复杂一点

```java
	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static void preOrderRecur(Node head) {
		if (head == null) {
			return;
		}
		System.out.print(head.value + " ");
		preOrderRecur(head.left);
		preOrderRecur(head.right);
	}

	public static void inOrderRecur(Node head) {
		if (head == null) {
			return;
		}
		inOrderRecur(head.left);
		System.out.print(head.value + " ");
		inOrderRecur(head.right);
	}

	public static void posOrderRecur(Node head) {
		if (head == null) {
			return;
		}
		posOrderRecur(head.left);
		posOrderRecur(head.right);
		System.out.print(head.value + " ");
	}

	public static void preOrderUnRecur(Node head) {
		System.out.print("pre-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.add(head);
			while (!stack.isEmpty()) {
				head = stack.pop();
				System.out.print(head.value + " ");
				if (head.right != null) {
					stack.push(head.right);
				}
				if (head.left != null) {
					stack.push(head.left);
				}
			}
		}
		System.out.println();
	}

	public static void inOrderUnRecur(Node head) {
		System.out.print("in-order: ");
		if (head != null) {
			Stack<Node> stack = new Stack<Node>();
			while (!stack.isEmpty() || head != null) {
				if (head != null) {
					stack.push(head);
					head = head.left;
				} else {
					head = stack.pop();
					System.out.print(head.value + " ");
					head = head.right;
				}
			}
		}
		System.out.println();
	}

	public static void posOrderUnRecur1(Node head) {
		System.out.print("pos-order: ");
		if (head != null) {
			Stack<Node> s1 = new Stack<Node>();
			Stack<Node> s2 = new Stack<Node>();
			s1.push(head);
			while (!s1.isEmpty()) {
				head = s1.pop();
				s2.push(head);
				if (head.left != null) {
					s1.push(head.left);
				}
				if (head.right != null) {
					s1.push(head.right);
				}
			}
			while (!s2.isEmpty()) {
				System.out.print(s2.pop().value + " ");
			}
		}
		System.out.println();
	}

	public static void posOrderUnRecur2(Node h) {
		System.out.print("pos-order: ");
		if (h != null) {
			Stack<Node> stack = new Stack<Node>();
			stack.push(h);
			Node c = null;
			while (!stack.isEmpty()) {
				c = stack.peek();
				if (c.left != null && h != c.left && h != c.right) {
					stack.push(c.left);
				} else if (c.right != null && h != c.right) {
					stack.push(c.right);
				} else {
					System.out.print(stack.pop().value + " ");
					h = c;
				}
			}
		}
		System.out.println();
	}
```



二叉树找一个节点的中序遍历的后继节点

```java
	public static class Node {
		public int value;
		public Node left;
		public Node right;
		public Node parent;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node getSuccessorNode(Node node) {
		if (node == null) {
			return node;
		}
		if (node.right != null) {
			return getLeftMost(node.right);
		} else {
			Node parent = node.parent;
			while (parent != null && parent.left != node) {
				node = parent;
				parent = node.parent;
			}
			return parent;
		}
	}

	public static Node getLeftMost(Node node) {
		if (node == null) {
			return node;
		}
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}
```



二叉树的序列化和反序列化

```java
	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static String serialByPre(Node head) {
		if (head == null) {
			return "#!";
		}
		String res = head.value + "!";
		res += serialByPre(head.left);
		res += serialByPre(head.right);
		return res;
	}

	public static Node reconByPreString(String preStr) {
		String[] values = preStr.split("!");
		Queue<String> queue = new LinkedList<String>();
		for (int i = 0; i != values.length; i++) {
			queue.offer(values[i]);
		}
		return reconPreOrder(queue);
	}

	public static Node reconPreOrder(Queue<String> queue) {
		String value = queue.poll();
		if (value.equals("#")) {
			return null;
		}
		Node head = new Node(Integer.valueOf(value));
		head.left = reconPreOrder(queue);
		head.right = reconPreOrder(queue);
		return head;
	}

	//根据队列实现
	public static String serialByLevel(Node head) {
		if (head == null) {
			return "#!";
		}
		String res = head.value + "!";
		Queue<Node> queue = new LinkedList<Node>();
		queue.offer(head);
		while (!queue.isEmpty()) {
			head = queue.poll();
			if (head.left != null) {
				res += head.left.value + "!";
				queue.offer(head.left);
			} else {
				res += "#!";
			}
			if (head.right != null) {
				res += head.right.value + "!";
				queue.offer(head.right);
			} else {
				res += "#!";
			}
		}
		return res;
	}

	public static Node reconByLevelString(String levelStr) {
		String[] values = levelStr.split("!");
		int index = 0;
		Node head = generateNodeByString(values[index++]);
		Queue<Node> queue = new LinkedList<Node>();
		if (head != null) {
			queue.offer(head);
		}
		Node node = null;
		while (!queue.isEmpty()) {
			node = queue.poll();
			node.left = generateNodeByString(values[index++]);
			node.right = generateNodeByString(values[index++]);
			if (node.left != null) {
				queue.offer(node.left);
			}
			if (node.right != null) {
				queue.offer(node.right);
			}
		}
		return head;
	}
```



折纸问题

判断二叉树是否是平衡二叉树、搜索二叉树、完全二叉树

```java
    class Solution {
        public boolean isBalanced(TreeNode root) {
            if(root == null)
                return true;
            return Math.abs(maxHigh(root.left) - maxHigh(root.right)) <= 1
                && isBalanced(root.left) && isBalanced(root.right);
        }
        public int maxHigh(TreeNode root){
            if(root == null)
                return 0;
            return Math.max(maxHigh(root.left), maxHigh(root.right))+1;
        }
    }
	public static boolean isBST(Node head) {
		if (head == null) {
			return true;
		}
		boolean res = true;
		Node pre = null;
		Node cur1 = head;
		Node cur2 = null;
		while (cur1 != null) {
			cur2 = cur1.left;
			if (cur2 != null) {
				while (cur2.right != null && cur2.right != cur1) {
					cur2 = cur2.right;
				}
				if (cur2.right == null) {
					cur2.right = cur1;
					cur1 = cur1.left;
					continue;
				} else {
					cur2.right = null;
				}
			}
			if (pre != null && pre.value > cur1.value) {
				res = false;
			}
			pre = cur1;
			cur1 = cur1.right;
		}
		return res;
	}

	class Solution {
		public boolean _CheckCompleteTree(TreeNode root) {
			Queue<TreeNode> queue = LinkedList<>();
			if(root == null)
				return true;
			queue.add(root);
			while(!queue.isEmpty()){
				TreeNode node = queue.pop();
				if(node != null){
					if(flag == true)
						return false;
					queue.add(node.left);
					queue.add(node.right);
				}else{
					flag = true;
				}
			}
			return true;
		}
	}

```



完全二叉树的节点个数

```java
	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static int nodeNum(Node head) {
		if (head == null) {
			return 0;
		}
		return bs(head, 1, mostLeftLevel(head, 1));
	}

	public static int bs(Node node, int l, int h) {
		if (l == h) {
			return 1;
		}
		if (mostLeftLevel(node.right, l + 1) == h) {
			return (1 << (h - l)) + bs(node.right, l + 1, h);
		} else {
			return (1 << (h - l - 1)) + bs(node.left, l + 1, h);
		}
	}

	public static int mostLeftLevel(Node node, int level) {
		while (node != null) {
			level++;
			node = node.left;
		}
		return level - 1;
	}
```





> 完全二叉树：若二叉树的高度是h，除第h层之外，其他（1~h-1）层的节点数都达到了最大个数，并且第h层的节点都连续的集中在最左边。想到点什么没？实际上，完全二叉树和堆联系比较紧密哈~~~
> 满二叉树：除最后一层外，每一层上的所有节点都有两个子节点，最后一层都是叶子节点。
> 哈夫曼树：给定n个权值作为n的叶子结点，构造一棵二叉树，若带权路径长度达到最小，称这样的二叉树为最优二叉树，也称为哈夫曼树(Huffman tree)。
> 二叉排序树：又称二叉查找树（Binary Search Tree），亦称二叉搜索树。二叉排序树或者是一棵空树，或者是具有下列性质的二叉树：
> 若左子树不空，则左子树上所有结点的值均小于它的根结点的值；
> 若右子树不空，则右子树上所有结点的值均大于或等于它的根结点的值；
> 左、右子树也分别为二叉排序树；
> 没有键值相等的节点
> 二分查找的时间复杂度是O(log(n))，最坏情况下的时间复杂度是O(n)（相当于顺序查找）
> 平衡二叉树：又称 AVL 树。平衡二叉树是二叉搜索树的进化版，所谓平衡二叉树指的是，左右两个子树的高度差的绝对值不超过1。
> 红黑树：红黑树是每个节点都带颜色的树，节点颜色或是红色或是黑色，红黑树是一种查找树。红黑树有一个重要的性质，从根节点到叶子节点的最长的路径不多于最短的路径的长度的两倍。对于红黑树，插入，删除，查找的复杂度都是O（log N）。 

二叉树中节点的个数

```java
	public static int getNodeNumRec(TreeNode root) {
		if (root == null) {
			return 0;
		}
		return getNodeNumRec(root.left) + getNodeNumRec(root.right) + 1;
	}
```

二叉树最大深度

```java
	class Solution {
		public int maxDepth(TreeNode root) {
			if(root == null)
				return 0;
			return Math.max(maxDepth(root.left), maxDepth(root.right))+1;
		}
	}
```

二叉树的最小深度

```java
class Solution {
    public int minDepth(TreeNode root) {
        if(root == null)
            return 0;
        int left = minDepth(root.left);
        int right = minDepth(root.right);
        return (left == 0 || right == 0) ? left + right + 1 : Math.min(left, right)+ 1;
    }
}
```



分层遍历(从上到下打印，从下到上打印，之字形打印)

```java
	class Solution {
		public List<List<Integer>> levelOrder(TreeNode root) {
			List<List<Integer>> res = new ArrayList<List<Integer>>();
			if(root == null)
				return res;
			Queue<TreeNode> queue = new LinkedList<TreeNode>();
			TreeNode cur = null;
			queue.add(root);
			while(!queue.isEmpty()){
				ArrayList<Integer> level = new ArrayList<Integer>();
				int l = queue.size();
				for(int i=0; i<l;i++){
					cur = queue.poll();
					level.add(cur.val);
					if(cur.left != null)
						queue.add(cur.left);
					if(cur.right != null)
						queue.add(cur.right);
				}
				res.add(level);
			}
			return res;
		}
	}
	class Solution {
		public List<List<Integer>> levelOrderBottom(TreeNode root) {
			List<List<Integer>> res = new LinkedList<>();
			Queue<TreeNode> queue = new LinkedList<>();
			if(root == null)
				return res;
			queue.add(root);
			while(!queue.isEmpty()){
				int count = queue.size();
				List<Integer> temp = new LinkedList<>();
				for(int i=0; i<count; i++){
					TreeNode node = queue.poll();
					temp.add(node.val);
					if(node.left != null)
						queue.add(node.left);
					if(node.right != null)
						queue.add(node.right);
				}
				// 每次都添加到第一个位置
				res.add(0, temp);
			}
			return res;
		}
	}
    public ArrayList<ArrayList<Integer> > Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer> > res = new ArrayList<ArrayList<Integer> >();
        Stack<TreeNode> s1 = new Stack<TreeNode>();
        Stack<TreeNode> s2 = new Stack<TreeNode>();
        int flag = 1;
        if(pRoot == null)
            return res;
        s2.push(pRoot);
        ArrayList<Integer> temp = new ArrayList<Integer>();
        while(!s1.isEmpty() || !s2.isEmpty()){
            if(flag % 2 != 0){
                while(!s2.isEmpty()){
                    TreeNode node = s2.pop();
                    temp.add(node.val);
                    if(node.left != null){
                        s1.push(node.left);
                    }
                    if(node.right != null){
                        s1.push(node.right);
                    }
                }
            }
            if(flag % 2 == 0){
                while(!s1.isEmpty()){
                    TreeNode node = s1.pop();
                    temp.add(node.val);
                    if(node.right != null){
                        s2.push(node.right);
                    }
                    if(node.left != null){
                        s2.push(node.left);
                    }
                }
            }
            res.add(new ArrayList<Integer>(temp));
            temp.clear();
            flag ++;
        }
        return res;
    }

```



二叉树第k层的节点个数

```java
public void get_k_level_number(TreeNode root, int k ){
	if(root == null || k == 0) {
		return 0;
	}
	if(root != null || k == 1){
		return 1;
	}
	return get_k_level_number(root.left,k-1) + get_k_level_number(root.right,k-1);
}
```

二叉树第k层的叶子节点个数

```java
public void get_k_level_number(TreeNode root, int k ){
	if(root == null || k == 0) {
		return 0;
	}
	if(root != null || k == 1){
		//叶节点才返回1
		if(root.left == null || root.right == null){
			return 1;
		}else{
			return 0;
		}
	}
	return get_k_level_number(root.left,k-1) + get_k_level_number(root.right,k-1);
}
```

判断两个树是否结构相等

递归解法：
（1）如果两棵二叉树都为空，返回真
（2）如果两棵二叉树一棵为空，另一棵不为空，返回假
（3）如果两棵二叉树都不为空，如果对应的左子树和右子树都同构返回真，其他返回假 

```java
	class Solution {
		public boolean isSameTree(TreeNode p, TreeNode q) {
			if(p == null && q == null)
				return true;
			if(p == null || q == null)
				return false;
			if(p.val == q.val)
				return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
			return false;
		}
	}
```



判断是否为二叉平衡树

```java
public boolean isBalanced(TreeNode root) {
	if(root == null) {
		return true;
	}
	return Math.abs(maxHeight(root.left),maxHeight(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
}
public int maxHeight(TreeNode root) {
	if(root == null) {
		return 0;
	}
	return Math.max(maxHeight(root.left),maxHeight(root.right)) + 1;
}
```

二叉树镜像

```java
public TreeNode invertTree(TreeNode root) {
	if(root == null) {
		return root;
	}
	TreeNode temp = root.left;
	root.left = invertTree(root.right);
	root.right = invertTree(temp);
	return root;

}
```



判断是否为对称二叉树(跟判断两个树是否结构相同类似)

```java
	class Solution {
		public boolean isSymmetric(TreeNode root) {
			return root == null || isSymmetricHelper(root.left, root.right);
		}
		public boolean isSymmetricHelper(TreeNode left, TreeNode right){
			if(left == null && right == null)
				return true;
			if(left == null || right == null)
				return false;
			if(left.val != right.val)
				return false;
			return isSymmetricHelper(left.left, right.right) && isSymmetricHelper(left.right, right.left);
		}
	}
```



二叉树中两个节点的最低公共祖先节点

递归解法：
（1）如果两个节点分别在根节点的左子树和右子树，则返回根节点
（2）如果两个节点都在左子树，则递归处理左子树；如果两个节点都在右子树，则递归处理右子树 

```java
	class Solution {
		public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
			if(root == null || root == p || root == q)
				return root;
			TreeNode left = lowestCommonAncestor(root.left, p, q);
			TreeNode right = lowestCommonAncestor(root.right, p, q);
			if(left != null && right != null)
				return root;
			return left == null ? right : left;
		}
	}
```

二叉搜索树的最低公共祖先(跟前面类似只是判断的时候用值来比较)

```java
	class Solution {
		public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
			if(root.val > p.val && root.val > q.val){
				return lowestCommonAncestor(root.left, p, q);
			}
			else if(root.val < p.val && root.val < q.val){
				return lowestCommonAncestor(root.right, p, q);
			}
			else{
				return root;
			}
		}
	}
```



二叉树的直径(任意两个节点路径长度的最大值,路径可能会穿过根节点)

```
public int path = 0;
public int diameterOfBinaryTree(TreeNode root) {
    dimhelper(root);
    return path;
}
public int dimhelper(TreeNode root) {
    if(root == null) {
        return 0;
    }
    int left = dimhelper(root.left);
    int right = dimhelper(root.right);
    path = Math.max(left+1,right+1);
    return Math.max(right,left) + 1；
}
```

由先序和中序重建二叉树

```java
public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
    return reBuilder(pre,0,pre.length-1,in,0,in.length-1);
}
public static TreeNode reBuilder(int pre[],int pleft,int pright,int in[],int ileft,int iright){
    if(pleft>pright||ileft>iright) return null;
    //先序列的第一个位置就是根节点
    TreeNode cur = new TreeNode(pre[pleft]);
    int i=0;
    //寻找根节点在中序的位置
    for(i = ileft;i<=in.length;i++){
        if(pre[pleft] == in[i])
            break;
    }
    //递归左子树进行恢复,由上面可以知道左子树的长度是i-ileft,则开始index=pleft+1,结束		 		index=pleft+1+(i-ileft)-1=pleft+i-ileft
    cur.left = reBuilder(pre,pleft+1,pleft+i-ileft,in,ileft,i-1);
    //递归右子树进行恢复，显然在pre中右子树的开始索引就是前面左子树结束索引的下一个位置，所以就是		//pleft+i-ileft+1
    cur.right = reBuilder(pre,pleft+i-ileft+1,pright,in,i+1,iright);
    return cur;
}
```

从中序与后序遍历序列构造二叉树 

```java
	class Solution {
		public TreeNode buildTree(int[] inorder, int[] postorder) {
			if(inorder.length == 0 || postorder.length == 0)
				return null;
			return buildTreeHelper(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
		}
		public TreeNode buildTreeHelper(int[] inorder, int in_start, int in_end,
										int[] postorder, int post_start, int post_end){
			if(in_start > in_end || post_start > post_end)
				return null;
			TreeNode root = new TreeNode(postorder[post_end]);
			for(int i = in_start; i <= in_end; i++){
				if(inorder[i] == postorder[post_end]){
					root.left = buildTreeHelper(inorder, in_start, i - 1, postorder, post_start,post_start + i - in_start - 1);
					root.right = buildTreeHelper(inorder, i + 1, in_end, postorder, post_start + i - in_start, post_end - 1);
				}
			}
			return root;
		}
	}
```



判断一个二叉树是不是完全二叉树(层次遍历 如果当前遍历到了null节点后面如果还有非null节点就不是完全二叉树)

```java
//相当于层次遍历的过程
public boolean isComplete(TreeNode root) {
	boolean flag = false;
	Deque<TreeNode> queue = new ArrayDeque<>();
	if(root == null) {
		return true;
	}
	queue.offer(root);
	while(!queue.isEmpty()){
		TreeNode cur = queue.poll();
		if(cur != null) {
			if(flag){
				return false;
			}
			queue.offer(cur.left);
			queue.offer(cur.right);
		}else{
			flag = true;
		}
	}
	return true;
}
```

树的子结构(判断B是不是A的子结构)

```java
public static boolean subTree(TreeNode root1,TreeNode root2){
	if(root1 == null || root2 == null) {
		return false;
	}
	return isSameTree(root1,root2) || subTree(root1.left ,root2 ) || subTree(root1.right,root2);
}

public static boolean isSameTree(TreeNode root1,TreeNode root2) {
	//先判断root2是否为空为空直接为true,因为isSameTree()是从subTree()过来的则肯定初始的时候都不是为null,比较到这里之前的比较都是相同的所以直接返回true
	//子结构不是一定要是到叶节点的部分子树，只要是树中任意一部分都行
	if(root2 == null) return true;
	if(root1 == null) return false;
	if(root1.val == root2.val) {
		return isSameTree(root1.left,root2.left)&&isSameTree(root1.right,root2.right);
	}else{
		return false;
	}
}
```

二叉树中和为某一路径的路径(从根节点到叶节点的一个路径)

```java
ArrayList<Integer> temp = new ArrayList<>();
ArrayList<ArrayList<Integer>> result = new ArrayList<>();
public ArrayList<ArrayList<Integer>> findPath(TreeNode root,int target) {
	if(root == null){
		return result;
	}
	target = target - root.val;
	temp.add(root.val);
	if(target == 0 && root.left == null || root.right == null) {
		result.add(new ArrayList<Integer>(temp));
	}else{
		findPath(root.left,target);
		findPath(root.right,target);
	}
	//回溯
	temp.remove(temp.size()-1);
	return result;
}
```



二叉树的下一个节点(中序遍历的下一个节点)

```java
	public class Solution {
		public TreeLinkNode GetNext(TreeLinkNode pNode)
		{
			if(pNode == null){
				return null;
			}
			if(pNode.right != null){
				TreeLinkNode node = pNode.right;
				while(node.left != null){
					node = node.left;
				}
				return node;
			}
			while(pNode.next != null){
				TreeLinkNode root = pNode.next;
				if(pNode == root.left)
					return root;
				pNode = root;
			}
			return null;
		}
	}
```



二叉搜索树的第k个节点(二叉搜素树的中序比遍历是有序的，直接返回第k个)

```java
	class Solution {
		public int kthSmallest(TreeNode root, int k) {
			if(root == null)
				return Integer.MIN_VALUE;
			Stack<TreeNode> stack = new Stack<>();
			int count = 0;
			TreeNode p = root;
			while(p != null || !stack.isEmpty()){
				if(p != null){
					stack.push(p);
					p = p.left;
				}else{
					TreeNode node = stack.pop();
					count ++;
					if(count == k)
						return node.val;
					p = node.right;
				}
			}
			return Integer.MIN_VALUE;
		}
	}
```



**hash函数和hash表**

RandomPool结构(insert() delete() getRandom()) 利用两个hash表实现记录进表的顺序

```java
	public static class Pool<K> {
		private HashMap<K, Integer> keyIndexMap;
		private HashMap<Integer, K> indexKeyMap;
		private int size;

		public Pool() {
			this.keyIndexMap = new HashMap<K, Integer>();
			this.indexKeyMap = new HashMap<Integer, K>();
			this.size = 0;
		}

		public void insert(K key) {
			if (!this.keyIndexMap.containsKey(key)) {
				this.keyIndexMap.put(key, this.size);
				this.indexKeyMap.put(this.size++, key);
			}
		}

		public void delete(K key) {
			if (this.keyIndexMap.containsKey(key)) {
				int deleteIndex = this.keyIndexMap.get(key);
				int lastIndex = --this.size;
				K lastKey = this.indexKeyMap.get(lastIndex);
				this.keyIndexMap.put(lastKey, deleteIndex);
				this.indexKeyMap.put(deleteIndex, lastKey);
				this.keyIndexMap.remove(key);
				this.indexKeyMap.remove(lastIndex);
			}
		}

		public K getRandom() {
			if (this.size == 0) {
				return null;
			}
			int randomIndex = (int) (Math.random() * this.size); // 0 ~ size -1
			return this.indexKeyMap.get(randomIndex);
		}

	}
```

布隆过滤器

一致性hash

岛问题

并查集结构

前缀树：字符串数组中的字符串哪些是在另一个字符串数组中出现过得或者是哪些字符串是在另一个数组中某个字符串的前缀出现的，或者是求出现次数最大的前缀



**贪心策略**

分割金条问题 哈夫曼编码的问题结构

```java
	public static int lessMoney(int[] arr) {
		PriorityQueue<Integer> pQ = new PriorityQueue<>();
		for (int i = 0; i < arr.length; i++) {
			pQ.add(arr[i]);
		}
		int sum = 0;
		int cur = 0;
		while (pQ.size() > 1) {
			cur = pQ.poll() + pQ.poll();
			sum += cur;
			pQ.add(cur);
		}
		return sum;
	}
```

做项目得到最大利润问题 cost[i]项目i的话费  profits[i] 项目i的利润(扣除花费剩下的利润)

```java
	public static class Node {
		public int p;
		public int c;

		public Node(int p, int c) {
			this.p = p;
			this.c = c;
		}
	}

	public static class MinCostComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.c - o2.c;
		}

	}

	public static class MaxProfitComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o2.p - o1.p;
		}

	}

	public static int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
		Node[] nodes = new Node[Profits.length];
		for (int i = 0; i < Profits.length; i++) {
			nodes[i] = new Node(Profits[i], Capital[i]);
		}

		PriorityQueue<Node> minCostQ = new PriorityQueue<>(new MinCostComparator());
		PriorityQueue<Node> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
		for (int i = 0; i < nodes.length; i++) {
			minCostQ.add(nodes[i]);
		}
		for (int i = 0; i < k; i++) {
			while (!minCostQ.isEmpty() && minCostQ.peek().c <= W) {
				maxProfitQ.add(minCostQ.poll());
			}
			if (maxProfitQ.isEmpty()) {
				return W;
			}
			W += maxProfitQ.poll().p;
		}
		return W;
	}
```



数据流中获取中位数 两个堆实现一个大顶堆维护前半数据的最大值，一个小根堆维护后半数据的最小值

```java
	public static class MedianHolder {
		private PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(new MaxHeapComparator());
		private PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(new MinHeapComparator());

		private void modifyTwoHeapsSize() {
			if (this.maxHeap.size() == this.minHeap.size() + 2) {
				this.minHeap.add(this.maxHeap.poll());
			}
			if (this.minHeap.size() == this.maxHeap.size() + 2) {
				this.maxHeap.add(this.minHeap.poll());
			}
		}

		public void addNumber(int num) {
			if (this.maxHeap.isEmpty()) {
				this.maxHeap.add(num);
				return;
			}
			if (this.maxHeap.peek() >= num) {
				this.maxHeap.add(num);
			} else {
				if (this.minHeap.isEmpty()) {
					this.minHeap.add(num);
					return;
				}		
				if (this.minHeap.peek() > num) {
					this.maxHeap.add(num);
				} else {
					this.minHeap.add(num);
				}
			}
			modifyTwoHeapsSize();
		}

		public Integer getMedian() {
			int maxHeapSize = this.maxHeap.size();
			int minHeapSize = this.minHeap.size();
			if (maxHeapSize + minHeapSize == 0) {
				return null;
			}
			Integer maxHeapHead = this.maxHeap.peek();
			Integer minHeapHead = this.minHeap.peek();
			if (((maxHeapSize + minHeapSize) & 1) == 0) {
				return (maxHeapHead + minHeapHead) / 2;
			}
			return maxHeapSize > minHeapSize ? maxHeapHead : minHeapHead;
		}

	}

	public static class MaxHeapComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			if (o2 > o1) {
				return 1;
			} else {
				return -1;
			}
		}
	}

	public static class MinHeapComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			if (o2 < o1) {
				return 1;
			} else {
				return -1;
			}
		}
	}
```



字符串数组拼接后形成最低的字典序  比较器实现   str1str2 > str1str2 那么str1显然要排在str2后面

```java
	public static class MyComparator implements Comparator<String> {
		@Override
		public int compare(String a, String b) {
			return (a + b).compareTo(b + a);
		}
	}

	public static String lowestString(String[] strs) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		Arrays.sort(strs, new MyComparator());
		String res = "";
		for (int i = 0; i < strs.length; i++) {
			res += strs[i];
		}
		return res;
	}
```



宣讲会的最多场次  选择结束时间最早的那个而不是最早开始的那个

```java
	public static class Program {
		public int start;
		public int end;

		public Program(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	public static class ProgramComparator implements Comparator<Program> {

		@Override
		public int compare(Program o1, Program o2) {
			return o1.end - o2.end;
		}

	}

	public static int bestArrange(Program[] programs, int start) {
		Arrays.sort(programs, new ProgramComparator());
		int result = 0;
		for (int i = 0; i < programs.length; i++) {
			if (start <= programs[i].start) {
				result++;
				start = programs[i].end;
			}
		}
		return result;
	}
```





**递归和动态规划**

暴力递归：
​	把问题转化为规模缩小了的同类问题的子问题；
​	有明确的不需要继续进行递归的条件（base case）；
​	有当得到了子问题的结果之后 的决策过程；
​	不记录每一个子问题的解。
动态规划：
​	从暴力递归中来
​	将每一个子问题的解记录下来，避免重复计算
​	把暴力递归的过程，抽象成了状态表达
​	并且存在化简状态表达，使其更加简洁的可能。
动态规划的前提：无后效性尝试
​	列出可变参数组合（表）
​	base case填简单状态
​	最终状态
​	普遍位置如何依赖其他位置
​	根据第4步的依赖顺序逆着从简单到复杂（填表）

求n!

汉诺塔问题

```java
	public static void hanoi(int n) {
		if (n > 0) {
			func(n, n, "left", "mid", "right");
		}
	}

	public static void func(int rest, int down, String from, String help, String to) {
		if (rest == 1) {
			System.out.println("move " + down + " from " + from + " to " + to);
		} else {
			func(rest - 1, down - 1, from, to, help);
			func(1, down, from, help, to);
			func(rest - 1, down - 1, help, from, to);
		}
	}
```



打印字符串的全部子序列(子序列不等于子串，子串必须要是连续的，子序列不用)

```java
	public static void printAllSubsquence(String str) {
		char[] chs = str.toCharArray();
		process(chs, 0);
	}

	public static void process(char[] chs, int i) {
		if (i == chs.length) {
			System.out.println(String.valueOf(chs));
			return;
		}
		process(chs, i + 1);
		char tmp = chs[i];
		chs[i] = 0;
		process(chs, i + 1);
		chs[i] = tmp;
	}
	
	public static void function(String str) {
		char[] chs = str.toCharArray();
		process(chs, 0, new ArrayList<Character>());
	}
	
	public static void process(char[] chs, int i, List<Character> res) {
		if(i == chs.length) {
			printList(res);
		}
		List<Character> resKeep = copyList(res);
		resKeep.add(chs[i]);
		process(chs, i+1, resKeep);
		List<Character> resNoInclude = copyList(res);
		process(chs, i+1, resNoInclude);
	}
```



打印字符串的全排序(包括重复元素和不重复元素)

```java
	public static void printAllPermutations1(String str) {
		char[] chs = str.toCharArray();
		process1(chs, 0);
	}

	public static void process1(char[] chs, int i) {
		if (i == chs.length) {
			System.out.println(String.valueOf(chs));
		}
		for (int j = i; j < chs.length; j++) {
			swap(chs, i, j);
			process1(chs, i + 1);
			swap(chs, i, j);
		}
	}

	public static void printAllPermutations2(String str) {
		char[] chs = str.toCharArray();
		process2(chs, 0);
	}

	public static void process2(char[] chs, int i) {
		if (i == chs.length) {
			System.out.println(String.valueOf(chs));
		}
		HashSet<Character> set = new HashSet<>();
		for (int j = i; j < chs.length; j++) {
			if (!set.contains(chs[j])) {
				set.add(chs[j]);
				swap(chs, i, j);
				process2(chs, i + 1);
				swap(chs, i, j);
			}
		}
	}

	public static void swap(char[] chs, int i, int j) {
		char tmp = chs[i];
		chs[i] = chs[j];
		chs[j] = tmp;
	}
```



求牛的个数

```java
	public static int cowNumber1(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2 || n == 3) {
			return n;
		}
		return cowNumber1(n - 1) + cowNumber1(n - 3);
	}

	public static int cowNumber2(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2 || n == 3) {
			return n;
		}
		int res = 3;
		int pre = 2;
		int prepre = 1;
		int tmp1 = 0;
		int tmp2 = 0;
		for (int i = 4; i <= n; i++) {
			tmp1 = res;
			tmp2 = pre;
			res = res + prepre;
			pre = tmp1;
			prepre = tmp2;
		}
		return res;
	}
```



不用其他栈实现栈中元素的逆序 

```java
//逆序一个栈，不使用其他数据结构
public class ReverseStackUsingRecursive {
    public static void reverse(Stack<Integer> stack){
        if (stack.isEmpty()){
            return;
        }
    	int i = getAndRemoveLastElement(stack);
    	reverse(stack);
    	stack.push(i);
    }
 	//递归函数1，将栈的栈底元素返回并删除

    public static int getAndRemoveLastElement(Stack<Integer> stack){
    	int result = stack.pop();
        if (stack.isEmpty()){
            return result;
        }else {
            int last = getAndRemoveLastElement(stack);
            stack.push(result);
            return last;
        }
    }
}
```

反转栈

```java
	public static void reverse(Stack<Integer> stack) {
		if (stack.isEmpty()) {
			return;
		}
		int i = getAndRemoveLastElement(stack);
		reverse(stack);
		stack.push(i);
	}

	public static int getAndRemoveLastElement(Stack<Integer> stack) {
		int result = stack.pop();
		if (stack.isEmpty()) {
			return result;
		} else {
			int last = getAndRemoveLastElement(stack);
			stack.push(result);
			return last;
		}
	}
```

返回二维数组从左上到右下的最大路径和(只能向下或者向右)

```java
	public static int minPath1(int[][] matrix) {
		return process1(matrix, matrix.length - 1, matrix[0].length - 1);
	}

	public static int process1(int[][] matrix, int i, int j) {
		int res = matrix[i][j];
		if (i == 0 && j == 0) {
			return res;
		}
		if (i == 0 && j != 0) {
			return res + process1(matrix, i, j - 1);
		}
		if (i != 0 && j == 0) {
			return res + process1(matrix, i - 1, j);
		}
		return res + Math.min(process1(matrix, i, j - 1), process1(matrix, i - 1, j));
	}

	public static int minPath2(int[][] m) {
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 0;
		}
		int row = m.length;
		int col = m[0].length;
		int[][] dp = new int[row][col];
		dp[0][0] = m[0][0];
		for (int i = 1; i < row; i++) {
			dp[i][0] = dp[i - 1][0] + m[i][0];
		}
		for (int j = 1; j < col; j++) {
			dp[0][j] = dp[0][j - 1] + m[0][j];
		}
		for (int i = 1; i < row; i++) {
			for (int j = 1; j < col; j++) {
				dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
			}
		}
		return dp[row - 1][col - 1];
	}
```



数组arr和整数aim,能不能通过数组的数字累加得到aim

```java
	public static boolean money1(int[] arr, int aim) {
		return process1(arr, 0, 0, aim);
	}

	public static boolean process1(int[] arr, int i, int sum, int aim) {
		if (sum == aim) {
			return true;
		}
		// sum != aim
		if (i == arr.length) {
			return false;
		}
		return process1(arr, i + 1, sum, aim) || process1(arr, i + 1, sum + arr[i], aim);
	}

	public static boolean money2(int[] arr, int aim) {
		boolean[][] dp = new boolean[arr.length + 1][aim + 1];
		for (int i = 0; i < dp.length; i++) {
			dp[i][aim] = true;
		}
		for (int i = arr.length - 1; i >= 0; i--) {
			for (int j = aim - 1; j >= 0; j--) {
				dp[i][j] = dp[i + 1][j];
				if (j + arr[i] <= aim) {
					dp[i][j] = dp[i][j] || dp[i + 1][j + arr[i]];
				}
			}
		}
		return dp[0][0];
	}
```



换钱的方法数

```java
    //暴力递归法 process1(arr,index,aim)用arr的index到length-1的值组成aim的方法
    public static int getMethod1(int[] arr,int aim) {
        if(arr == null || arr.length == 0 || aim <0)
            return 0;
        return process1(arr,0,aim);
    }

    public static int process1(int arr[],int index,int aim) {
        int count = 0;
        //到最后aim是否为0
        if(index == arr.length) {
            count = (aim == 0 ? 1 : 0);

        }else {
            //不断增加张数
            for (int i = 0; arr[index] * i <= aim; i++)
                count += process1(arr, index + 1, aim - arr[index] * i);
        }
        return count;
    }


    /**
     * 记忆搜索优化，准备全局变量map，记录已经计算过的递归过程的结果
     * map是一张二维表，map[i][j]表示递归过程p(i,j)的返回值。另外有一些特别值，map[i][j]==0表示递归过程p(i,j)从来没有计算过。
     * map[i][j]==-1表示递归过程p(i,j)计算过，但是返回是0.
     * 如果map[i][j]的值既不等于0，也不等于-1，记为a，则表示递归过程p(i,j)的返回值为a
     * 记忆化搜索的时间复杂度为O(N*aim^2)
     * @param arr
     * @param aim
     * @return
     */
    private static int getMethod2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[][] map = new int[arr.length + 1][aim + 1];
        return process2(arr, 0, aim, map);
    }

    private static int process2(int[] arr, int index, int aim, int[][] map) {
        int res = 0;
        if (index == arr.length) {
            res = aim == 0 ? 1 : 0;
        } else {
            int mapValue = 0;
            for (int i = 0; arr[index] * i <= aim; i++) {
                mapValue = map[index + 1][aim - arr[index] * i];
                if (mapValue != 0) {
                    res += mapValue == -1 ? 0 : mapValue;
                } else {
                    res += process2(arr, index + 1, aim - arr[index] * i, map);
                }
            }
        }
        map[index][aim] = res == 0 ? -1 : res;
        return res;
    }


    /**
     * 除第一行和第一列的其他位置，记为位置（i,j）。dp[i,j]的值是以下几个值的累加。
     *
     * 完全不用arr[i]货币，只用arr[0...i-1]货币时，方法数为dp[i-1][j];
     * 用1张arr[i]货币，剩下的钱用arr[0...i-1]货币组成时，方法数为dp[i-1][j-arr[i]];
     * 用2张arr[i]货币，剩下的钱用arr[0...i-1]货币组成时，方法数为dp[i-1][j-2*arr[i]];
     * ...
     * 用k张arr[i]货币，剩下的钱用arr[0...i-1]货币组成时，方法数为dp[i-1][j-k*arr[i]]，j-k*arr[i]>=0，k为非负整数.
     *
     * 动态规划方法
     * 生成生成行数为N，列数为aim+1的矩阵dp，dp[i][j]的含义是在使用arr[0...i]货币的情况下，组成钱数j有多少种方法。
     * 时间复杂度为O(N*aim^2)。
     *
     * @param arr
     * @param aim
     * @return
     */
    private static int getMethod3(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[][] dp = new int[arr.length][aim + 1];
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }
        for (int j = 1; arr[0] * j <= aim; j++) {
            dp[0][arr[0] * j] = 1;
        }
        int num = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j <= aim; j++) {
                num = 0;
                for (int k = 0; j - arr[i] * k >= 0; k++) {
                    num += dp[i - 1][j - arr[i] * k];
                }
                dp[i][j] = num;
            }
        }
        return dp[arr.length - 1][aim];
    }

    //动态规划法
    /**
     * 步骤3可以简化为dp[i][j] = dp[i-1][j]+dp[i][j-arr[i]]
     * 时间复杂度度为O(N*aim)
     *
     * @param arr
     * @param aim
     * @return
     */
    public static int getMethod4(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[][] dp = new int[arr.length][aim + 1];
        for (int i = 0; i < arr.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; arr[0] * i <= aim; i++) {
            dp[0][arr[0] * i] = 1;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j <= aim; j++) {
                dp[i][j] = dp[i - 1][j];
                dp[i][j] += j - arr[i] >= 0 ? dp[i][j - arr[i]] : 0;
            }
        }
        return dp[arr.length - 1][aim];
    }

    /**
     * 空间压缩
     * @param arr
     * @param aim
     * @return
     */
    public static int getMethod5(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 0) {
            return 0;
        }
        int[] dp = new int[aim + 1];
        for (int j = 0; j * arr[0] <= aim; j++) {
            dp[arr[0] * j] = 1;
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j <= aim; j++) {
                dp[j] += j - arr[i] >= 0 ? dp[j - arr[i]] : 0;
            }
        }
        return dp[aim];
    }
```




**字符串**

KMP算法 

BM算法

替换空格

最长公共前缀

最长回文串(通过给定字符串中所有的字符能构成最长回文串，比如abccccdd->dccaccd)
只需统计字符出现的此时，双数才能组成回文，中间一个位置可以一个数单独出现

验证回文串

最长回文子串(暴力分别以每个元素为中心往两边扩散，计算有偶数长度的最大回文子串和基数最大的回文子串，经典的马拉车解法)

最长回文子序列(bbbb是字符串bbbab的自序列但是不是子串)

字符串的排序(一个字符串是否包含另一个字符串的排序，第一个字符串的排序是第二个字符串的子串): 两个指针位置相差就是第一个数组的长度，然后第一个遇到字符后在表中记录-1，第二个遇到字符后在表中记录+1,所以当遍历到包含连续的排序的时候，最终表中记录值全部为0

打印字符串的全排序

第一个只出现一次的字符(最直接的办法,hash表统计各个字符出现的次数，遍历结束子再比遍历hash表得到第一个只出现一次的字符，也可以用int数组代替)

反转单词顺序

左旋转字符串(b是否是a旋转一部分的结果)

```java
public boolean solution(String s1,String s2){
	return s1.length() == s2.length() && (s1+s1).contains(s2);
}
```

反转字符串(将输入的字符串反转过来)

把字符串转换成整数(相当于实现Integer.valueOf(string)，前提是这个字符串能够转成整数，不合法时返回0)

正则表达式匹配('.'代表任意各一个字符.'*'代码上一个字符可以出现0次或者多次)

```java
    public boolean isMatch(String str,String p){
        //都为空则返回true
        if(p.isEmpty())
            return str.isEmpty();
        //p长度为1，则s长度必须为1且满则s,p首字符相同或者是p为'.'
        if(p.length() == 1)
            return (str.length() == 1)&&(str.charAt(0) == p.charAt(0)||p.charAt(0) == '.');
        //当p第二个字符不是'*'时
        if(p.charAt(1) != '*'){
            //s为空则直接返回false
            if(str.isEmpty()) return false;
            //判断首字符是否相同(p首字符为.或者s和p首字符相同)且都从第二个字符进行递归判断
            return (str.charAt(0) == p.charAt(0)||p.charAt(0) == '.') && 
                    isMatch(str.substring(1),p.substring(1));
        }
        //当p第二个字符是'*'时
        //s不为空且和s和p的首字符匹配(首字符直接相等或者p首字符为.)，递归调用匹配s和去掉前两个字符的p，相当于把*看作是0
        //如果匹配则直接返回true,否则将s去掉首字符跟去掉前两个字符的p比较
        while(!str.isEmpty()&&(p.charAt(0) == str.charAt(0)|| p.charAt(0) == '.')){
            //即考虑*代表0次的情况
            if(isMatch(str,p.substring(2))) return true;
            //即考虑*不代表0的情况,让s去掉首字符接着跟p比较，上面p.substring(2)不会改变p的值
            str = str.substring(1);
        }
        //上面都行不通之后，这时候str已经去掉前面的一些字符了，然后接着跟*代表0的情况比较
        return  isMatch(str,p.substring(2));
    }
```

表示数值的字符串

```java
public class Solution {
	public boolean isNumeric(char[] str) {
		int len = str.length;
		//标志位：是否出现过+/-符号，是否出现过小数点，是否出现e/E
		boolean sign = false, decimal = false, hasE = false;
		for(int i = 0; i < len; i++){
			if(str[i] == '+' || str[i] == '-'){
				if(!sign && i > 0 && str[i-1] != 'e' && str[i-1] != 'E')
					return false;
				if(sign && str[i-1] != 'e' && str[i-1] != 'E')
					return false;
				sign = true;
			}else if(str[i] == 'e' || str[i] == 'E'){
				if(i == len - 1)
					return false;
				if(hasE)
					return false;
				hasE = true;
			}else if(str[i] == '.'){
				if(hasE || decimal)
					return false;
				decimal = true;
			}else if(str[i] < '0' || str[i] > '9')
				return false;
		}
		return true;
	}
}
```

字符流中第一个不重复的字符(注意是字符流不是字符串)
用hash表来记录每个字符出现的次数，并且用另一个字符串s来保存字符流中字符出现的顺序



# LeetCode

## 双指针

<!-- GFM-TOC -->

* [有序数组的 Two Sum](#有序数组的-two-sum)
* [两数平方和](#两数平方和)
* [反转字符串中的元音字符](#反转字符串中的元音字符)
* [回文字符串](#回文字符串)
* [归并两个有序数组](#归并两个有序数组)
* [判断链表是否存在环](#判断链表是否存在环)
* [最长子序列](#最长子序列)
  <!-- GFM-TOC -->


双指针主要用于遍历数组，两个指针指向不同的元素，从而协同完成任务。

### 有序数组的 Two Sum

[Leetcode ：167. Two Sum II - Input array is sorted (Easy)](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/)

```html
Input: numbers={2, 7, 11, 15}, target=9
Output: index1=1, index2=2
```

题目描述：在有序数组中找出两个数，使它们的和为 target。

使用双指针，一个指针指向值较小的元素，一个指针指向值较大的元素。指向较小元素的指针从头向尾遍历，指向较大元素的指针从尾向头遍历。

- 如果两个指针指向元素的和 sum == target，那么得到要求的结果；
- 如果 sum > target，移动较大的元素，使 sum 变小一些；
- ### 如果 sum < target，移动较小的元素，使 sum 变大一些。

```java
public int[] twoSum(int[] numbers, int target) {
    int i = 0, j = numbers.length - 1;
    while (i < j) {
        int sum = numbers[i] + numbers[j];
        if (sum == target) {
            return new int[]{i + 1, j + 1};
        } else if (sum < target) {
            i++;
        } else {
            j--;
        }
    }
    return null;
}
```

### 两数平方和

[633. Sum of Square Numbers (Easy)](https://leetcode.com/problems/sum-of-square-numbers/description/)

```html
Input: 5
Output: True
Explanation: 1 * 1 + 2 * 2 = 5
```

题目描述：判断一个数是否为两个数的平方和。

```java
public boolean judgeSquareSum(int c) {
    int i = 0, j = (int) Math.sqrt(c);
    while (i <= j) {
        int powSum = i * i + j * j;
        if (powSum == c) {
            return true;
        } else if (powSum > c) {
            j--;
        } else {
            i++;
        }
    }
    return false;
}
```

### 反转字符串中的元音字符

[345. Reverse Vowels of a String (Easy)](https://leetcode.com/problems/reverse-vowels-of-a-string/description/)

```html
Given s = "leetcode", return "leotcede".
```

使用双指针指向待反转的两个元音字符，一个指针从头向尾遍历，一个指针从尾到头遍历。

```java
private final static HashSet<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));

public String reverseVowels(String s) {
    int i = 0, j = s.length() - 1;
    char[] result = new char[s.length()];
    while (i <= j) {
        char ci = s.charAt(i);
        char cj = s.charAt(j);
        if (!vowels.contains(ci)) {
            result[i++] = ci;
        } else if (!vowels.contains(cj)) {
            result[j--] = cj;
        } else {
            result[i++] = cj;
            result[j--] = ci;
        }
    }
    return new String(result);
}
```

### 回文字符串

[680. Valid Palindrome II (Easy)](https://leetcode.com/problems/valid-palindrome-ii/description/)

```html
Input: "abca"
Output: True
Explanation: You could delete the character 'c'.
```

题目描述：可以删除一个字符，判断是否能构成回文字符串。

```java
public boolean validPalindrome(String s) {
    int i = -1, j = s.length();
    while (++i < --j) {
        if (s.charAt(i) != s.charAt(j)) {
            return isPalindrome(s, i, j - 1) || isPalindrome(s, i + 1, j);
        }
    }
    return true;
}

private boolean isPalindrome(String s, int i, int j) {
    while (i < j) {
        if (s.charAt(i++) != s.charAt(j--)) {
            return false;
        }
    }
    return true;
}
```

### 归并两个有序数组(从尾向头)

[88. Merge Sorted Array (Easy)](https://leetcode.com/problems/merge-sorted-array/description/)

```html
Input:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3

Output: [1,2,2,3,5,6]
```

题目描述：把归并结果存到第一个数组上。

需要从尾开始遍历，否则在 nums1 上归并得到的值会覆盖还未进行归并比较的值。

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int index1 = m - 1, index2 = n - 1;
    int indexMerge = m + n - 1;
    while (index1 >= 0 || index2 >= 0) {
        if (index1 < 0) {
            nums1[indexMerge--] = nums2[index2--];
        } else if (index2 < 0) {
            nums1[indexMerge--] = nums1[index1--];
        } else if (nums1[index1] > nums2[index2]) {
            nums1[indexMerge--] = nums1[index1--];
        } else {
            nums1[indexMerge--] = nums2[index2--];
        }
    }
}
```

### 判断链表是否存在环

[141. Linked List Cycle (Easy)](https://leetcode.com/problems/linked-list-cycle/description/)

使用双指针，一个指针每次移动一个节点，一个指针每次移动两个节点，如果存在环，那么这两个指针一定会相遇。

```java
public boolean hasCycle(ListNode head) {
    if (head == null) {
        return false;
    }
    ListNode l1 = head, l2 = head.next;
    while (l1 != null && l2 != null && l2.next != null) {
        if (l1 == l2) {
            return true;
        }
        l1 = l1.next;
        l2 = l2.next.next;
    }
    return false;
}
```

### 最长子序列

[524. Longest Word in Dictionary through Deleting (Medium)](https://leetcode.com/problems/longest-word-in-dictionary-through-deleting/description/)

```
Input:
s = "abpcplea", d = ["ale","apple","monkey","plea"]

Output:
"apple"
```

题目描述：删除 s 中的一些字符，使得它构成字符串列表 d 中的一个字符串，找出能构成的最长字符串。如果有多个相同长度的结果，返回字典序的最小字符串。

```java
public String findLongestWord(String s, List<String> d) {
    String longestWord = "";
    for (String target : d) {
        int l1 = longestWord.length(), l2 = target.length();
        if (l1 > l2 || (l1 == l2 && longestWord.compareTo(target) < 0)) {
            continue;
        }
        if (isValid(s, target)) {
            longestWord = target;
        }
    }
    return longestWord;
}

private boolean isValid(String s, String target) {
    int i = 0, j = 0;
    while (i < s.length() && j < target.length()) {
        if (s.charAt(i) == target.charAt(j)) {
            j++;
        }
        i++;
    }
    return j == target.length();
}
```



## 排序问题

* [快速选择](#快速选择)
* [堆排序](#堆排序)
    * [Kth Element](#kth-element)
* [桶排序](#桶排序)
    * [出现频率最多的 k 个数](#出现频率最多的-k-个数)
    * [按照字符出现次数对字符串排序](#按照字符出现次数对字符串排序)
* [荷兰国旗问题](#荷兰国旗问题)
    * [按颜色进行排序](#按颜色进行排序)



### 快速选择

用于求解  **Kth Element**  问题，使用快速排序的 partition() 进行实现。

需要先打乱数组，否则最坏情况下时间复杂度为 O(N<sup>2</sup>)。

### 堆排序

用于求解  **TopK Elements**  问题，通过维护一个大小为 K 的堆，堆中的元素就是 TopK Elements。

堆排序也可以用于求解 Kth Element 问题，堆顶元素就是 Kth Element。

快速选择也可以求解 TopK Elements 问题，因为找到 Kth Element 之后，再遍历一次数组，所有小于等于 Kth Element 的元素都是 TopK Elements。

可以看到，快速选择和堆排序都可以求解 Kth Element 和 TopK Elements 问题。

### Kth Element

[215. Kth Largest Element in an Array (Medium)](https://leetcode.com/problems/kth-largest-element-in-an-array/description/)

题目描述：找到第 k 大的元素。

**排序** ：时间复杂度 O(NlogN)，空间复杂度 O(1)

```java
public int findKthLargest(int[] nums, int k) {
    Arrays.sort(nums);
    return nums[nums.length - k];
}
```

**堆排序** ：时间复杂度 O(NlogK)，空间复杂度 O(K)。

每次poll出去的都是最小的值，总共弹出了len-k个小元素出去，堆顶就是最大的k个元素里面的最小的元素即第k大的元素

```java
public int findKthLargest(int[] nums, int k) {
    PriorityQueue<Integer> pq = new PriorityQueue<>(); // 小顶堆
    for (int val : nums) {
        pq.add(val);
        if (pq.size() > k)  // 维护堆的大小为 K
            pq.poll();
    }
    return pq.peek();
}
```

**快速选择** ：时间复杂度 O(N)，空间复杂度 O(1)

```java
public int findKthLargest(int[] nums, int k) {
    k = nums.length - k;
    int l = 0, h = nums.length - 1;
    while (l < h) {
        int j = partition(nums, l, h);
        if (j == k) {
            break;
        } else if (j < k) {
            l = j + 1;
        } else {
            h = j - 1;
        }
    }
    return nums[k];
}

private int partition(int[] a, int l, int h) {
    int i = l, j = h + 1;
    while (true) {
        while (a[++i] < a[l] && i < h) ;
        while (a[--j] > a[l] && j > l) ;
        if (i >= j) {
            break;
        }
        swap(a, i, j);
    }
    swap(a, l, j);
    return j;
}

private void swap(int[] a, int i, int j) {
    int t = a[i];
    a[i] = a[j];
    a[j] = t;
}
```

### 桶排序

### 出现频率最多的 k 个数

[347. Top K Frequent Elements (Medium)](https://leetcode.com/problems/top-k-frequent-elements/description/)

```html
Given [1,1,1,2,2,3] and k = 2, return [1,2].
```

设置若干个桶，每个桶存储出现频率相同的数，并且桶的下标代表桶中数出现的频率，即第 i 个桶中存储的数出现的频率为 i。

把数都放到桶之后，从后向前遍历桶，最先得到的 k 个数就是出现频率最多的的 k 个数。

```java
public List<Integer> topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> frequencyForNum = new HashMap<>();
    for (int num : nums) {
        frequencyForNum.put(num, frequencyForNum.getOrDefault(num, 0) + 1);
    }
    List<Integer>[] buckets = new ArrayList[nums.length + 1];
    for (int key : frequencyForNum.keySet()) {
        int frequency = frequencyForNum.get(key);
        if (buckets[frequency] == null) {
            buckets[frequency] = new ArrayList<>();
        }
        buckets[frequency].add(key);
    }
    List<Integer> topK = new ArrayList<>();
    for (int i = buckets.length - 1; i >= 0 && topK.size() < k; i--) {
        if (buckets[i] == null) {
            continue;
        }
        if (buckets[i].size() <= (k - topK.size())) {
            topK.addAll(buckets[i]);
        } else {
            topK.addAll(buckets[i].subList(0, k - topK.size()));
        }
    }
    return topK;
}
```

### 按照字符出现次数对字符串排序

[451. Sort Characters By Frequency (Medium)](https://leetcode.com/problems/sort-characters-by-frequency/description/)

```html
Input:
"tree"

Output:
"eert"

Explanation:
'e' appears twice while 'r' and 't' both appear once.
So 'e' must appear before both 'r' and 't'. Therefore "eetr" is also a valid answer.
```

```java
public String frequencySort(String s) {
    Map<Character, Integer> frequencyForNum = new HashMap<>();
    for (char c : s.toCharArray())
        frequencyForNum.put(c, frequencyForNum.getOrDefault(c, 0) + 1);

    //出现次数为index，值为list[index]
    List<Character>[] frequencyBucket = new ArrayList[s.length() + 1];
    for (char c : frequencyForNum.keySet()) {
        int f = frequencyForNum.get(c);
        if (frequencyBucket[f] == null) {
            frequencyBucket[f] = new ArrayList<>();
        }
        frequencyBucket[f].add(c);
    }
    StringBuilder str = new StringBuilder();
    //从list的最后一个index(出现次数多)往前，遍历
    for (int i = frequencyBucket.length - 1; i >= 0; i--) {
        if (frequencyBucket[i] == null) {
            continue;
        }
        for (char c : frequencyBucket[i]) {
            for (int j = 0; j < i; j++) {
                str.append(c);
            }
        }
    }
    return str.toString();
}
```

### 荷兰国旗问题

荷兰国旗包含三种颜色：红、白、蓝。

有三种颜色的球，算法的目标是将这三种球按颜色顺序正确地排列。

它其实是三向切分快速排序的一种变种，在三向切分快速排序中，每次切分都将数组分成三个区间：小于切分元素、等于切分元素、大于切分元素，而该算法是将数组分成三个区间：等于红色、等于白色、等于蓝色。

按颜色进行排序

[75. Sort Colors (Medium)](https://leetcode.com/problems/sort-colors/description/)

```html
Input: [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
```

题目描述：只有 0/1/2 三种颜色。

```java
public void sortColors(int[] nums) {
    int zero = -1, one = 0, two = nums.length;
    while (one < two) {
        if (nums[one] == 0) {
            swap(nums, ++zero, one++);
        } else if (nums[one] == 2) {
            swap(nums, --two, one);
        } else {
            ++one;
        }
    }
}

private void swap(int[] nums, int i, int j) {
    int t = nums[i];
    nums[i] = nums[j];
    nums[j] = t;
}
```

## 贪心策略

<!-- GFM-TOC -->

* [分配饼干](#分配饼干)
* [不重叠的区间个数](#不重叠的区间个数)
* [投飞镖刺破气球](#投飞镖刺破气球)
* [根据身高和序号重组队列](#根据身高和序号重组队列)
* [分隔字符串使同种字符出现在一起](#分隔字符串使同种字符出现在一起)
* [种植花朵](#种植花朵)
* [判断是否为子序列](#判断是否为子序列)
* [修改一个数成为非递减数组](#修改一个数成为非递减数组)
* [股票的最大收益](#股票的最大收益)
* [子数组最大的和](#子数组最大的和)
* [买入和售出股票最大的收益](#买入和售出股票最大的收益)
  <!-- GFM-TOC -->


保证每次操作都是局部最优的，并且最后得到的结果是全局最优的。

### 分配饼干

[455. Assign Cookies (Easy)](https://leetcode.com/problems/assign-cookies/description/)

```html
Input: [1,2], [1,2,3]
Output: 2

Explanation: You have 2 children and 3 cookies. The greed factors of 2 children are 1, 2.
You have 3 cookies and their sizes are big enough to gratify all of the children,
You need to output 2.
```

题目描述：每个孩子都有一个满足度，每个饼干都有一个大小，只有饼干的大小大于等于一个孩子的满足度，该孩子才会获得满足。求解最多可以获得满足的孩子数量。

给一个孩子的饼干应当尽量小又能满足该孩子，这样大饼干就能拿来给满足度比较大的孩子。因为最小的孩子最容易得到满足，所以先满足最小的孩子。

证明：假设在某次选择中，贪心策略选择给当前满足度最小的孩子分配第 m 个饼干，第 m 个饼干为可以满足该孩子的最小饼干。假设存在一种最优策略，给该孩子分配第 n 个饼干，并且 m < n。我们可以发现，经过这一轮分配，贪心策略分配后剩下的饼干一定有一个比最优策略来得大。因此在后续的分配中，贪心策略一定能满足更多的孩子。也就是说不存在比贪心策略更优的策略，即贪心策略就是最优策略。

```java
public int findContentChildren(int[] g, int[] s) {
    Arrays.sort(g);
    Arrays.sort(s);
    int gi = 0, si = 0;
    while (gi < g.length && si < s.length) {
        if (g[gi] <= s[si]) {
            gi++;
        }
        si++;
    }
    return gi;
}
```

### 不重叠的区间个数

[435. Non-overlapping Intervals (Medium)](https://leetcode.com/problems/non-overlapping-intervals/description/)

```html
Input: [ [1,2], [1,2], [1,2] ]

Output: 2

Explanation: You need to remove two [1,2] to make the rest of intervals non-overlapping.
```

```html
Input: [ [1,2], [2,3] ]

Output: 0

Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
```

题目描述：计算让一组区间不重叠所需要移除的区间个数。

先计算最多能组成的不重叠区间个数，然后用区间总个数减去不重叠区间的个数。

在每次选择中，区间的结尾最为重要，选择的区间结尾越小，留给后面的区间的空间越大，那么后面能够选择的区间个数也就越大。

按区间的结尾进行排序，每次选择结尾最小，并且和前一个区间不重叠的区间。

```java
public int eraseOverlapIntervals(Interval[] intervals) {
    if (intervals.length == 0) {
        return 0;
    }
    Arrays.sort(intervals, Comparator.comparingInt(o -> o.end));
    int cnt = 1;
    int end = intervals[0].end;
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i].start < end) {
            continue;
        }
        end = intervals[i].end;
        cnt++;
    }
    return intervals.length - cnt;
}
```

使用 lambda 表示式创建 Comparator 会导致算法运行时间过长，如果注重运行时间，可以修改为普通创建 Comparator 语句：

```java
Arrays.sort(intervals, new Comparator<Interval>() {
    @Override
    public int compare(Interval o1, Interval o2) {
        return o1.end - o2.end;
    }
});
```

### 投飞镖刺破气球(同样是开会时间的问题)

[452. Minimum Number of Arrows to Burst Balloons (Medium)](https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/description/)

```
Input:
[[10,16], [2,8], [1,6], [7,12]]

Output:
2
```

题目描述：气球在一个水平数轴上摆放，可以重叠，飞镖垂直投向坐标轴，使得路径上的气球都会刺破。求解最小的投飞镖次数使所有气球都被刺破。

也是计算不重叠的区间个数，不过和 Non-overlapping Intervals 的区别在于，[1, 2] 和 [2, 3] 在本题中算是重叠区间。

```java
public int findMinArrowShots(int[][] points) {
    if (points.length == 0) {
        return 0;
    }
    Arrays.sort(points, Comparator.comparingInt(o -> o[1]));
    int cnt = 1, end = points[0][1];
    for (int i = 1; i < points.length; i++) {
        if (points[i][0] <= end) {
            continue;
        }
        cnt++;
        end = points[i][1];
    }
    return cnt;
}
```

### 根据身高和序号重组队列

[406. Queue Reconstruction by Height(Medium)](https://leetcode.com/problems/queue-reconstruction-by-height/description/)

```html
Input:
[[7,0], [4,4], [7,1], [5,0], [6,1], [5,2]]

Output:
[[5,0], [7,0], [5,2], [6,1], [4,4], [7,1]]
```

题目描述：一个学生用两个分量 (h, k) 描述，h 表示身高，k 表示排在前面的有 k 个学生的身高比他高或者和他一样高。

为了使插入操作不影响后续的操作，身高较高的学生应该先做插入操作，否则身高较小的学生原先正确插入的第 k 个位置可能会变成第 k+1 个位置。

身高降序、k 值升序，然后按排好序的顺序插入队列的第 k 个位置中。

```java
public int[][] reconstructQueue(int[][] people) {
    if (people == null || people.length == 0 || people[0].length == 0) {
        return new int[0][0];
    }
    Arrays.sort(people, (a, b) -> (a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]));
    List<int[]> queue = new ArrayList<>();
    for (int[] p : people) {
        queue.add(p[1], p);
    }
    return queue.toArray(new int[queue.size()][]);
}
```

### 分隔字符串使同种字符出现在一起

[763. Partition Labels (Medium)](https://leetcode.com/problems/partition-labels/description/)

```html
Input: S = "ababcbacadefegdehijhklij"
Output: [9,7,8]
Explanation:
The partition is "ababcbaca", "defegde", "hijhklij".
This is a partition so that each letter appears in at most one part.
A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
```

```java
public List<Integer> partitionLabels(String S) {
    int[] lastIndexsOfChar = new int[26];
    for (int i = 0; i < S.length(); i++) {
        lastIndexsOfChar[char2Index(S.charAt(i))] = i;
    }
    List<Integer> partitions = new ArrayList<>();
    int firstIndex = 0;
    while (firstIndex < S.length()) {
        int lastIndex = firstIndex;
        for (int i = firstIndex; i < S.length() && i <= lastIndex; i++) {
            int index = lastIndexsOfChar[char2Index(S.charAt(i))];
            if (index > lastIndex) {
                lastIndex = index;
            }
        }
        partitions.add(lastIndex - firstIndex + 1);
        firstIndex = lastIndex + 1;
    }
    return partitions;
}

private int char2Index(char c) {
    return c - 'a';
}
```


### 种植花朵

[605. Can Place Flowers (Easy)](https://leetcode.com/problems/can-place-flowers/description/)

```html
Input: flowerbed = [1,0,0,0,1], n = 1
Output: True
```

题目描述：花朵之间至少需要一个单位的间隔，求解是否能种下 n 朵花。

```java
public boolean canPlaceFlowers(int[] flowerbed, int n) {
    int len = flowerbed.length;
    int cnt = 0;
    for (int i = 0; i < len && cnt < n; i++) {
        if (flowerbed[i] == 1) {
            continue;
        }
        int pre = i == 0 ? 0 : flowerbed[i - 1];
        int next = i == len - 1 ? 0 : flowerbed[i + 1];
        if (pre == 0 && next == 0) {
            cnt++;
            flowerbed[i] = 1;
        }
    }
    return cnt >= n;
}
```

### 判断是否为子序列

[392. Is Subsequence (Medium)](https://leetcode.com/problems/is-subsequence/description/)

```html
s = "abc", t = "ahbgdc"
Return true.
```

```java
public boolean isSubsequence(String s, String t) {
    int index = -1;
    for (char c : s.toCharArray()) {
        index = t.indexOf(c, index + 1);
        if (index == -1) {
            return false;
        }
    }
    return true;
}
```

### 修改一个数成为非递减数组

[665. Non-decreasing Array (Easy)](https://leetcode.com/problems/non-decreasing-array/description/)

```html
Input: [4,2,3]
Output: True
Explanation: You could modify the first 4 to 1 to get a non-decreasing array.
```

题目描述：判断一个数组能不能只修改一个数就成为非递减数组。

在出现 nums[i] < nums[i - 1] 时，需要考虑的是应该修改数组的哪个数，使得本次修改能使 i 之前的数组成为非递减数组，并且  **不影响后续的操作** 。优先考虑令 nums[i - 1] = nums[i]，因为如果修改 nums[i] = nums[i - 1] 的话，那么 nums[i] 这个数会变大，就有可能比 nums[i + 1] 大，从而影响了后续操作。还有一个比较特别的情况就是 nums[i] < nums[i - 2]，只修改 nums[i - 1] = nums[i] 不能使数组成为非递减数组，只能修改 nums[i] = nums[i - 1]。

```java
public boolean checkPossibility(int[] nums) {
    int cnt = 0;
    for (int i = 1; i < nums.length && cnt < 2; i++) {
        if (nums[i] >= nums[i - 1]) {
            continue;
        }
        cnt++;
        if (i - 2 >= 0 && nums[i - 2] > nums[i]) {
            nums[i] = nums[i - 1];
        } else {
            nums[i - 1] = nums[i];
        }
    }
    return cnt <= 1;
}
```

### 股票的最大收益

[122. Best Time to Buy and Sell Stock II (Easy)](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/description/)

题目描述：一次股票交易包含买入和卖出，多个交易之间不能交叉进行。

对于 [a, b, c, d]，如果有 a <= b <= c <= d ，那么最大收益为 d - a。而 d - a = (d - c) + (c - b) + (b - a) ，因此当访问到一个 prices[i] 且 prices[i] - prices[i-1] > 0，那么就把 prices[i] - prices[i-1] 添加到收益中，从而在局部最优的情况下也保证全局最优。

```java
public int maxProfit(int[] prices) {
    int profit = 0;
    for (int i = 1; i < prices.length; i++) {
        if (prices[i] > prices[i - 1]) {
            profit += (prices[i] - prices[i - 1]);
        }
    }
    return profit;
}
```

### 子数组最大的和

[53. Maximum Subarray (Easy)](https://leetcode.com/problems/maximum-subarray/description/)

```html
For example, given the array [-2,1,-3,4,-1,2,1,-5,4],
the contiguous subarray [4,-1,2,1] has the largest sum = 6.
```

```java
public int maxSubArray(int[] nums) {
    if (nums == null || nums.length == 0) {
        return 0;
    }
    int preSum = nums[0];
    int maxSum = preSum;
    for (int i = 1; i < nums.length; i++) {
        preSum = preSum > 0 ? preSum + nums[i] : nums[i];
        maxSum = Math.max(maxSum, preSum);
    }
    return maxSum;
}
```

### 买入和售出股票最大的收益

[121. Best Time to Buy and Sell Stock (Easy)](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/)

题目描述：只进行一次交易。

只要记录前面的最小价格，将这个最小价格作为买入价格，然后将当前的价格作为售出价格，查看当前收益是不是最大收益。

```java
public int maxProfit(int[] prices) {
    int n = prices.length;
    if (n == 0) return 0;
    int soFarMin = prices[0];
    int max = 0;
    for (int i = 1; i < n; i++) {
        if (soFarMin > prices[i]) soFarMin = prices[i];
        else max = Math.max(max, prices[i] - soFarMin);
    }
    return max;
}
```

## 二分查找

<!-- GFM-TOC -->

* [原理](#原理)
    * [1. 正常实现](#1-正常实现)
    * [2. 时间复杂度](#2-时间复杂度)
    * [3. m 计算](#3-m-计算)
    * [4. 返回值](#4-返回值)
    * [5. 变种](#5-变种)
* [例题](#例题)
    * [1. 求开方](#1-求开方)
    * [2. 大于给定元素的最小元素](#2-大于给定元素的最小元素)
    * [3. 有序数组的 Single Element](#3-有序数组的-single-element)
    * [4. 第一个错误的版本](#4-第一个错误的版本)
    * [5. 旋转数组的最小数字](#5-旋转数组的最小数字)
    * [6. 查找区间](#6-查找区间)
      <!-- GFM-TOC -->

### 原理

### 1. 正常实现

```java
public int binarySearch(int[] nums, int key) {
    int l = 0, h = nums.length - 1;
    while (l <= h) {
        int m = l + (h - l) / 2;
        if (nums[m] == key) {
            return m;
        } else if (nums[m] > key) {
            h = m - 1;
        } else {
            l = m + 1;
        }
    }
    return -1;
}
```

### 2. 时间复杂度

二分查找也称为折半查找，每次都能将查找区间减半，这种折半特性的算法时间复杂度为 O(logN)。

### 3. m 计算

有两种计算中值 m 的方式：

- m = (l + h) / 2
- m = l + (h - l) / 2

l + h 可能出现加法溢出，最好使用第二种方式。

### 4. 返回值

循环退出时如果仍然没有查找到 key，那么表示查找失败。可以有两种返回值：

- -1：以一个错误码表示没有查找到 key
- l：将 key 插入到 nums 中的正确位置

### 5. 变种

二分查找可以有很多变种，变种实现要注意边界值的判断。例如在一个有重复元素的数组中查找 key 的最左位置的实现如下：

```java
public int binarySearch(int[] nums, int key) {
    int l = 0, h = nums.length - 1;
    while (l < h) {
        int m = l + (h - l) / 2;
        if (nums[m] >= key) {
            h = m;
        } else {
            l = m + 1;
        }
    }
    return l;
}
```

该实现和正常实现有以下不同：

- 循环条件为 l < h
- h 的赋值表达式为 h = m
- 最后返回 l 而不是 -1

在 nums[m] >= key 的情况下，可以推导出最左 key 位于 [l, m] 区间中，这是一个闭区间。h 的赋值表达式为 h = m，因为 m 位置也可能是解。

在 h 的赋值表达式为 h = mid 的情况下，如果循环条件为 l <= h，那么会出现循环无法退出的情况，因此循环条件只能是 l < h。以下演示了循环条件为 l <= h 时循环无法退出的情况：

```text
nums = {0, 1, 2}, key = 1
l   m   h
0   1   2  nums[m] >= key
0   0   1  nums[m] < key
1   1   1  nums[m] >= key
1   1   1  nums[m] >= key
...
```

当循环体退出时，不表示没有查找到 key，因此最后返回的结果不应该为 -1。为了验证有没有查找到，需要在调用端判断一下返回位置上的值和 key 是否相等。

### 例题

### 1. 求开方

[69. Sqrt(x) (Easy)](https://leetcode.com/problems/sqrtx/description/)

```html
Input: 4
Output: 2

Input: 8
Output: 2
Explanation: The square root of 8 is 2.82842..., and since we want to return an integer, the decimal part will be truncated.
```

一个数 x 的开方 sqrt 一定在 0 \~ x 之间，并且满足 sqrt == x / sqrt。可以利用二分查找在 0 \~ x 之间查找 sqrt。

对于 x = 8，它的开方是 2.82842...，最后应该返回 2 而不是 3。在循环条件为 l <= h 并且循环退出时，h 总是比 l 小 1，也就是说 h = 2，l = 3，因此最后的返回值应该为 h 而不是 l。

```java
public int mySqrt(int x) {
    if (x <= 1) {
        return x;
    }
    int l = 1, h = x;
    while (l <= h) {
        int mid = l + (h - l) / 2;
        int sqrt = x / mid;
        if (sqrt == mid) {
            return mid;
        } else if (mid > sqrt) {
            h = mid - 1;
        } else {
            l = mid + 1;
        }
    }
    return h;
}
```

### 2. 大于给定元素的最小元素

[744. Find Smallest Letter Greater Than Target (Easy)](https://leetcode.com/problems/find-smallest-letter-greater-than-target/description/)

```html
Input:
letters = ["c", "f", "j"]
target = "d"
Output: "f"

Input:
letters = ["c", "f", "j"]
target = "k"
Output: "c"
```

题目描述：给定一个有序的字符数组 letters 和一个字符 target，要求找出 letters 中大于 target 的最小字符，如果找不到就返回第 1 个字符。

```java
public char nextGreatestLetter(char[] letters, char target) {
    int n = letters.length;
    int l = 0, h = n - 1;
    while (l <= h) {
        int m = l + (h - l) / 2;
        if (letters[m] <= target) {
            l = m + 1;
        } else {
            h = m - 1;
        }
    }
    return l < n ? letters[l] : letters[0];
}
```

### 3. 有序数组的 Single Element

[540. Single Element in a Sorted Array (Medium)](https://leetcode.com/problems/single-element-in-a-sorted-array/description/)

```html
Input: [1, 1, 2, 3, 3, 4, 4, 8, 8]
Output: 2
```

题目描述：一个有序数组只有一个数不出现两次，找出这个数。要求以 O(logN) 时间复杂度进行求解。

令 index 为 Single Element 在数组中的位置。如果 m 为偶数，并且 m + 1 < index，那么 nums[m] == nums[m + 1]；m + 1 >= index，那么 nums[m] != nums[m + 1]。

从上面的规律可以知道，如果 nums[m] == nums[m + 1]，那么 index 所在的数组位置为 [m + 2, h]，此时令 l = m + 2；如果 nums[m] != nums[m + 1]，那么 index 所在的数组位置为 [l, m]，此时令 h = m。

因为 h 的赋值表达式为 h = m，那么循环条件也就只能使用 l < h 这种形式。

```java
public int singleNonDuplicate(int[] nums) {
    int l = 0, h = nums.length - 1;
    while (l < h) {
        int m = l + (h - l) / 2;
        if (m % 2 == 1) {
            m--;   // 保证 l/h/m 都在偶数位，使得查找区间大小一直都是奇数
        }
        if (nums[m] == nums[m + 1]) {
            l = m + 2;
        } else {
            h = m;
        }
    }
    return nums[l];
}
```

### 4. 第一个错误的版本

[278. First Bad Version (Easy)](https://leetcode.com/problems/first-bad-version/description/)

题目描述：给定一个元素 n 代表有 [1, 2, ..., n] 版本，可以调用 isBadVersion(int x) 知道某个版本是否错误，要求找到第一个错误的版本。

如果第 m 个版本出错，则表示第一个错误的版本在 [l, m] 之间，令 h = m；否则第一个错误的版本在 [m + 1, h] 之间，令 l = m + 1。

因为 h 的赋值表达式为 h = m，因此循环条件为 l < h。

```java
public int firstBadVersion(int n) {
    int l = 1, h = n;
    while (l < h) {
        int mid = l + (h - l) / 2;
        if (isBadVersion(mid)) {
            h = mid;
        } else {
            l = mid + 1;
        }
    }
    return l;
}
```

### 5. 旋转数组的最小数字

[153. Find Minimum in Rotated Sorted Array (Medium)](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/description/)

```html
Input: [3,4,5,1,2],
Output: 1
```

```java
public int findMin(int[] nums) {
    int l = 0, h = nums.length - 1;
    while (l < h) {
        int m = l + (h - l) / 2;
        if (nums[m] <= nums[h]) {
            h = m;
        } else {
            l = m + 1;
        }
    }
    return nums[l];
}
```

### 6. 查找区间

[34. Search for a Range (Medium)](https://leetcode.com/problems/search-for-a-range/description/)

```html
Input: nums = [5,7,7,8,8,10], target = 8
Output: [3,4]

Input: nums = [5,7,7,8,8,10], target = 6
Output: [-1,-1]
```

```java
public int[] searchRange(int[] nums, int target) {
    //target为原数的最左边界
    int first = binarySearch(nums, target);
    //target+1的最左边界 - 1
    int last = binarySearch(nums, target + 1) - 1;
    if (first == nums.length || nums[first] != target) {
        return new int[]{-1, -1};
    } else {
        return new int[]{first, Math.max(first, last)};
    }
}
private int binarySearch(int[] nums, int target) {
    int l = 0, h = nums.length; // 注意 h 的初始值
    while (l < h) {
        int m = l + (h - l) / 2;
        if (nums[m] >= target) {
            h = m;
        } else {
            l = m + 1;
        }
    }
    return l;
}
```





## 分治法

给表达式加括号

<!-- GFM-TOC -->

* [1. 给表达式加括号](#1-给表达式加括号)
  <!-- GFM-TOC -->


### 1. 给表达式加括号

[241. Different Ways to Add Parentheses (Medium)](https://leetcode.com/problems/different-ways-to-add-parentheses/description/)

```html
Input: "2-1-1".

((2-1)-1) = 0
(2-(1-1)) = 2

Output : [0, 2]
```

```java
public List<Integer> diffWaysToCompute(String input) {
    List<Integer> ways = new ArrayList<>();
    for (int i = 0; i < input.length(); i++) {
        char c = input.charAt(i);
        if (c == '+' || c == '-' || c == '*') {
            List<Integer> left = diffWaysToCompute(input.substring(0, i));
            List<Integer> right = diffWaysToCompute(input.substring(i + 1));
            //从左右list取出每个结果值分别进行运算得出所有可能的结果
            for (int l : left) {
                for (int r : right) {
                    switch (c) {
                        case '+':
                            ways.add(l + r);
                            break;
                        case '-':
                            ways.add(l - r);
                            break;
                        case '*':
                            ways.add(l * r);
                            break;
                    }
                }
            }
        }
    }
    if (ways.size() == 0) {
        ways.add(Integer.valueOf(input));
    }
    return ways;
}
```

## 搜索

## 动态规划

## 数学问题

## 链表问题

找出两个链表的交点

链表反转

归并两个有序的链表

从有序链表中删除重复节点

```java
//递归版本
public ListNode deleteDuplicates(ListNode head) {
    if(head == null || head.next == null) {
        return head;
    }
    head.next = deleteDuplicates(head.next);
    return head.val == head.next.val ? head.next:head;
}
```



删除链表的倒数第n个节点(快慢指针，快指针先走n步)

交换链表中的相邻节点(注意因为前两个交换位置了所pre的位置是指向l1而不是l2)

链表求和(记录进位和当前值)

回文链表(快慢指针来实现来到中点，后一半入栈然后依次比较，O(1)空间将后半部分反转然后比较最后将后面部分再反转回去)

分隔链表(创建几个链表头然后遍历链表挂到不通的后面最后将几个链表连在一起)

链表元素按奇偶聚集

## 树

### 递归做法

#### 树的高度

##### 平衡树

##### 两节点的最长路径

##### 反转树

##### 归并两个树

##### 判断路径和是否等于某一个数

##### 统计路径和等于某一个数的路径数量

##### 子树

##### 树的对称

##### 最小路径

##### 统计左叶子节点的和

##### 相同节点值得最大路径长度

##### 间隔遍历

##### 找出二叉树中第二小的节点



#### 层次遍历

##### 一棵树每层节点的平均数

##### 得到左下角的节点



#### 前中后序遍历

##### 非递归实现二叉树的前序遍历(栈实现)

##### 非递归实现二叉树的中序遍历(同样栈实现)

##### 非递归实现二叉树的后序遍历

​	左右根是根左右的左右打印顺序调换为根右左然后reverse就得到左右根



#### 二叉搜索树BST

##### 修剪二叉搜索树

##### 寻找二叉查找树的第k个元素(中序遍历的第k个节点)

##### 把二叉查找树每个节点的值都加上比它大的节点的值

##### 二叉查找树的最近公共祖先(同大同小在两边，一大一下在中间)

##### 二叉树的最近公共祖先

##### 数组构造二叉树结构

##### 

```java
public TreeNode arrayToBST(int arr[]) {
    return toBST(arr,0);
}
public TreeNode toBST(int arr[],int index) {
    if(left > right) {
        return null;
    }
    int value = arr[index];
    //建个节点
    TreeNode newNode = new TreeNode(value);
    //递归创建
    newNode.left = toBST(arr, index*2 + 1);
    newNode.right = toBST(arr, index*2 + 2);
    return newNode;
}
```



链表构造平衡二叉查找树

```java
//关键是如何找到中间的节点
public TreeNode sortedListToBST(ListNode head) {
    if(head == null) 
        return null;
    if(head.next == null)
        return new TreeNode(head.val);
    
    //找节点preMid,Mid,Mid.next，Mid是当前要创建节点的值，然后将链表断开两段
    ListNode preMid = preMid(head)l
   	ListNode mid = preMid.next;
    //断开
    preMid.next = null;
    TreeNode newNode = new TreeNode(mid.val);
    //递归到两边：head和mid.next
    newHead.left = sortedListToBST(head);
    newHead.right = sortedListToBST(mid.next);
    return newHead;
}

//找到中间节点前一个节点
public ListNode preMid(ListNode head) {
   	ListNode slow = head,fast = head.next;
    ListNode pre = head;
    while(fast != null && fast.next != null) {
        //pre每次指向慢指针
        pre = slow;
        slow = slow.next;
        fast = fast.next.next;
    }
    return pre;
}
```

在二叉查找树中寻找两个节点，使他们的和为一个给定值(中序遍历得到有序数组后进行two sum即可)

在二叉查找树中寻找两个节点只差的最小绝对值(同样利用中序遍历得到有序数组，最小绝对值肯定就是有序数组相邻两个元素的最小差值)

寻找二叉查找树中出现次数最多的值(同样还是利用中序遍历的过程)

```java
private int curCnt = 1;
private int maxCnt = 1;
private TreeNode preNode = null;

public int[] findMore(TreeNode node ){
    //这一步就是把list结果赋值到数组而已
    List<Integer> list = new ArrayList<>();
    inOrder(node,list);
    int[] ret = new int[list.size()];
    int index = 0;
    for(int num : list){
        ret[index++] = num;
    }
    return ret;
}

//出现最多次数的值可能不止一个所以要用数组来保存结果,在中序遍历的时候用list来记录
private void inOrder(TreeNode node, List<Integer> nums) {
    if(node == null)
        return;
    inOrder(node.left,nums);
    if(preNode != null){
        if(preNode.val == node.val) curCnt++;
        else curCnt = 1;
    }
    //次数大于当前最大的则清空list只保存当前这个元素
    if(curCnt > maxCnt){
        maxCnt = curCnt;
        nums.clear();
        nums.add(node.val);
    }else if(curCnt == maxCnt){
        //次数等于当前最大的则添加进list
        list.add(node.val);
    }
    //preNode是全局变量
    preNode = node;
    inOrder(node.right,nums);
}
```

Tire树(前缀树或者叫字典树)判断字符串是否存在或者具有某种字符串前缀的字符串



### 队列和栈

#### 用栈实现队列

#### 用队列实现栈

#### 最小值栈

#### 用栈实现括号匹配

```java
public boolean isValid(String s) {
    Stack<Character> stack = new Stack<>();
    for (char c : s.toCharArray()) {
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) {
                return false;
            }
            char cStack = stack.pop();
            boolean b1 = c == ')' && cStack != '(';
            boolean b2 = c == ']' && cStack != '[';
            boolean b3 = c == '}' && cStack != '{';
            if (b1 || b2 || b3) {
                return false;
            }
        }
    }
    return stack.isEmpty();
}
```



#### 数组中元素和下一个比它大的元素之间的距离

```java
//用栈保存遍历时的index,如果遍历的数比栈顶的元素大，说明栈顶元素(注意是栈顶元素不是当前遍历的元素)的下一个比它大的数就是当前元素，相当于每次求栈顶元素的下一个比它大的数，因为每个元素都是要入栈的
public int[] nextBigger(int[] arr) {
    int[] dist = new int[arr.length];
    Stack<Integer> stack = new LinkedList<>();
    for(int i = 0;i<arr.length;i++){
        //如果元素递减则全为0
        while(!stack.isEmpty() && arr[i] > arr[stack.peek()]){
            int preIndex = stack.pop();
            dist[preIndex] = i - preIndex;
        }
        stack.push(i);
    }
    return dist;
}
```



循环数组中比当前元素大的下一个元素

```java
//注意是旋转数组，并且求的不是距离而是下一个元素
public int[] nextBiggerInCircle(int[] arr){
    int[] dist = new int[arr.length];
    Arrays.fill(next,-1);
    Stack<Integer> stack = new LinkedList<>();
    int n = arr.length;
    for(int i = 0;i<arr.length*2;i++){
        //两倍的长度注意是取余
        int num = arr[i%n];
        while(!stack.isEmpty() && arr[i] > arr[stack.peek()]){
            int preIndex = stack.pop();
            dist[preIndex] = num;
        }
        if(i<n){
            //push只需要push一遍
         	stack.push(i)
        }
    }
    return dist;
}
```



### 哈希表

#### 数组中两个数的和为定值(用hashmap实现two sum)

#### 判断数组是否含有重复元素

#### 最长和谐序列 

​	和谐序列中的最大值和最小值相差正好是1，序列不一定是数组的连续元素

```java
public int findLHS(int[] nums) {
    Map<Integer,Integer> map = new HashMap<>();
    //记录每个元素出现的次数
    for(int num : nums){
        map.put(nums,map.getOrDefault(num,0)+1);
    }
    int longest = 0;
    //一次遍历每个单独元素看是否存在比它大1的数
    for(int num : map.keySet()) {
        if(map.containsKey(num+1)) {
            longest = Math.max(longest,map.get(num) + map.get(num + 1));
        }
    }
    return longest;
}
```

#### 最长连续序列

```java
//复用map来记录forward的长度，即从当前数开始向前的连续长度，比如当前数为8，且从8开始向前包含9，10,11则map.put(8,4)
public int longestConsecutive(int nums[]) {
    Map<Integet,Integer> map = new HashMap<>();
    //初始全都为1，不管重复不重复
    for(int num : nums){
        map.put(num,1);
    }
    //一次获取每个值能向前延伸的最长长度
    for(int num : nums){
        forward(map,num);
    }
    //返回最大的向前长度遍历map即可
    return maxCount(map);
}

public int forward(Map<Integer,Integer> map, int num) {
    if(!map.containsKey(num)){
        return 0;
    }
    //当前向前的长度,如果已经因为递归计算过了即值不为初始的1直接返回
    int count = map.get(num);
    if(count > 1){
        return count;
    }
    //递归得到当前元素向前的最长长度
    count = forward(map,num+1) + 1;
    //重新对num赋值
    map.put(num, count);
    return count;
}

//得到最大的向前长度即为最长序列长度
public int maxCount(Map<Integer,Integer> map) {
    int max = 0;
    for(int num : map.keySet()) {
        max = Math.max(map.get(num),max);
    }
    return max;
}
```





### 字符串

#### 字符串循环移位包含(只需判断s2是否在s1s1里面)

#### 字符串循环移位

​	(s1s1.substring(index,index+s1.length()))

#### 字符串中单词的反转

​	(将没个单词反转然后将整个字符串反转)

#### 两个字符串是否包含相同的字符

​	(int数组来记录出现次数，一个加一个减，最后如果有不为0的直接返回false)

#### 计算一组字符集合可以组成的回文字符串的最大长度

​	(用字符串去组成，记录每个字符的出现次数，偶数个的放两边，奇数个只能有一个即放中间)

```java
public int longestPalindrome(String s){
    int[] counts = new int[256];
    for(char c : s.toCharArray()) {
        counts[c]++;
    }
    int len = 0 ;
    for(int count: counts){
        len += (count/2)*2;
    }
    //还有数剩余可以再加一个放中间
    if(len < s.length){
        len++;
    }
    return len;
}
```

#### 字符串同构

​	(记录每个字符串上一次出现的位置如果出现位置相同是index不是具体值则为同构)

```java
public boolean isIsomorphic(String s, String t) {
    int[] preIndexOfS = new int[256];
    int[] preIndexOfT = new int[256];
    for (int i = 0; i < s.length(); i++) {
        char sc = s.charAt(i), tc = t.charAt(i);
        if (preIndexOfS[sc] != preIndexOfT[tc]) {
            return false;
        }
        preIndexOfS[sc] = i + 1;
        preIndexOfT[tc] = i + 1;
    }
    return true;
}
```

#### 回文子字符串个数

​	(从每个元素出发一次计算奇数长度和偶数长度的回文串)

```java
private count = 0;
public int countSubPalindrome(String s) {
    for(int i = 0; i<s.length();i++) {
        //基数长度
        extendSubString(s,i,i);
        //偶数长度
        extendSubString(s,i,i+1);
    }
    return count;
}

public extendSubString(String s, int start,int end){
    while(start>=0 && end<s.length() && s.charAt(start) == s.charAt(end)){
        count++;
        start--;
		end++;        
    }
}
```

#### 判断一个整数是否为回文串

​	(整数分为左右两部分，右边部分转置然后比较两部分是否相同)

```java
//将整数从右往左重构一边比如12341重构成14321，显然不等与12341所以不回文
public boolean isPalindromeNumber(int num) {
    if(num == 0 ){
        return true;
    }
    if(num < 0 || num % 10 == 0){
        return false;
    }
    
    int newNum = 0;
    //只需生成一般即可当进行判断
    while(num > right){
        right += right*10 + num%10;
        num /= 10;
    }
    //当偶数时应该是num == right当奇数时应该是num == right/10
    return num == right || num = right/10;
} 
```

#### 统计二进制字符串中连续1和连续0数量相同的子字符串个数

​	(注意是连续0和连续1,1001这样的不算)

```java
public int countBinarySubstring(String s) {
    int preLen = 0,curLen = 1,count = 0;
    for(int i = 1;i<s.length();i++) {
        if(s.chatAt(i) == s.charAt(i-1)){
            curLen++;
        }else{
            preLen = curLen;
            curLen = 1;
        }
        
        if(preLen >= curLen) {
            count++;
        }
    }
    return count;
}
```



### 数组和矩阵

#### 把数组中的0移到末尾

#### 改变矩阵维度

​	new[i] [j] = nums[index/n] [index%n]

#### 找出数组中最长的连续1

#### 有序矩阵查找

#### 有序矩阵的kth element

```java
//同样这里的mid并不是表示下标的，所有可能出现的值得中点
//当出现smallest~n之间的值出现小于k次而smallest~n+1之间的值出现的次数等于或者大于k次，则第k大的元素就是n+1
public int kthSmallsetInMatrix(int[][] matrix,int k) {
    int m = matrix.length, n = matrix[0].length;
    //left最小，right最大
    int left = matrix[0][0],right = matrix[m][n];
    while(left <= right) {
        int mid = left + (right-left)/2;
        int count = 0;
        for(i = 0 ;i<m;i++){
            for(j = 0;i<n && matrix[i][j]<= mid;j++){
                //并不是所有元素都会遍历一边当出现matrix[i][j]>mid就会停止
                count++;
            }
        }
        if(count<k) {
            left = mid + 1;
        }else{
            //当前个数已经等于或者大于k时,应该缩小，因为遍历所有元素值可能不止k个了
            right = mid - 1;
        }
    }
    return left;
}
```

#### 一个数组中元素在[1,n]之间，其中一个数被替换为另一个数，找出这重复的数和丢失的数

```java
//数组可能初始没有排序的
//通过交换数组元素是元素在正确的位置上
public int[] findErrorNums(int[] nums) {
    for(int i = 0;i<nums.length();i++) {
        //当前位置i的值num[i]应该等于i+1,如果不等看nums[i]本该属于的地方是不是就是nums[i]
        while(nums[i] != i+1 && nums[nums[i] - 1] != nums[i]){
            //都不满足则进行交换知道元素在它应该在的位置
            swap(nums,i,nums[i]-1);
        }
        for(int i = 0;i<nums.length();i++){
            if(nums[i] != i+1){
                return new int[]{nums[i],i+1};
            }
        }
    }
}
```

#### 找出数组中重复的数，数组值在[1,n]之间，数组长度为n+1

​	不能修改数组也不能使用额外空间，使用二分查找来做或者用双指针来做(思想很关键)

```java
//这里比较不是和nums[mid]比较而是直接和mid比较,求出来的mid不是代表index就是代表1~n中间的值而已
public int findDuplicate(int[] nums) {
    int l = 1,h = nums.length - 1;
    while(l <= h) {
        //最后会出现1~m正好出现m次，而1~m+1出现了m+2次那重复的就是m+1
        int mid = l +(h-l)/2;
        int cnt = 0;
        for(int i = 0;i<nums.length;i++){
            if(nums[i] <= mid) cnt++;
        }
        if(cnt >mid){
            h = mid -1;
        }else{
            l = mid +1;
        }
    }
    return l;
}

//双指针法

```

#### 数组相邻差值的个数

​	(数组元素为1~n的整数，要求构建数组，使得相邻元素的差值不相同的个数为k)

```java
//让前k+1个元素构建出k个不同的差值，序列为1,k+1,2,k,3,k-1,4,k-2,....k/2,k/2+1
//则差值为k,k-1,k-2,k-3,k-4,k-5...,1
public int[] constructArray(int n ,int k) {
    int ret = new int[n];
    ret[0] = 1;
    for(int i = 1,interval = k;i<=k;i++,interval--){
        //按规律构造
        ret[i] = i%2 == 1?ret[i-1]+interval : ret[i-1]-interval;
    }
    for(int i = k+1;i<n;i++) {
        ret[i] = i+1;
    }
    return ret;
}
```

#### 数组的度

​	(数组的度定义为元素出现的最高频率，找到最小的子数组，使子数组的度和原数组的度一样)

```java
//1. map1记录所以元素出现的次数
//2. map2记录所有元素第一次出现的index
//3. map3记录所有元素最后一次出现的index
//4. 遍历map1得到度
//5. 再次遍历数组，对于满足的度的元素求lastIndex - firstIndex + 1(即最短的子数组长度)，返回最短的长度
public int findShortestSubArray(int[] nums) {
    Map<Integer, Integer> numsCnt = new HashMap<>();
    Map<Integer, Integer> numsLastIndex = new HashMap<>();
    Map<Integer, Integer> numsFirstIndex = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
        int num = nums[i];
        numsCnt.put(num, numsCnt.getOrDefault(num, 0) + 1);
        numsLastIndex.put(num, i);
        if (!numsFirstIndex.containsKey(num)) {
            numsFirstIndex.put(num, i);
        }
    }
    int maxCnt = 0;
    for (int num : nums) {
        maxCnt = Math.max(maxCnt, numsCnt.get(num));
    }
    int ret = nums.length;
    for (int i = 0; i < nums.length; i++) {
        int num = nums[i];
        int cnt = numsCnt.get(num);
        if (cnt != maxCnt) continue;
        ret = Math.min(ret, numsLastIndex.get(num) - numsFirstIndex.get(num) + 1);
    }
    return ret;
}
```

#### 对角元素相等的矩阵

```java
//分别从第一行和第一列出发看是否对角线元素都相等
public boolean isToeplitzMatrix(int[][] matrix) {
    for (int i = 0; i < matrix[0].length; i++) {
        if (!check(matrix, matrix[0][i], 0, i)) {
            return false;
        }
    }
    for (int i = 0; i < matrix.length; i++) {
        if (!check(matrix, matrix[i][0], i, 0)) {
            return false;
        }
    }
    return true;
}
//递归判断对角线元素是否都相等，也可以不用递归
private boolean check(int[][] matrix, int expectValue, int row, int col) {
    if (row >= matrix.length || col >= matrix[0].length) {
        return true;
    }
    if (matrix[row][col] != expectValue) {
        return false;
    }
    return check(matrix, expectValue, row + 1, col + 1);
}
```



#### 嵌套数组

​	s[i]表示一个集合，结合的第一个元素是A[i],第二个元素为A[A[i]],如此嵌套下去，求最大的s[i]

```java
//从每个元素开始遍历并记录已经访问过的元素，数组值得范围应该在数组的长度区间内，不然会出现越界情况
public int arrayNesting(int[] nums) {
    int max = 0;
    for(int i = 0;i<nums.length;i++) {
        int count = 0;
        for(int j = i;nums[j] != -1;){
            count ++;
            int t = nums[j];
            //标记已经访问过，避免再次访问时出现循环访问,比如A[1] = 1，则再嵌套就是A[A[1]]=A[1] = 1会一直嵌套出现循环
            nums[j] = -1;
            j = t;
        }
        max = Math.max(max,count);
    }
    return max;
}
```



#### 分隔数组(分隔数组使对每一部分排序后整个数组就为有序)

```java
//数组使【0,1,2,3...arr.lenght-1】的全排序之一
//如果数组就在他原本就在的位置单独+1
public int maxPart(int[] arr){
    if(arr == null) 
        return 0;
    int count = 0;
    int right = arr[0];
    for(int i = 0;i<arr.length;i++){
        //已遍历部分的最大值正好等于当前的index,count++
        right = Math.max(right,arr[i]);
        if(right == i){
            count++;
        }
    }
    return count;
}

```

#### Jump range II

贪的是一个能到达的最远范围，我们遍历当前跳跃能到的所有位置，然后根据该位置上的跳力来预测下一步能跳到的最远距离，贪出一个最远的范围，一旦当这个范围到达末尾时，当前所用的步数一定是最小步数。

需要两个变量cur和pre分别来保存当前的能到达的最远位置和之前能到达的最远位置，只要cur未达到最后一个位置则循环继续，pre先赋值为cur的值，表示上一次循环后能到达的最远位置，如果当前位置i小于等于pre，说明还是在上一跳能到达的范围内，我们根据当前位置加跳力来更新cur，更新cur的方法是比较当前的cur和i + A[i]之中的较大值

```java
public class JumpRangeII {
    public static int lessStep(int[] arr) {
        int pre = 0,cur = 0, i = 0,n = arr.length;
        int step = 0;
        while(cur < n - 1) {
            step++;
            pre = cur;
            for(;i<=pre;i++){
                cur = Math.max(cur,arr[i] + i);
            }
            //cur没有更新过则说明不能跳到最后一个
            //这里一般不会出现
            if(cur == pre){
                return -1;
            }
        }
        return step;
    }

    public static void main(String[] args) {
        int arr[] = new int[]{2,1,1,1,3,1};
        int step = lessStep(arr);
        System.out.println(step);
    }
}

```









​	



















