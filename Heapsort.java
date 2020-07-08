/* Heapsort
 * ---
 * Author : - Danial Fitri Ghazali (272868)
 *          - Afirudin Jamilan (273829)
 *          - Shamil Shahimi (272850)
 * ---
 * Description: A heapsort implementation for
 *              Data Structure project
 * Usage: - Call constructor with the array to be sorted
 *        - Call sort() method to start sorting (returns the sorted array)
 *        - Call setArray(int[] arr) to set a new array to be sorted
 */

import java.util.Scanner;

class Heapsort {
    // Properties
    // ---
    // arr stores the int array to be sorted
    // length keeps the current workable length of array
    // - Later, the length will change during sort
    private int[] arr;
    private int length;
    
    private final boolean step;
    private final Scanner in;
    
    // Constructor
    // ---
    // Pass an int array to be sorted
    // Then the user can call sort to start sorting
    public Heapsort(int[] arr, boolean step) {
        this.arr = arr;
        this.step = step;
        length = arr.length;
        in = new Scanner(System.in);
        
        System.out.print("\nINITIAL ARRAY: ");
        print();
        
        if (step) {
            getInput();
        } else {
            System.out.println();
        }
    }
    
    // Call sort method to start the heapsort
    // First it builds a max heap tree by calling heapify
    // Then, it will start sorting
    public int[] sort() {
        // Build max heap
        // It will call heapify on the root of each subtree to
        // make turn them into max heap
        
        /* Example, imagine there's 5 elements.
           The completed tree will be like this:
           
                0
               / \
              1   2
             / \
            3   4
                        
           Starting from index 5 / 2 - 1 = 1, we will
           check index 1 and index 0 (both of which
           have child nodes).
           
           We call heapify on node 1 and 0, to turn
           them into a max heap.
           
           We work backwards so the whole tree will
           become max heap faster.
         */
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            System.out.println("Working on index " + i + " (" + arr[i] + ") - "
                + "Child nodes : index " + (i * 2 + 1) + " (" + arr[i * 2 + 1] + ") & index "
                + (i * 2 + 2) + " (" + arr[i * 2 + 2] + ")");
            
            heapify(i);
            System.out.print("Current array state: ");
            print();
            
            if (step) {
                getInput();
            } else {
                System.out.println();
            }
        }
        
        System.out.print("\nMAX HEAP: ");
        print();
        
        if (step) {
            getInput();
        } else {
            System.out.println();
        }
        
        // Due to max heap, the largest value should be
        // at the root of the tree.
        // First, we swap current largest value to the
        // current lowest index.
        // Then we perform heapify to the root of the tree
        // to bring the next largest value to the root.
        // Reduce length by 1 to avoid tampering with the
        // lower indices, which stored the sorted values.
        for (int i = --length; i > 0; i--) {
            System.out.println("Swapping " + arr[0] + " with " + arr[i]);
            swap(0, i);
            
            String text = "Working on index " + 0 + " (" + arr[0] + ") - "
                + "Child nodes ";
            
            if (0 * 2 + 1 < length)
                text +=  ": Left child index - " + (0 * 2 + 1) + " (" + arr[0 * 2 + 1] + ") ";
            if (0 * 2 + 2 < length)
                text += ": Right child index - " + (0 * 2 + 2) + " (" + arr[0 * 2 + 2] + ")";
            
            System.out.println(text);
            
            heapify(0);
            length--;
            System.out.print("Current array state: ");
            print();
            
            if (step) {
                getInput();
            } else {
                System.out.println();
            }
        }
        
        System.out.print("\nSORTED: ");
        print();
        System.out.println();
        
