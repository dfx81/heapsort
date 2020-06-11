/* Heapsort
 * ---
 * Author : - Danial Fitri Ghazali (272868)
 *          - Afirudin Jamilan (ABCDEF)
 *          - Shamil Shahimi (GHIJKL)
 * ---
 * Description: A heapsort implementation for
 *              Data Structure project
 * Usage: - Call constructor with the array to be sorted
 *        - Call sort() method to start sorting (returns the sorted array)
 *        - Call setArray(int[] arr) to set a new array to be sorted
 */

class Heapsort {
    // Properties
    // ---
    // arr stores the int array to be sorted
    // length keeps the current workable length of array
    // - Later, the length will change during sort
    private int[] arr;
    private int length;
    
    // Constructor
    // ---
    // Pass an int array to be sorted
    // Then the user can call sort to start sorting
    public Heapsort(int[] arr) {
        this.arr = arr;
        length = arr.length;
        print();
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
            heapify(i);
            print();
        }
        
        System.out.print("\nMAX HEAP: ");
        print();
        System.out.println();
        
        // Due to max heap, the largest value should be
        // at the root of the tree.
        // First, we swap current largest value to the
        // current lowest index.
        // Then we perform heapify to the root of the tree
        // to bring the next largest value to the root.
        // Reduce length by 1 to avoid tampering with the
        // lower indices, which stored the sorted values.
        for (int i = --length; i >= 0; i--) {
            swap(0, i);
            heapify(0);
            length--;
            print();
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
        for (int i: arr) {
            System.out.print(i + " ");
        }
        
        System.out.println();
    }
    
    // Setter in order to sort a new array
    public void setArray(int[] arr) {
        this.arr = arr;
        length = arr.length;
    }
    
    public static void main(String[] args) {
        int[] arr = {9, 4, 2, 5, 1, 7, 8, 6, 3, 10, 12, 0, 11};
        new Heapsort(arr).sort();
    }
}