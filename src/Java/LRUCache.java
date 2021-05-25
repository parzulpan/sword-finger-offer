package Java;

import java.util.HashMap;
import java.util.Map;

/**
 * @author parzulpan

 146.LRU 缓存

 请你为  LRU (最近最少使用)  缓存算法设计并实现数据结构。

 实现 LRUCache 类：
 LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
 int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
 void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。
 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
 要求在 O(1) 时间复杂度内完成这两种操作

 提示：
 1 <= capacity <= 3000
 0 <= key <= 3000
 0 <= value <= 104
 最多调用 3 * 104 次 get 和 put

 */

public class LRUCache {
    public static void main(String[] args) {
        LRUCache lruCache = new LRUCache(2);

        // 缓存是 {1=1}
        lruCache.put(1, 1);

        // 缓存是 {1=1, 2=2}
        lruCache.put(2, 2);

        // 返回 1
        System.out.println(lruCache.get(1));

        // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        lruCache.put(3, 3);

        // 返回 -1 (未找到)
        System.out.println(lruCache.get(2));

        // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        lruCache.put(4, 4);

        // 返回 -1 (未找到)
        System.out.println(lruCache.get(1));

        // 返回 3
        System.out.println(lruCache.get(3));

        // 返回 4
        System.out.println(lruCache.get(4));

    }

    static class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
        DLinkedNode() {}
        DLinkedNode(int key, int value) { this.key = key; this.value = value; }
    }

    private final Map<Integer, DLinkedNode> cache = new HashMap<>();
    private int size;
    private final int capacity;
    private final DLinkedNode head;
    private final DLinkedNode tail;

    public LRUCache(int capacity) {
        // 当前容量
        this.size = 0;
        // 容量上限
        this.capacity = capacity;
        // 使用伪头部和伪尾部节点
        this.head = new DLinkedNode();
        this.tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }

        // 如果 key 存在，先通过哈希表定位，再移动到头部
        moveToHead(node);

        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            // 如果 key 不存在，创建一个新的节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            // 添加进哈希表
            cache.put(key, newNode);
            // 添加到双向列表头部，增加容量
            addToHead(newNode);
            ++size;
            if (size > capacity) {
                DLinkedNode tail = removeTail();
                cache.remove(tail.key);
                --size;
            }
        } else {
            // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
            node.value = value;
            moveToHead(node);
        }
    }

    /**
     * 删除双向链表的尾部节点，并返回删除的节点
     */
    private DLinkedNode removeTail() {
        DLinkedNode node = this.tail.prev;
        removeNode(node);
        return node;
    }

    /**
     * 移动一个节点到头
     */
    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    /**
     * 添加一个节点到头
     */
    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    /**
     * 删除一个节点
     */
    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}
