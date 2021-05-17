# Notes for 17683 Data Structures for Application Programmers
Some codes are removed from this note considering the copyright problems.

## Data structures
### HashTable, HashMap and HashSet
+ hashing
how to deal with collisions:
1. **Open Addressing**: **Linear Probing**, When there is a collision, we try to find an empty cell sequentially and put the value into the nearest empty cell. However, this approach has an issue of forming clusters (primary clustering) and the performance can get bad. **Quadratic Probing**, when there is a collision, use step size 1, 4, 9.....that has another subtle clustering issue called secondary clustering due to the fixed interval of probing. To solve this clustering issue, there is another workaround, called **Double Hashing**, which uses two hash functions. One is to calculate the hash value and the other is to decide the step size of probing.
2. **Separate Chaining**:The other workaround is to have a linked list at each index. This allows us to not to worry too much about load factor. However, we do not want to make the linked lists become too full either.

what makes a good hash function?
(just copy from Terry's lecture note)
1. **Quick computation** is the key to a good hash function. Thus, a hash function with many multiplications and divisions is NOT a good idea. 
2. **deterministic**, same keys, same hashValue
3. **Randomness**
for **Random key values** that are random and positive,
index = key % hashArray.length;
for **Non-random key values**
Some work should be done to have these part numbers to form a range of more random numbers. 
• Don’t Use Non-Data: The key values should be squeezed as much as it could. For example, category code should be changed to be from 0 to 15. Also, the checksum should be removed because it is a derived number from other information and does not add any new information.
• Use All the Data: Other than the non-data values, we need to use all of the data values. Don’t just use the first four digits, etc.
• Use a Prime Number for the Modulo Base: Which means the table length should be a prime number. For example, if the table array length is 50, then all of the multiples of 50 in our car-part numbers will be hashed into the same index.
• Use Folding: Another reasonable hash function involves breaking keys into groups of digits and adding the groups.
SSN example: 123-45-6789
In case table length is 1009: Break the number into three groups of three digits. (123+456+789 = 1368 % 1009 = 359).
In case table array length is 101: Break the number into four two-digit numbers and one one-digit number. (12+34+56+78+9 = 189 % 101 = 88).
This way you can distribute the numbers better.


+ HashTable
1. in our implementation, use linear probing to solve collisions
2. takes only positive integers, no mapping, just keys
3. major operations: search, delete, insert
**search**: search from the index given by hashFunction, and until a null position. If found, return true.
**delete**: use a flag(DELETED) to represent the deleted item. if set to null, there will be some problems because searching stops when encounters null, so the following items which move because of collision can not be found. This is called **lazy deletion**, place flags when deleted, but actually delete one when insertion happens at that index. Return the item deleted or -1 if not exist.
**insert**: start s from the index given by hashFunction, and until a null position or DELETED flag, insertion will happen at this position.
**search can become really slow when there are many deletions, one way to improve is to use a threshold(like deleted flags/length), and shrink when > threshold**

+ hashMap
**hashCode() in java**
hashCode() method is used to get an integer number from object.
For Integer object in java, it seems to use the original value as its hashcode. But for String class, it uses:
s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
s[0] means the first character of the String.
**Why to use 31 here?**
Terry gives a very detailed explation, so please go to see the vedios or the hand-written note.
In general, because there are more lowercase letters in use than uppercase letters, and in ASCII, the different between lowercase letters and uppercase letters is at the fifth bit(ASCII is represented with 7 bits, B is 66, which is 1000010, and b is 98(66 + 32 = 98), so the 0 at the fifth bit becomes 1, which is 1100010). That's why there is more 1 at the 5th bit(about 75%) which leads to a pattern in hashing which is hated by ours.
When we use the formula above, 31 * i == (i << 5) - i, which means left shifting 5 bits and minus. The minus will lead to a borrow operation in minus and thus cause the high appearance rate in the 5th bit to be spread to lower bits. For detailed calculation, please refer to the vedios or the hand-written note.
And this link is also good, [Why does Java's hashCode() in String use 31 as a multiplier?](https://stackoverflow.com/questions/299304/why-does-javas-hashcode-in-string-use-31-as-a-multiplier)
+ implementaion
HashMap should use the power-of-two as the length of its array, to make h & (length-1) equals to mod operation.
default initial capacity is 16 and load factor is 0.75.

 ```java
public V put(K key, V value) {
    int hash = hash(key.hashCode()); 
    int i = indexFor(hash, table.length);
    for (Entry<K,V> e = table[i]; e != null; e = e.next) { 
        Object k; 
        if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
            V oldValue = e.value; e.value = value;
            e.recordAccess(this); 
            return oldValue;
        }
    } 
    modCount++;
    addEntry(hash, key, value, i); 
    return null;
}

//do folding, fold all bits into lower bits
//这里用中文写了，我觉得你应该再去详细研究一下为什么要做这个folding，可以看一下这个https://blog.csdn.net/qq_32999113/article/details/102527859
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}

//equals to mod, hash%table.length
 static int indexFor(int h, int length) { 
     return h & (length-1);
}

void addEntry(int hash, K key, V value, int bucketIndex) { 
    Entry<K, V> e = table[bucketIndex]; 
    table[bucketIndex] = new Entry<>(hash, key, value, e); 
    if (size++ >= threshold) 
    resize(2*table.length);
}
 ``` 

usage:
```java
//how to iterate
Iterator<String> itr = hm.keySet().iterator();

while (itr.hasNext()) {

}

for (String word : hm.values()) {
    
}
```
+ HashSet
hashset is backed by hashMap.It use a hashmap structure and set the value to be a dummy value(PRESENT).
It uses both hash and equal(because there are collisions) to determine whether to overwrite the element or it is an new element
### Binary search tree
+ tree terminology:
Root
parent
child
leaf
level(height): root is at level 1 and its children are at level 1. Empty tree also has level 0 in this course.
**height formula(for complete binary tree and height starts from 1) :** floor(log2 n) + 1, n is th number of nodes
A full binary tree: is a binary tree where each node has exactly zero or two children.
A complete binary tree: is a binary tree that is completely filled in reading (from left to right) each row with the possible exception of the bottom level.
+ defining characteristic(ordering invariant):
Any node with a key value k, all keys in the left sub-tree must be less than k, right sub-tree must be greater than k.(No duplicate keys)
+ major operations
1. search: search in a recursive way, if current.key < input key, then search the right sub-tree until the leaf... If not found, return false.
2. insert: insert as a leaf. Should record parent, and insert as its left/right child.
3. deletion: should search the node that need to be deleted, but should adjust the tree after deletion. Considering the 4 cases:
• CASE 1: The node is not in the tree. return;
• CASE 2: The node is a leaf. use a variable isLeftChild to determine whether to use parent.left/right = null;
• CASE 3: The node has one child. Put its child as its parent's child. If ifLeftChild, and it only has left child, then parent.left = current.left;
• CASE 4: The node has two children. A bit complex, need to recontruct the tree. In this course, we use **successor** to replace. Successor is the left most node in its right sub-tree(Always find left, and null node's parent is the left most node). Besides, 4 connections need to be rebuild.Please refer to the getSuccessor() method and case 4 code.
4. traversal
**preorder**:parent-left-right
**inorder**left-parent-right. For BST, inorder traversal will visit all nodes in ascending order.
**postorder**left-right-parent

inorder traversal is implemented by recursion:
call itself to traverse left subtree
print the node
call itself to traverse right subtree


+ efficiency
1. search: bounded by height, O(log2n), worst-case: O(n)
2. insert: bounded by height, O(log2n), worst-case: O(n)
3. delete: bounded by height(find toDelete and find successor), O(log2n), worst-case: O(n)
4. traversal:O(n)

+ balanced or not
if not balanced, a BST can degenerate to be a linkedlist when input values are already sorted or inversely sorted, can worst-case running time complexity for search, insertion and deletion becomes O(n).
Ways to remedy this is to build self-balanced tree, such as Red-Black tree, 2-3 tree, Splay Tree and AVL tree, etc.

+ application
**Haffman encoding**:
It is very useful to compress data with the application of a binary tree.

### TreeMap and TreeSet
+ Useful to have all the elements **in order** and perform non-exact match searches
+ implemented based on self-balanced tree, major operations are guaranteed to have O(logn n) worst case running time complexity
+ need to implement Comparable or Comparator for the class to put into
#### TreeMap
+ HashMap offers the best alternative on average for inserting, deleting and searching for elements. But does not allow us to traverse elements in a sorted order(scatter storage).
+ based on Red-Black tree and guaranteed to be balanced. Operations such as inserting, deleting and searching can be guaranteed to be O(log n)
+ is useful when there should be an order in keys.For example, use time as key, and bus number as value, we need to find the first bus after 5pm. At this time, treeMap is a good choice.
+ Useful methods:
```java
//build a treeMap from a hashMap, by default, it is in ascending order
TreeMap<String, Integer> sortedWords = new TreeMap<>(freqOfWords);

//print in decending order
System.out.println(sortedWords.descendingMap());

// find the least key that is greated or equal to "1700"
System.out.println(sortedWords.ceilingKey("1700"));
// find the least key that is strickly greated than "1700"
System.out.println(sortedWords.higherKey("1700"));
....
```
### TreeSet
+ based on Red-Black tree and guaranteed to be balanced. Operations such as inserting, deleting and searching can be guaranteed to be O(log n)
+ Useful methods:
```java
//build a treeSet from a hashSet, by default, it is in ascending order
    HashSet<String> hs = new HashSet<>();
    hs.add("sss");
    hs.add("sss");
    hs.add("aaa");
    hs.add("bbb");
    TreeSet<String> ts = new TreeSet<>(hs);

//print in a ascending/descending order
    System.out.println(ts);
    System.out.println(ts.descendingSet());

//Returns a view of the portion of this set whose elements are strictly less than toElement
    System.out.println(ts.headSet("bbb"));
//elements are strictly less than toElement and the closest
    System.out.println(ts.headSet("bbb").last());
//equals to 
    System.out.println(ts.lower("bbb"));
```
### PriorityQueue(heap)
+ heap characteristics:
1. structural property: complete(almost) binary tree
2. relational(heap-order) properties: largest(smallest) at the root & children contains smaller(or equal) keys than their parent
+ heap is NOT a sorted structure but can be considered as PARTIALLY ordered
+ Operations:
1. Insert
Insert the element to the next tree position to remain complete. Swap the parent to remain heap-order properties.
2. Remove (the root)
Remove the root and replace it with the last node to stay complete(Terry said, "A new employee becomes CEO"). Swap with larger child.
+ Base data structure:
Array. And level order traversal is used to store element.
Use the formulas below to link tree with array.
**left child index:** index * 2 + 1
**right child index:** index * 2 + 2
**parent:**(index - 1) / 2 (both left and right child can use this formula)

+ Implementation

+ Efficiency
The insert and remove operation of heap is bounded by the height of the tree. The worst-case running time complexity is O(log n), log base 2 if it is a binary tree.

+ related data structure
Priority Queue.
add(Object)/offer(Object): O(logn)
poll()/peek(): O(logn)
remove(Object): O(n)
Priority Queue class does not guarantee to traverse the elements in any particular order 

+ comparison with BST

**why 'almost'?**
 If the bottom level is not filled all the way to the right, we say it is "almost" complete binary tree. Some people say that is also a complete binary tree (without "almost")



## Sorting algorithms
+ Bubblesort
Put the largest value to the right by comparing and swaping two values.
The  **worst-case/average** running time complexity is : O(n2)
The **best-case** running time complexity can be O(n) with some slight modification
**stable:** swap just happens between left and right elements, and if they are the same, the order will not change.
```java
public void bubbleSort(int arr[]) {
    for(int i = 0, len = arr.length; i < len - 1; i++) {
        for(int j = 0; j < len - i - 1; j++) {
            if(arr[j + 1] < arr[j])
                swap(arr, j, j + 1);
        }
    }
}

//slight modification to make it O(n) for the best case
public void bubbleSort(int arr[]) {
    boolean didSwap;
    for(int i = 0, len = arr.length; i < len - 1; i++) {
        didSwap = false;
        for(int j = 0; j < len - i - 1; j++) {
            if(arr[j + 1] < arr[j]) {
                swap(arr, j, j + 1);
                didSwap = true;
            }
        }
        if(didSwap == false)
            return;
    }    
}
```
+ SelectionSort
Select the smallest number and place it at the right position.
Faster than bubble sort mainly due to less number of swaps.
The  **worst-case/average/best-case** running time complexity is : O(n2)
**not stable:** swap the element at the position and the smallest one.

+ InsertionSort

+ HeapSort
basic idea:
build a heap using insert() and call removeMax() for max heap or removeMin() for min heap to get items in a sorted order.
The  **worst-case/best-case/average** running time complexity is O(nlogn) :O
**not stable:** think about it, when remove the root, the last element is placed at root. if it comforms to the heap-order properties, there is no more operations. For example, 3 27 36 27.

## Searching algorithms
+ binary search
worst-case: O(log n)

+ hash search


## Lab review
lab7:Heaps and Java PriorityQueue class

PriorityQueue in java is by default min heap, so when you need to create a max heap, input a comparator (Collections.reverseOrder()).

Use a min heap and a max heap to create an implementation that get a median in O(1).(The idea is to split the array into two ordered array and store them separately in to heaps)
**这题是剑指offer 41题**

## Homework review