        return arr;
    }
    
    /* -- DURING HEAP BUILDING PROCESS --
    
       We used the heapify function to turn the tree
       into a max heap. To use it, call heapify and
       pass the root of the tree as args.
       
       Example, imagine the elements are
       [0:3, 1:1, 2:5, 3:2, 4:4] and we called
       heapify on index 1:
       
                3
               / \
             [1]  5
             / \
            2   4
            
        First, assume the largest value is at the root (1).
        The child nodes would be at index 3 & 4 (2, 4 respectively).
        
        Then, we check each child nodes whether the value is larger
        than the root and keep the index as largest.
        
        In this case:
        
                3
               / \
             [1]  5
             / \
            2  <4> -- Largest = 4
            
        Then, check whether the previous root is larger than other elements. If not,
        swap the value of new largest to the root using heapify(largest):
        
                3
               / \
              4   5
             / \
            2  [1] -- There might be subtree beneath the former root
            
        Finally, perform heapify one more time on this new root to
        make sure it was the largest on the subtree it was on (just in case).
        
        -- DURING SORT PROCESS --
        
        Imagine the heap was like this (5 was moved to lowest index during
        sort phase and current length is 4):
        
                1
               / \
              4   2
             / \
            3   5
            
        Using heapify at root, we can bring the next largest value to the
        root.
        
               [1]        [4]          4           4
               / \        / \         / \         / \
             <4>  2  =>  1   2  =>  [1]  2  =>  [3]  2
             / \        / \         / \         / \
            3   5      3   5      <3>  5       1   5
            
            heapify(0)             heapify(1) - NOTE: reduced length prevent us from
                                                      tampering the sorted values
            
        This allows us to continue the sort:
        
                1
               / \
              3   2
             / \
            4   5
     */
    private void heapify(int i) {
        // Assume largest is the root
        // Then get the child nodes
        int largest = i;
        int left = i * 2 + 1;
        int right = i * 2 + 2;

        // Guard the index to make sure it doesn't go out of bounds
        // It also prevents the tampering of sorted values stored
        // on lower indices
        // Then check the values
        if (left < length && arr[left] > arr[largest]) {
            largest = left;
        } 

        // Similar to above but for another node
        if (right < length && arr[right] > arr[largest]) {
            largest = right;
        }
        
        // If root is not the largest, swap and call heapify
        // on the former root since it might became a root
        // for another subtree.
        if (largest != i) {
            swap(i, largest);
            System.out.print("Swapped " + arr[i] + " with " + arr[largest] + ": ");
            print();
            
            String text = "Check current index " + largest + " (" + arr[largest] + ") "
                + "childs ";
            
            if (largest * 2 + 1 < length) {
                text += ": Left child index - " + (largest * 2 + 1) + " ("
                + arr[largest * 2 + 1] + ") ";
            }
            
            if (largest * 2 + 2 < length) {
                text += ": Right child index - " + (largest * 2 + 2) + " ("
                + arr[largest * 2 + 2] + ")";
            }
            
            System.out.println(text);
            heapify(largest);
        }
    }
    
    // Utility methods
    
    // Swap method to swap two values
    private void swap(int a, int b) {
        arr[a] += arr[b];
        arr[b] = arr[a] - arr[b];
        arr[a] -= arr[b];
    }
    
    // Print method to print current state of arr
    private void print() {
        disp();
    }
    
    private void disp() {
        System.out.print("[");
        for (int i = 0; i != arr.length; i++) {
            if (i != arr.length - 1) {
                System.out.print(arr[i] + " ");
            } else {
                System.out.println(arr[i] + "]");
            }
        }
    }
    
    private void getInput() {
        System.out.print("\nPress any key to continue: ");
        in.next();
        System.out.println();
    }
    
    // Setter in order to sort a new array
    public void setArray(int[] arr) {
        this.arr = arr;
        length = arr.length;
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] arr = {7, 8, 5, 10, 3, 12, 1, 14, 0, 13, 2, 11, 4, 9, 6};
        
        System.out.print("Use default array with value 0 - 14? (type y to use): ");
        char useDef = in.next().charAt(0);
        
        if (!(useDef == 'y' || useDef == 'Y')) {
            System.out.print("Enter the number of length of array: ");
            arr = new int[in.nextInt()];
            System.out.println();
        
            for (int i = 0; i != arr.length; i++) {
                System.out.print("Number on index " + i + ": ");
                arr[i] = in.nextInt();
            }
        }
        
        System.out.print("\nEnable stepping? (type y to enable): ");
        char step = in.next().charAt(0);
        
        new Heapsort(arr, step == 'y' || step == 'Y').sort();
    }
}